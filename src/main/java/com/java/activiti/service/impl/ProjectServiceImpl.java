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
     * ��������
     * @param project
     * @return
     */
    @Operation(value = "��������")
    public int addProject(Project project){
        return projectDao.addProject(project);
    }

    /**
     * �޸ĳ���
     * @param project
     * @return
     */
    @Operation(value = "������Ŀ��Ϣ")
    public int updateProject(Project project){
        return projectDao.updateProject(project);
    }

    /**
     * ����ɾ������
     * @param id
     * @return
     */
    @Operation(value = "����ɾ������")
    public int deleteProjectById(List<String> id){
        return projectDao.deleteProjectById(id);
    }

    /**
     * �жϳ����Ƿ��Ѿ�����
     * @param project
     * @return
     */
    public List<Project> findProject(Project project){
        return projectDao.findProject(project);
    }

    /**
     * ����excel�ļ�
     */
    @Override
    public void export(OutputStream os, Map<String,Object> map) {
        // ��ȡ���й�Ӧ����Ϣ
        List<Project> projectList = projectDao.projectPage(map);
        // 1.����excel������
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.����һ��������
        HSSFSheet sheet = wk.createSheet("ϵͳ�û�");
        // 3.д���ͷ
        HSSFRow row = sheet.createRow(0);
        // ��ͷ
        String[] headerName = { "��ĿID", "��Ŀ����", "��԰����", "��������"};
        // �п�
        int[] columnWidths = { 6000, 6000, 6000, 6000 };
        HSSFCell cell = null;
        for (int i = 0; i < headerName.length; i++) {
            // ������ͷ��Ԫ��
            cell = row.createCell(i);
            // ���ͷ��Ԫ��дֵ
            cell.setCellValue(headerName[i]);
            sheet.setColumnWidth(i, columnWidths[i]);
        }
        // 4.�����ݵ�Ԫ��дֵ
        int i = 1;
        for (Project project : projectList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(project.getProjectID());// ��ĿID
            row.createCell(1).setCellValue(project.getProjectName());// ��Ŀ����
            row.createCell(2).setCellValue(project.getPark().getParkName());// ��԰����
//            row.createCell(3).setCellValue(project.getCity().getCityName());// ����
            row.createCell(3).setCellValue(project.getPark().getCity().getCityName());// ����
            i++;
        }
        try {
            // д�뵽�������
            wk.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // �رչ�����
                wk.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ���ݵ���
     */
    @Override
    public void projectImport(InputStream is) throws IOException {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            // ��ȡ����
            // ���һ�е��к�
            int lastRow = sheet.getLastRowNum();
            Project project = null;
            for (int i = 1; i <= lastRow; i++) {
                // projectID
                project = new Project();
                project.setProjectID((int)(sheet.getRow(i).getCell(0).getNumericCellValue()));
                // �ж��Ƿ��Ѿ����ڣ�ͨ��projectID���ж�
                List<Project> list = projectDao.selectProjectByProjectID(project.getProjectID());
                if (list.size() > 0) {
                    // ˵��������ͬ����Ҫ����
                    project = list.get(0);
                }
                HSSFCell cell = null;
                // ��Ŀ����
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                project.setProjectName(sheet.getRow(i).getCell(1).getStringCellValue());
                // ��԰���
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                project.setProjectID(Integer.parseInt(sheet.getRow(i).getCell(2).getStringCellValue()));
                // �������
                cell = sheet.getRow(i).getCell(3);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                project.setProjectID(Integer.parseInt(sheet.getRow(i).getCell(3).getStringCellValue()));

                if (list.size() == 0) {
                    // ˵���������û���Ϣ����Ҫ����
                    projectDao.addProject(project);
                } else {
                    // �����û���Ϣ
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
