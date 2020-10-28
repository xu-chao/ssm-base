package com.java.activiti.service.impl;

import com.java.activiti.dao.ProjectDao;
import com.java.activiti.model.Park;
import com.java.activiti.model.Project;
import com.java.activiti.service.ProjectService;
import com.java.activiti.util.StringUtil;
import com.java.activiti.util.aop.Operation;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

    @Resource
    private ProjectDao projectDao;

    @Override
    public List<Project> projectPage(Map<String, Object> map) {
        return projectDao.projectPage(map);
    }

    @Override
    public int projectCount(Map<String, Object> map) {
        return projectDao.projectCount(map);
    }

    @Override
    public List<Project> findProjectName(String projectName) {
            return projectDao.selectProjectName(projectName);
    }
    @Override
    public List<Project> searchAllProject() {
        return projectDao.searchAllProject();

    }

    @Override
    public List<Park> findParkByCityID(String cityID) {
        return projectDao.findParkByCityID(cityID);
    }
    @Override
    public List<Project> findProjectByParkID(String parkID) {
        return projectDao.findProjectByParkID(parkID);
    }

    /**
     * 新增城市
     * @param project
     * @return
     */
    @Operation(value = "新增城市")
    public int addProject(Project project){
        return projectDao.addProject(project);
    }

    /**
     * 修改城市
     * @param project
     * @return
     */
    @Operation(value = "更新项目信息")
    public int updateProject(Project project){
        return projectDao.updateProject(project);
    }

    /**
     * 批量删除城市
     * @param id
     * @return
     */
    @Operation(value = "批量删除城市")
    public int deleteProjectById(List<String> id){
        return projectDao.deleteProjectById(id);
    }

    /**
     * 判断城市是否已经存在
     * @param project
     * @return
     */
    public List<Project> findProject(Project project){
        return projectDao.findProject(project);
    }

    /**
     * 导出excel文件
     */
    @Override
    public void export(OutputStream os, Map<String,Object> map) {
        // 获取所有供应商信息
        List<Project> projectList = projectDao.projectPage(map);
        // 1.创建excel工作薄
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.创建一个工作表
        HSSFSheet sheet = wk.createSheet("系统用户");
        // 3.写入表头
        HSSFRow row = sheet.createRow(0);
        // 表头
        String[] headerName = { "项目ID", "项目名称", "公园名称", "城市名称"};
        // 列宽
        int[] columnWidths = { 6000, 6000, 6000, 6000 };
        HSSFCell cell = null;
        for (int i = 0; i < headerName.length; i++) {
            // 创建表头单元格
            cell = row.createCell(i);
            // 向表头单元格写值
            cell.setCellValue(headerName[i]);
            sheet.setColumnWidth(i, columnWidths[i]);
        }
        // 4.向内容单元格写值
        int i = 1;
        for (Project project : projectList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(project.getProjectID());// 项目ID
            row.createCell(1).setCellValue(project.getProjectName());// 项目名称
            row.createCell(2).setCellValue(project.getPark().getParkName());// 公园名称
//            row.createCell(3).setCellValue(project.getCity().getCityName());// 城市
            row.createCell(3).setCellValue(project.getPark().getCity().getCityName());// 城市
            i++;
        }
        try {
            // 写入到输出流中
            wk.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭工作簿
                wk.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 数据导入
     */
    @Override
    public void projectImport(InputStream is) throws IOException {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            // 读取数据
            // 最后一行的行号
            int lastRow = sheet.getLastRowNum();
            Project project = null;
            for (int i = 1; i <= lastRow; i++) {
                // projectID
                project = new Project();
                project.setProjectID((int)(sheet.getRow(i).getCell(0).getNumericCellValue()));
                // 判断是否已经存在，通过projectID来判断
                List<Project> list = projectDao.selectProjectByProjectID(project.getProjectID());
                if (list.size() > 0) {
                    // 说明存在相同，需要更新
                    project = list.get(0);
                }
                HSSFCell cell = null;
                // 项目名称
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                project.setProjectName(sheet.getRow(i).getCell(1).getStringCellValue());
                // 公园序号
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                project.setProjectID(Integer.parseInt(sheet.getRow(i).getCell(2).getStringCellValue()));
                // 城市序号
                cell = sheet.getRow(i).getCell(3);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                project.setProjectID(Integer.parseInt(sheet.getRow(i).getCell(3).getStringCellValue()));

                if (list.size() == 0) {
                    // 说明不存在用户信息，需要新增
                    projectDao.addProject(project);
                } else {
                    // 更新用户信息
                    projectDao.updateProject(project);
                }
            }
        } finally {
            if (null != wb) {
                try {
                    wb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
