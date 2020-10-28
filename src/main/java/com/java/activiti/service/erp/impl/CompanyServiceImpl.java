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
     * 新增物料
     *
     * @param company
     * @return
     */
    @Operation(value = "新增公司主体")
    public int addCompany(Company company) {
        return companyDao.addCompany(company);
    }

    /**
     * 修改物料
     *
     * @param company
     * @return
     */
    @Operation(value = "更新公司主体信息")
    public int updateCompany(Company company) {
        return companyDao.updateCompany(company);
    }

    /**
     * 批量删除城市
     *
     * @param id
     * @return
     */
    @Operation(value = "批量删除公司主体")
    public int deleteCompanyById(List<String> id) {
        return companyDao.deleteCompanyById(id);
    }

    /**
     * 判断城市是否已经存在
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
     * 导出excel文件
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {
        // 获取所有公司主体信息
        List<Company> companyList = companyDao.companyPage(map);
        // 1.创建excel工作薄
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.创建一个工作表
        HSSFSheet sheet = wk.createSheet("系统用户");
        // 3.写入表头
        HSSFRow row = sheet.createRow(0);
        // 表头
        String[] headerName = {"公司主体Id", "公司主体名称", "公司主体负责人 ", "公司主体联系方式", "公司主体Email", "公司主体地址"};
        // 列宽
        int[] columnWidths = {6000, 6000, 6000, 6000, 6000, 6000};
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
    @Operation(value = "Excel导入公司主体信息")
    @Override
    public void companyImport(InputStream is) throws IOException {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            // 读取数据
            // 最后一行的行号
            int lastRow = sheet.getLastRowNum();
            Company company = null;
            for (int i = 1; i <= lastRow; i++) {
                // cityID
                company = new Company();
                company.setCOMPANY_ID((int) (sheet.getRow(i).getCell(0).getNumericCellValue()));
                // 判断是否已经存在，通过cityID来判断
                List<Company> list = companyDao.selectCompanyByCompanyID(company.getCOMPANY_ID());
                if (list.size() > 0) {
                    // 说明存在相同，需要更新
                    company = list.get(0);
                }
                HSSFCell cell = null;
                // 城市名称
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                company.setCOMPANY_NAME(sheet.getRow(i).getCell(1).getStringCellValue());
                // 城市名称
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                company.setCOMPANY_NAME(sheet.getRow(i).getCell(2).getStringCellValue());
                // 经度
                cell = sheet.getRow(i).getCell(3);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                company.setCOMPANY_TEL(sheet.getRow(i).getCell(3).getStringCellValue());
                // 经度
                cell = sheet.getRow(i).getCell(4);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                company.setCOMPANY_EMAIL(sheet.getRow(i).getCell(4).getStringCellValue());
                // 经度
                cell = sheet.getRow(i).getCell(5);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                company.setCOMPANY_ADDRESS(sheet.getRow(i).getCell(5).getStringCellValue());
                if (list.size() == 0) {
                    // 说明不存在用户信息，需要新增
                    companyDao.addCompany(company);
                } else {
                    // 更新用户信息
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
