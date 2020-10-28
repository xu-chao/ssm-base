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
     * ��������
     *
     * @param goods
     * @return
     */
    @Operation(value = "��������")
    public int addGoods(Goods goods) {
        return goodsDao.addGoods(goods);
    }

    /**
     * �޸�����
     *
     * @param goods
     * @return
     */
    @Operation(value = "����������Ϣ")
    public int updateGoods(Goods goods) {
        return goodsDao.updateGoods(goods);
    }

    /**
     * ����ɾ������
     *
     * @param id
     * @return
     */
    @Operation(value = "����ɾ������")
    public int deleteGoodsById(List<String> id) {
        return goodsDao.deleteGoodsById(id);
    }

    /**
     * �жϳ����Ƿ��Ѿ�����
     *
     * @param goods
     * @return
     */
    public List<Goods> findGoods(Goods goods) {
        return goodsDao.findGoods(goods);
    }

    /**
     * ����excel�ļ�
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {
        // ��ȡ���й�Ӧ����Ϣ
        List<Goods> goodsList = goodsDao.goodsPage(map);
        // 1.����excel������
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.����һ��������
        HSSFSheet sheet = wk.createSheet("ϵͳ�û�");
        // 3.д���ͷ
        HSSFRow row = sheet.createRow(0);
        // ��ͷ
        String[] headerName = {"�������", "��������", "��� ", "ͼ�� ", "��λ", "�ߴ�", "����", "���� ", "����", "��ֵ", "����"};
        // �п�
        int[] columnWidths = {6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000, 6000};
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
    @Operation(value = "Excel����������Ϣ")
    @Override
    public void goodsImport(InputStream is) throws IOException {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            // ��ȡ����
            // ���һ�е��к�
            int lastRow = sheet.getLastRowNum();
            Goods goods = null;
            for (int i = 1; i <= lastRow; i++) {
                // cityID
                goods = new Goods();
                goods.setGoodsId((String) (sheet.getRow(i).getCell(0).getStringCellValue()));
                // �ж��Ƿ��Ѿ����ڣ�ͨ��cityID���ж�
                List<Goods> list = goodsDao.selectGoodsByGoodsID(goods.getGoodsId());
                if (list.size() > 0) {
                    // ˵��������ͬ����Ҫ����
                    goods = list.get(0);
                }
                HSSFCell cell = null;
                // ��������
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsName(sheet.getRow(i).getCell(1).getStringCellValue());
                // ��������
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsSysName(sheet.getRow(i).getCell(2).getStringCellValue());
                // ����
                cell = sheet.getRow(i).getCell(3);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsType(sheet.getRow(i).getCell(3).getStringCellValue());
                // ����
                cell = sheet.getRow(i).getCell(4);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsCode(sheet.getRow(i).getCell(4).getStringCellValue());
                // γ��
                cell = sheet.getRow(i).getCell(5);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsUnit(sheet.getRow(i).getCell(5).getStringCellValue());
                // γ��
                cell = sheet.getRow(i).getCell(6);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsSize(sheet.getRow(i).getCell(6).getStringCellValue());
                // γ��
                cell = sheet.getRow(i).getCell(7);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsFunction(sheet.getRow(i).getCell(7).getStringCellValue());
                // γ��
                cell = sheet.getRow(i).getCell(8);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsMessage(sheet.getRow(i).getCell(8).getStringCellValue());
                // γ��
                cell = sheet.getRow(i).getCell(9);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsElse(sheet.getRow(i).getCell(9).getStringCellValue());
                // γ��
                cell = sheet.getRow(i).getCell(10);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                goods.setGoodsLink(sheet.getRow(i).getCell(10).getStringCellValue());
                if (list.size() == 0) {
                    // ˵���������û���Ϣ����Ҫ����
                    goodsDao.addGoods(goods);
                } else {
                    // �����û���Ϣ
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
