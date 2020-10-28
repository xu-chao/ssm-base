package com.java.activiti.service.impl;

import com.java.activiti.dao.SubProjectDao;
import com.java.activiti.model.Park;
import com.java.activiti.model.SubProject;
import com.java.activiti.service.SubProjectService;
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

@Service("subProjectService")
public class SubProjectServiceImpl implements SubProjectService {

    @Resource
    private SubProjectDao subProjectDao;

    @Override
    public List<SubProject> subProjectPage(Map<String, Object> map) {
        return subProjectDao.subProjectPage(map);
    }

    @Override
    public int subProjectCount(Map<String, Object> map) {
        return subProjectDao.subProjectCount(map);
    }

    @Override
    public List<SubProject> findSubProjectName(String subProjectName) {
            return subProjectDao.selectSubProjectName(subProjectName);
    }

    @Override
    public List<SubProject> searchAllSubProject() {
        return subProjectDao.searchAllSubProject();

    }

    @Override
    public List<Park> findParkByCityID(String cityID) {
        return subProjectDao.findParkByCityID(cityID);
    }

    @Override
    public List<SubProject> findSubProjectByPark(int parkID) {
        return subProjectDao.findSubProjectByPark(parkID);
    }

    @Override
    public SubProject findSubProjectByParkID(int parkID, String subProjectName) {
        return subProjectDao.findSubProjectByParkID(parkID,subProjectName);
    }

    /**
     * 新增项目
     * @param subProject
     * @return
     */
    @Operation(value = "新增项目")
    public int addSubProject(SubProject subProject){
        return subProjectDao.addSubProject(subProject);
    }

    /**
     * 修改城市
     * @param subProject
     * @return
     */
    @Operation(value = "更新项目信息")
    public int updateSubProject(SubProject subProject){
        return subProjectDao.updateSubProject(subProject);
    }

    /**
     * 批量删除项目
     * @param id
     * @return
     */
    @Operation(value = "批量删除项目")
    public int deleteSubProjectById(List<String> id){
        return subProjectDao.deleteSubProjectById(id);
    }

    /**
     * 判断城市是否已经存在
     * @param subProject
     * @return
     */
    public List<SubProject> findSubProject(SubProject subProject){
        return subProjectDao.findSubProject(subProject);
    }

    /**
     * 导出excel文件
     */
    @Override
    public void export(OutputStream os, Map<String,Object> map) {
        // 获取所有供应商信息
        List<SubProject> subProjectList = subProjectDao.subProjectPage(map);
        // 1.创建excel工作薄
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.创建一个工作表
        HSSFSheet sheet = wk.createSheet("系统用户");
        // 3.写入表头
        HSSFRow row = sheet.createRow(0);
        // 表头
        String[] headerName = { "子项目ID", "子项目名称", "公园名称", "城市名称"};
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
        for (SubProject subProject : subProjectList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(subProject.getSubProjectID());// 项目ID
            row.createCell(1).setCellValue(subProject.getSubProjectName());// 项目名称
            row.createCell(2).setCellValue(subProject.getPark().getParkName());// 公园名称
            row.createCell(3).setCellValue(subProject.getCity().getCityName());// 城市
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
    public void subProjectImport(InputStream is) throws IOException {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            // 读取数据
            // 最后一行的行号
            int lastRow = sheet.getLastRowNum();
            SubProject subProject = null;
            for (int i = 1; i <= lastRow; i++) {
                // projectID
                subProject = new SubProject();
                subProject.setSubProjectID((int)(sheet.getRow(i).getCell(0).getNumericCellValue()));
                // 判断是否已经存在，通过projectID来判断
                List<SubProject> list = subProjectDao.selectSubProjectBySubProjectID(subProject.getSubProjectID());
                if (list.size() > 0) {
                    // 说明存在相同，需要更新
                    subProject = list.get(0);
                }
                HSSFCell cell = null;
                // 项目名称
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                subProject.setSubProjectName(sheet.getRow(i).getCell(1).getStringCellValue());
                // 公园序号
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                subProject.setSubProjectID(Integer.parseInt(sheet.getRow(i).getCell(2).getStringCellValue()));
                // 城市序号
                cell = sheet.getRow(i).getCell(3);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                subProject.setSubProjectID(Integer.parseInt(sheet.getRow(i).getCell(3).getStringCellValue()));

                if (list.size() == 0) {
                    // 说明不存在用户信息，需要新增
                    subProjectDao.addSubProject(subProject);
                } else {
                    // 更新用户信息
                    subProjectDao.updateSubProject(subProject);
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
