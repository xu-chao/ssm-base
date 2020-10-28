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
     * ������Ŀ
     * @param subProject
     * @return
     */
    @Operation(value = "������Ŀ")
    public int addSubProject(SubProject subProject){
        return subProjectDao.addSubProject(subProject);
    }

    /**
     * �޸ĳ���
     * @param subProject
     * @return
     */
    @Operation(value = "������Ŀ��Ϣ")
    public int updateSubProject(SubProject subProject){
        return subProjectDao.updateSubProject(subProject);
    }

    /**
     * ����ɾ����Ŀ
     * @param id
     * @return
     */
    @Operation(value = "����ɾ����Ŀ")
    public int deleteSubProjectById(List<String> id){
        return subProjectDao.deleteSubProjectById(id);
    }

    /**
     * �жϳ����Ƿ��Ѿ�����
     * @param subProject
     * @return
     */
    public List<SubProject> findSubProject(SubProject subProject){
        return subProjectDao.findSubProject(subProject);
    }

    /**
     * ����excel�ļ�
     */
    @Override
    public void export(OutputStream os, Map<String,Object> map) {
        // ��ȡ���й�Ӧ����Ϣ
        List<SubProject> subProjectList = subProjectDao.subProjectPage(map);
        // 1.����excel������
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.����һ��������
        HSSFSheet sheet = wk.createSheet("ϵͳ�û�");
        // 3.д���ͷ
        HSSFRow row = sheet.createRow(0);
        // ��ͷ
        String[] headerName = { "����ĿID", "����Ŀ����", "��԰����", "��������"};
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
        for (SubProject subProject : subProjectList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(subProject.getSubProjectID());// ��ĿID
            row.createCell(1).setCellValue(subProject.getSubProjectName());// ��Ŀ����
            row.createCell(2).setCellValue(subProject.getPark().getParkName());// ��԰����
            row.createCell(3).setCellValue(subProject.getCity().getCityName());// ����
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
    public void subProjectImport(InputStream is) throws IOException {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            // ��ȡ����
            // ���һ�е��к�
            int lastRow = sheet.getLastRowNum();
            SubProject subProject = null;
            for (int i = 1; i <= lastRow; i++) {
                // projectID
                subProject = new SubProject();
                subProject.setSubProjectID((int)(sheet.getRow(i).getCell(0).getNumericCellValue()));
                // �ж��Ƿ��Ѿ����ڣ�ͨ��projectID���ж�
                List<SubProject> list = subProjectDao.selectSubProjectBySubProjectID(subProject.getSubProjectID());
                if (list.size() > 0) {
                    // ˵��������ͬ����Ҫ����
                    subProject = list.get(0);
                }
                HSSFCell cell = null;
                // ��Ŀ����
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                subProject.setSubProjectName(sheet.getRow(i).getCell(1).getStringCellValue());
                // ��԰���
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                subProject.setSubProjectID(Integer.parseInt(sheet.getRow(i).getCell(2).getStringCellValue()));
                // �������
                cell = sheet.getRow(i).getCell(3);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                subProject.setSubProjectID(Integer.parseInt(sheet.getRow(i).getCell(3).getStringCellValue()));

                if (list.size() == 0) {
                    // ˵���������û���Ϣ����Ҫ����
                    subProjectDao.addSubProject(subProject);
                } else {
                    // �����û���Ϣ
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
