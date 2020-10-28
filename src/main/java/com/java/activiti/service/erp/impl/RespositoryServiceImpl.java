package com.java.activiti.service.erp.impl;

import com.java.activiti.dao.erp.RespositoryDao;
import com.java.activiti.model.erp.Respository;
import com.java.activiti.service.erp.RespositoryService;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("respositoryService")
public class RespositoryServiceImpl implements RespositoryService {

    @Resource
    private RespositoryDao respositoryDao;

    @Override
    public List<Respository> respositoryPage(Map<String, Object> map) {
        return respositoryDao.respositoryPage(map);
    }

    @Override
    public int respositoryCount(Map<String, Object> map) {
        return respositoryDao.respositoryCount(map);
    }

    @Override
    public List<Respository> findRespositoryName(String q) {
        return respositoryDao.selectRespositoryName(q);
    }

    @Override
    public List<Respository> findRespositoryName() {
        return respositoryDao.selectALL();
    }

    /**
     * 新增物料
     *
     * @param respository
     * @return
     */
    @Operation(value = "新增仓库")
    public int addRespository(Respository respository) {
        return respositoryDao.addRespository(respository);
    }

    /**
     * 修改物料
     *
     * @param respository
     * @return
     */
    @Operation(value = "更新仓库信息")
    public int updateRespository(Respository respository) {
        return respositoryDao.updateRespository(respository);
    }

    /**
     * 批量删除城市
     *
     * @param id
     * @return
     */
    @Operation(value = "批量删除仓库")
    public int deleteRespositoryById(List<String> id) {
        return respositoryDao.deleteRespositoryById(id);
    }

    /**
     * 判断城市是否已经存在
     *
     * @param respository
     * @return
     */
    public List<Respository> findRespository(Respository respository) {
        return respositoryDao.findRespository(respository);
    }

    /**
     * 导出excel文件
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {
        // 获取所有供应商信息
        List<Respository> respositoryList = respositoryDao.respositoryPage(map);
        // 1.创建excel工作薄
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.创建一个工作表
        HSSFSheet sheet = wk.createSheet("系统用户");
        // 3.写入表头
        HSSFRow row = sheet.createRow(0);
        // 表头
        String[] headerName = {"仓库Id", "仓库地址", "状态 ", "区域", "描述"};
        // 列宽
        int[] columnWidths = {6000, 6000, 6000, 6000, 6000};
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
        for (Respository respository : respositoryList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(respository.getREPO_ID());// REPO_ID
            row.createCell(1).setCellValue(respository.getREPO_ADDRESS());// REPO_ADDRESS
            row.createCell(2).setCellValue(respository.getREPO_STATUS());// REPO_STATUS
            row.createCell(3).setCellValue(respository.getREPO_AREA());// REPO_AREA
            row.createCell(4).setCellValue(respository.getREPO_DESC());// REPO_DESC
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
    @Operation(value = "Excel导入物料信息")
    @Override
    public void respositoryImport(InputStream is) throws IOException {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            // 读取数据
            // 最后一行的行号
            int lastRow = sheet.getLastRowNum();
            Respository respository = null;
            for (int i = 1; i <= lastRow; i++) {
                // cityID
                respository = new Respository();
                respository.setREPO_ID((int) (sheet.getRow(i).getCell(0).getNumericCellValue()));
                // 判断是否已经存在，通过cityID来判断
                List<Respository> list = respositoryDao.selectRespositoryByRespositoryID(respository.getREPO_ID());
                if (list.size() > 0) {
                    // 说明存在相同，需要更新
                    respository = list.get(0);
                }
                HSSFCell cell = null;
                // 城市名称
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                respository.setREPO_ADDRESS(sheet.getRow(i).getCell(1).getStringCellValue());
                // 城市名称
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                respository.setREPO_STATUS(sheet.getRow(i).getCell(2).getStringCellValue());
                // 经度
                cell = sheet.getRow(i).getCell(3);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                respository.setREPO_AREA(sheet.getRow(i).getCell(3).getStringCellValue());
                // 经度
                cell = sheet.getRow(i).getCell(4);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                respository.setREPO_DESC(sheet.getRow(i).getCell(4).getStringCellValue());
                if (list.size() == 0) {
                    // 说明不存在用户信息，需要新增
                    respositoryDao.addRespository(respository);
                } else {
                    // 更新用户信息
                    respositoryDao.updateRespository(respository);
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
    public Respository selectRespositoryById(int REPO_ID) {
        return respositoryDao.findById(REPO_ID);
    }
}
