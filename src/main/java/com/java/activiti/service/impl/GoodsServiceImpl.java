package com.java.activiti.service.impl;

import com.java.activiti.dao.GoodsDao;
import com.java.activiti.model.Goods;
import com.java.activiti.pojo.Tree;
import com.java.activiti.service.GoodsService;
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

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsDao goodsDao;

    @Override
    public List<Goods> goodsPage(Map<String, Object> map) {
        return goodsDao.goodsPage(map);
    }

    @Override
    public int goodsCount(Map<String, Object> map) {
        return goodsDao.goodsCount(map);
    }

    @Override
    public List<Goods> findGoodsNameLike(String q) {
        return goodsDao.selectGoodsNameLike(q);
    }
    @Override
    public List<Goods> findGoodsCodeLike(String q) {
        return goodsDao.selectGoodsCodeLike(q);
    }
    @Override
    public List<Goods> findGoodsTypeLike(String q) {
        return goodsDao.selectGoodsTypeLike(q);
    }
    @Override
    public List<Tree> selectGoodsType() {
        List<Tree> listTree = new LinkedList<Tree>();
        int i = 0;
        List<String> goodsTypes = goodsDao.selectGoodsTypeAll();
        for (String goodsType : goodsTypes) {
            Tree tree = new Tree();
            tree.setId(String.valueOf(i));
            tree.setText(goodsType);
            listTree.add(i, tree);
            i++;
        }
        return listTree;
    }

    /**
     * 新增物料
     *
     * @param goods
     * @return
     */
    @Operation(value = "新增物料")
    public int addGoods(Goods goods) {
        return goodsDao.addGoods(goods);
    }

    /**
     * 修改物料
     *
     * @param goods
     * @return
     */
    @Operation(value = "更新物料信息")
    public int updateGoods(Goods goods) {
        return goodsDao.updateGoods(goods);
    }

    /**
     * 批量删除城市
     *
     * @param id
     * @return
     */
    @Operation(value = "批量删除物料")
    public int deleteGoodsById(List<String> id) {
        return goodsDao.deleteGoodsById(id);
    }

    /**
     * 判断城市是否已经存在
     *
     * @param goods
     * @return
     */
    public List<Goods> findGoods(Goods goods) {
        return goodsDao.findGoods(goods);
    }

    /**
     * 导出excel文件
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {
        // 获取所有供应商信息
        List<Goods> goodsList = goodsDao.goodsPage(map);
        // 1.创建excel工作薄
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.创建一个工作表
        HSSFSheet sheet = wk.createSheet("系统用户");
        // 3.写入表头
        HSSFRow row = sheet.createRow(0);
        // 表头
        String[] headerName = {"存货编码", "物料名称", "类别 ", "图号 ", "单位", "尺寸", "功能", "资料 ", "其他", "价值", "链接"};
        // 列宽
        int[] columnWidths = {6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000};
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
        for (Goods goods : goodsList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(goods.getGoodsId());// GoodsID
            row.createCell(1).setCellValue(goods.getGoodsName());// GoodsName
            row.createCell(2).setCellValue(goods.getGoodsSysName());// GoodsSysName
            row.createCell(3).setCellValue(goods.getGoodsType());// GoodsType
            row.createCell(4).setCellValue(goods.getGoodsCode());// GoodsType
            row.createCell(5).setCellValue(goods.getGoodsUnit()); // GoodsUnit
            row.createCell(6).setCellValue(goods.getGoodsSize());// GoodsSize
            row.createCell(7).setCellValue(goods.getGoodsFunction());// GoodsFunction
            row.createCell(8).setCellValue(goods.getGoodsMessage());// GoodsMessage
            row.createCell(9).setCellValue(goods.getGoodsElse()); // GoodsElse
            row.createCell(10).setCellValue(goods.getGoodsLink()); // GoodsLink
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
    public void goodsImport(InputStream is) throws IOException {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            // 读取数据
            // 最后一行的行号
            int lastRow = sheet.getLastRowNum();
            Goods goods = null;
            for (int i = 1; i <= lastRow; i++) {
                // cityID
                goods = new Goods();
                goods.setGoodsId((String) (sheet.getRow(i).getCell(0).getStringCellValue()));
                // 判断是否已经存在，通过cityID来判断
                List<Goods> list = goodsDao.selectGoodsByGoodsID(goods.getGoodsId());
                if (list.size() > 0) {
                    // 说明存在相同，需要更新
                    goods = list.get(0);
                }
                HSSFCell cell = null;
                // 城市名称
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsName(sheet.getRow(i).getCell(1).getStringCellValue());
                // 城市名称
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsSysName(sheet.getRow(i).getCell(2).getStringCellValue());
                // 经度
                cell = sheet.getRow(i).getCell(3);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsType(sheet.getRow(i).getCell(3).getStringCellValue());
                // 经度
                cell = sheet.getRow(i).getCell(4);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsCode(sheet.getRow(i).getCell(4).getStringCellValue());
                // 纬度
                cell = sheet.getRow(i).getCell(5);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsUnit(sheet.getRow(i).getCell(5).getStringCellValue());
                // 纬度
                cell = sheet.getRow(i).getCell(6);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsSize(sheet.getRow(i).getCell(6).getStringCellValue());
                // 纬度
                cell = sheet.getRow(i).getCell(7);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsFunction(sheet.getRow(i).getCell(7).getStringCellValue());
                // 纬度
                cell = sheet.getRow(i).getCell(8);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsMessage(sheet.getRow(i).getCell(8).getStringCellValue());
                // 纬度
                cell = sheet.getRow(i).getCell(9);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsElse(sheet.getRow(i).getCell(9).getStringCellValue());
                // 纬度
                cell = sheet.getRow(i).getCell(10);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsLink(sheet.getRow(i).getCell(10).getStringCellValue());
                if (list.size() == 0) {
                    // 说明不存在用户信息，需要新增
                    goodsDao.addGoods(goods);
                } else {
                    // 更新用户信息
                    goodsDao.updateGoods(goods);
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
    public Goods selectGoodsById(String ECodeID) {
        return goodsDao.findById(ECodeID);
    }
}
