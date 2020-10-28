package com.java.activiti.service.erp.impl;

import com.java.activiti.dao.erp.CompanyDao;
import com.java.activiti.dao.erp.SupplierDao;
import com.java.activiti.model.erp.Company;
import com.java.activiti.model.erp.Supplier;
import com.java.activiti.service.erp.CompanyService;
import com.java.activiti.service.erp.SupplierService;
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

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

    @Resource
    private CompanyDao companyDao;

    @Override
    public List<Company> companyPage(Map<String, Object> map) {
        return companyDao.companyPage(map);
    }

    @Override
    public int companyCount(Map<String, Object> map) {
        return companyDao.companyCount(map);
    }

    @Override
    public List<Company> findCompanyName(String q) {
        return companyDao.selectCompanyName(q);
    }

    @Override
    public List<Company> findCompanyName() {
        return companyDao.selectALL();
    }

    /**
     * ��������
     *
     * @param company
     * @return
     */
    @Operation(value = "������˾����")
    public int addCompany(Company company) {
        return companyDao.addCompany(company);
    }

    /**
     * �޸�����
     *
     * @param company
     * @return
     */
    @Operation(value = "���¹�˾������Ϣ")
    public int updateCompany(Company company) {
        return companyDao.updateCompany(company);
    }

    /**
     * ����ɾ������
     *
     * @param id
     * @return
     */
    @Operation(value = "����ɾ����˾����")
    public int deleteCompanyById(List<String> id) {
        return companyDao.deleteCompanyById(id);
    }

    /**
     * �жϳ����Ƿ��Ѿ�����
     *
     * @param company
     * @return
     */
    public List<Company> findCompany(Company company) {
        return companyDao.findCompany(company);
    }

    public Company findCompanyByName(String COMPANY_NAME) {
        return companyDao.findCompanyByName(COMPANY_NAME);
    }

    /**
     * ����excel�ļ�
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {
        // ��ȡ���й�˾������Ϣ
        List<Company> companyList = companyDao.companyPage(map);
        // 1.����excel������
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.����һ��������
        HSSFSheet sheet = wk.createSheet("ϵͳ�û�");
        // 3.д���ͷ
        HSSFRow row = sheet.createRow(0);
        // ��ͷ
        String[] headerName = {"��˾����Id", "��˾��������", "��˾���帺���� ", "��˾������ϵ��ʽ", "��˾����Email", "��˾�����ַ"};
        // �п�
        int[] columnWidths = {6000, 6000, 6000, 6000, 6000, 6000};
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
        for (Company company : companyList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(company.getCOMPANY_ID());// COMPANY_ID()()
            row.createCell(1).setCellValue(company.getCOMPANY_NAME());// COMPANY_NAME()
            row.createCell(2).setCellValue(company.getCOMPANY_PERSON());// COMPANY_PERSON()
            row.createCell(3).setCellValue(company.getCOMPANY_TEL());// COMPANY_TEL()
            row.createCell(4).setCellValue(company.getCOMPANY_EMAIL());// COMPANY_EMAIL()
            row.createCell(5).setCellValue(company.getCOMPANY_ADDRESS());// COMPANY_ADDRESS()
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
    @Operation(value = "Excel���빫˾������Ϣ")
    @Override
    public void companyImport(InputStream is) throws IOException {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            // ��ȡ����
            // ���һ�е��к�
            int lastRow = sheet.getLastRowNum();
            Company company = null;
            for (int i = 1; i <= lastRow; i++) {
                // cityID
                company = new Company();
                company.setCOMPANY_ID((int) (sheet.getRow(i).getCell(0).getNumericCellValue()));
                // �ж��Ƿ��Ѿ����ڣ�ͨ��cityID���ж�
                List<Company> list = companyDao.selectCompanyByCompanyID(company.getCOMPANY_ID());
                if (list.size() > 0) {
                    // ˵��������ͬ����Ҫ����
                    company = list.get(0);
                }
                HSSFCell cell = null;
                // ��������
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                company.setCOMPANY_NAME(sheet.getRow(i).getCell(1).getStringCellValue());
                // ��������
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                company.setCOMPANY_NAME(sheet.getRow(i).getCell(2).getStringCellValue());
                // ����
                cell = sheet.getRow(i).getCell(3);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                company.setCOMPANY_TEL(sheet.getRow(i).getCell(3).getStringCellValue());
                // ����
                cell = sheet.getRow(i).getCell(4);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                company.setCOMPANY_EMAIL(sheet.getRow(i).getCell(4).getStringCellValue());
                // ����
                cell = sheet.getRow(i).getCell(5);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                company.setCOMPANY_ADDRESS(sheet.getRow(i).getCell(5).getStringCellValue());
                if (list.size() == 0) {
                    // ˵���������û���Ϣ����Ҫ����
                    companyDao.addCompany(company);
                } else {
                    // �����û���Ϣ
                    companyDao.updateCompany(company);
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

    @Override
    public Company selectCompanyById(int COMPANY_ID) {
        return companyDao.findById(COMPANY_ID);
    }
}
