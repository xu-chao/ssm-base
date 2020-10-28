package com.java.activiti.service.impl;

import com.java.activiti.dao.NeedGoodsDao;
import com.java.activiti.model.Need;
import com.java.activiti.model.NeedGoods;
import com.java.activiti.service.NeedGoodsService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.DataFormat;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("needGoodsService")
public class NeedGoodsServiceImpl implements NeedGoodsService {

	@Resource
	private NeedGoodsDao needGoodsDao;

	@Override
	public List<NeedGoods> needGoodsPage(Map<String, Object> map) {
		return needGoodsDao.needGoodsPage(map);
	}

	@Override
	public int needGoodsCount(Map<String, Object> map) {
		return needGoodsDao.needGoodsCount(map);
	}

	@Override
	public LinkedList<NeedGoods> needGoodsThroughPage(Map<String, Object> map) {
		return needGoodsDao.needGoodsThroughPage(map);
	}

	@Override
	public int needGoodsThroughCount(Map<String, Object> map) {
		return needGoodsDao.needGoodsThroughCount(map);
	}

	public int deleteAllGoodsByNId(String nId){
		return needGoodsDao.deleteAllGoodsByNId(nId);
	}
	
	public int addNeedGoods(NeedGoods needGoods){
		return needGoodsDao.addNeedGoods(needGoods);
	}

	@Override
	public List<NeedGoods> selectNeedGoods(String nId) {
		return needGoodsDao.selectNeedGoods(nId);
	}

	@Override
	public NeedGoods findNeedGoodsByPurchaseId(String purchaseId) {
		return needGoodsDao.findNeedGoodsByPurchaseId(purchaseId);
	}

	@Override
	public int updateGoods(String nId_tt, String goodsId_tt, String goodsId) {
		return needGoodsDao.updateGoods(nId_tt, goodsId_tt, goodsId);
	}

    @Override
    public void needGoodsExport(OutputStream os, Map<String, Object> map, String flag) {
        List<NeedGoods> needGoodsList = needGoodsDao.needGoodsThroughPage(map);
        // 1.创建excel工作薄
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.创建一个工作表
        HSSFSheet sheet = wk.createSheet("系统用户");
        // 3.写入表头
        HSSFRow row = sheet.createRow(0);
        if(flag.equals("goods")){
            // 表头
            String[] headerName = {
                    "存货编码", "采购单号", "申请日期",
                    "物料名称", "图号", "单位"
            };
            // 列宽
            int[] columnWidths = {
                    6000, 6000, 6000,
                    6000 ,6000, 6000
            };
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
            for (NeedGoods needGoods : needGoodsList) {
                row = sheet.createRow(i);
                row.createCell(0).setCellValue((needGoods.getGoods() == null ? "" : needGoods.getGoods().getGoodsId()));// 存货编码
                row.createCell(1).setCellValue(needGoods.getnId());// 采购单号
                if (needGoods.getNeed().getEApplicantData() != null) {
                    HSSFCellStyle style_date = wk.createCellStyle();
                    DataFormat df = wk.createDataFormat();
                    style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));
                    row.createCell(2).setCellValue(needGoods.getNeed().getEApplicantData());// 申请日期
                    sheet.getRow(i).getCell(2).setCellStyle(style_date);
                }
                row.createCell(4).setCellValue((needGoods.getGoods() == null ? "" : needGoods.getGoods().getGoodsName()));// 物料名称
                row.createCell(5).setCellValue((needGoods.getGoods() == null ? "" : needGoods.getGoods().getGoodsCode()));// 图号
                row.createCell(6).setCellValue((needGoods.getGoods() == null ? "" : needGoods.getGoods().getGoodsUnit()));// 单位
                i++;
            }
        }
        else if(flag.equals("storage")){
            // 表头
            String[] headerName = {
                    "库存号", "采购单号", "申请日期", "存货编码",
                    "工艺数量", "实购数量", "备用数量", "申购数量",
                    "临时备注", "计划备注", "发货标识"
            };
            // 列宽
            int[] columnWidths = {
                    6000, 6000, 6000, 6000,
                    6000 ,6000, 6000, 6000,
                    6000, 6000, 6000
            };
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
            for (NeedGoods needGoods : needGoodsList) {
                row = sheet.createRow(i);
                row.createCell(0).setCellValue((needGoods.getStorage() == null ? "" : needGoods.getStorage().getStorageId()));// 库存号
                row.createCell(1).setCellValue(needGoods.getnId());// 采购单号
                if (needGoods.getNeed().getEApplicantData() != null) {
                    HSSFCellStyle style_date = wk.createCellStyle();
                    DataFormat df = wk.createDataFormat();
                    style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));
                    row.createCell(2).setCellValue(needGoods.getNeed().getEApplicantData());// 申请日期
                    sheet.getRow(i).getCell(2).setCellStyle(style_date);
                }
                row.createCell(3).setCellValue(needGoods.getGoods().getGoodsId());// 存货编码
                row.createCell(4).setCellValue((needGoods.getStorage() == null ? 0 : needGoods.getStorage().getMount()));// 工艺数量
                row.createCell(5).setCellValue((needGoods.getStorage() == null ? 0 : needGoods.getStorage().getMountIn()));// 实购数量
                row.createCell(6).setCellValue((needGoods.getStorage() == null ? 0 : needGoods.getStorage().getMountBack()));// 备用数量
                row.createCell(7).setCellValue((needGoods.getStorage() == null ? 0 : needGoods.getStorage().getMountStorage()));// 申购数量
                row.createCell(8).setCellValue((needGoods.getStorage() == null ? "" : needGoods.getStorage().getETemp()));// 临时备注
                row.createCell(9).setCellValue((needGoods.getStorage() == null ? "" : needGoods.getStorage().getEPlan()));// 计划备注
                row.createCell(10).setCellValue((needGoods.getStorage() == null ? "" : needGoods.getStorage().getEIsRun()));// 发货标识
                i++;
            }
        }
        else if(flag.equals("need")){
            System.out.println("...");
        }
        else if(flag.equals(("purchase"))){
            // 表头
            String[] headerName = {
                    "收货流水号", "采购单号", "申请日期", "存货编码",
                    "收货日期", "供应商信息", "收货数量", "采购人员",
                    "收货人员", "仓库信息"
            };
            // 列宽
            int[] columnWidths = {
                    6000, 6000, 6000, 6000,
                    6000, 6000, 6000, 6000,
                    6000 ,6000
            };
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
            for (NeedGoods needGoods : needGoodsList) {
                row = sheet.createRow(i);
                row.createCell(0).setCellValue((needGoods.getPurchase() == null ? "" : needGoods.getPurchase().getPurchaseId()));// 收货流水号Id
                row.createCell(1).setCellValue(needGoods.getnId());// 采购单号
                if (needGoods.getNeed().getEApplicantData() != null) {
                    HSSFCellStyle style_date = wk.createCellStyle();
                    DataFormat df = wk.createDataFormat();
                    style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));
                    row.createCell(2).setCellValue(needGoods.getNeed().getEApplicantData());// 申请日期
                    sheet.getRow(i).getCell(2).setCellStyle(style_date);
                }
                row.createCell(3).setCellValue(needGoods.getGoods().getGoodsId());// 存货编码
                if (needGoods.getPurchase().getPurchaseDate() != null) {
                    HSSFCellStyle style_date = wk.createCellStyle();
                    DataFormat df = wk.createDataFormat();
                    style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));
                    row.createCell(4).setCellValue(needGoods.getPurchase().getPurchaseDate());// 收货日期
                    sheet.getRow(i).getCell(4).setCellStyle(style_date);
                }
                row.createCell(5).setCellValue(needGoods.getPurchase().getSupplier().getSUPPLIER_NAME());// 供应商信息
                row.createCell(6).setCellValue((needGoods.getPurchase() == null ? 0 : needGoods.getPurchase().getPurchaseMount()));// 收货数量
                row.createCell(7).setCellValue((needGoods.getPurchase() == null ? "" : needGoods.getPurchase().getPurchasePerson()));// 采购人员
                row.createCell(8).setCellValue(needGoods.getPurchase().getUser().getAllName());// 收货人员
                row.createCell(9).setCellValue(needGoods.getPurchase().getRespository().getREPO_ADDRESS());// 仓库信息
                i++;
            }
        }
        else if(flag.equals(("checkout"))){
            // 表头
            String[] headerName = {
                    "质检流水号", "采购单号", "申请日期", "存货编码",
                    "质检日期", "供应商信息", "合格数量", "不合格数量",
                    "送检类型", "送检资料", "送检人员", "首次送检状态",
                    "单据状态", "标签状态"
            };
            // 列宽
            int[] columnWidths = {
                    6000, 6000, 6000, 6000,
                    6000 ,6000, 6000, 6000,
                    6000 ,6000, 6000, 6000,
                    6000, 6000
            };
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
            for (NeedGoods needGoods : needGoodsList) {
                row = sheet.createRow(i);
                row.createCell(0).setCellValue((needGoods.getCheckout() == null ? "" : needGoods.getCheckout().getCheckoutId()));// 质检流水号Id
                row.createCell(1).setCellValue(needGoods.getnId());// 采购单号
                if (needGoods.getNeed().getEApplicantData() != null) {
                    HSSFCellStyle style_date = wk.createCellStyle();
                    DataFormat df = wk.createDataFormat();
                    style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));
                    row.createCell(2).setCellValue(needGoods.getNeed().getEApplicantData());// 申请日期
                    sheet.getRow(i).getCell(2).setCellStyle(style_date);
                }
                row.createCell(3).setCellValue(needGoods.getGoods().getGoodsId());// 存货编码
                if (needGoods.getNeed().getEApplicantData() != null) {
                    HSSFCellStyle style_date = wk.createCellStyle();
                    DataFormat df = wk.createDataFormat();
                    style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));
                    row.createCell(4).setCellValue(needGoods.getCheckout().getCheckoutDate());// 质检日期
                    sheet.getRow(i).getCell(4).setCellStyle(style_date);
                }
                row.createCell(5).setCellValue(needGoods.getCheckout().getSupplier().getSUPPLIER_NAME());// 供应商信息
                row.createCell(6).setCellValue((needGoods.getCheckout() == null ? 0 : needGoods.getCheckout().getCheckoutMount()));// 合格数量
                row.createCell(7).setCellValue((needGoods.getCheckout() == null ? 0 : needGoods.getCheckout().getCheckoutNotQuaMount()));// 不合格数量
                row.createCell(8).setCellValue((needGoods.getCheckout() == null ? "" : needGoods.getCheckout().getCheckoutType()));// 送检类型
                row.createCell(9).setCellValue((needGoods.getCheckout() == null ? "" : needGoods.getCheckout().getCheckoutMaterial()));// 送检资料
                row.createCell(10).setCellValue(needGoods.getCheckout().getUser().getAllName());// 送检人员
                row.createCell(11).setCellValue((needGoods.getCheckout() == null ? "" : needGoods.getCheckout().getCheckoutStatus()));// 首次送检状态
                row.createCell(12).setCellValue((needGoods.getCheckout() == null ? "" : needGoods.getCheckout().getCheckoutDataStatus()));// 单据状态
                row.createCell(13).setCellValue((needGoods.getCheckout() == null ? "" : needGoods.getCheckout().getCheckoutLabelStatus()));// 标签状态
                i++;
            }
        }
        else if(flag.equals(("RCheckout"))){
            // 表头
            String[] headerName = {
                    "不合格编号", "采购单号", "申请日期", "存货编码",
                    "外观", "尺寸", "硬度", "型号",
                    "性能", "探伤", "材料", "其他",
                    "描述"
            };
            // 列宽
            int[] columnWidths = {
                    6000, 6000, 6000, 6000,
                    6000 ,6000, 6000, 6000,
                    6000, 6000, 6000, 6000
            };
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
            for (NeedGoods needGoods : needGoodsList) {
                row = sheet.createRow(i);
                row.createCell(0).setCellValue(((needGoods.getCheckout() == null || needGoods.getCheckout().getNotQualified() == null) ? "" : needGoods.getCheckout().getNotQualified().getNotQuaId()));// 不合格编号
                row.createCell(1).setCellValue(needGoods.getnId());// 采购单号
                if (needGoods.getPurchase().getPurchaseDate() != null) {
                    HSSFCellStyle style_date = wk.createCellStyle();
                    DataFormat df = wk.createDataFormat();
                    style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));
                    row.createCell(2).setCellValue(needGoods.getNeed().getEApplicantData());// 申请日期
                    sheet.getRow(i).getCell(2).setCellStyle(style_date);
                }
                row.createCell(3).setCellValue(needGoods.getGoods().getGoodsId());// 存货编码
                row.createCell(4).setCellValue(((needGoods.getCheckout() == null || needGoods.getCheckout().getNotQualified() == null) ? 0 : needGoods.getCheckout().getNotQualified().getNotQuaLook()));// 外观
                row.createCell(5).setCellValue(((needGoods.getCheckout() == null || needGoods.getCheckout().getNotQualified() == null) ? 0 : needGoods.getCheckout().getNotQualified().getNotQuaSize()));// 尺寸
                row.createCell(6).setCellValue(((needGoods.getCheckout() == null || needGoods.getCheckout().getNotQualified() == null) ? 0 : needGoods.getCheckout().getNotQualified().getNotQuaYd()));// 硬度
                row.createCell(7).setCellValue(((needGoods.getCheckout() == null || needGoods.getCheckout().getNotQualified() == null) ? 0 : needGoods.getCheckout().getNotQualified().getNotQuaType()));// 型号
                row.createCell(8).setCellValue(((needGoods.getCheckout() == null || needGoods.getCheckout().getNotQualified() == null) ? 0 : needGoods.getCheckout().getNotQualified().getNotQuaXn()));// 性能
                row.createCell(9).setCellValue(((needGoods.getCheckout() == null || needGoods.getCheckout().getNotQualified() == null) ? 0 : needGoods.getCheckout().getNotQualified().getNotQuaTs()));// 探伤
                row.createCell(10).setCellValue(((needGoods.getCheckout() == null || needGoods.getCheckout().getNotQualified() == null) ? 0 : needGoods.getCheckout().getNotQualified().getNotQuaZl()));// 资料
                row.createCell(11).setCellValue(((needGoods.getCheckout() == null || needGoods.getCheckout().getNotQualified() == null) ? 0 : needGoods.getCheckout().getNotQualified().getNotQuaQt()));// 其他
                row.createCell(12).setCellValue(((needGoods.getCheckout() == null || needGoods.getCheckout().getNotQualified() == null) ? "" : needGoods.getCheckout().getNotQualified().getNotQuaDesc()));// 描述
                i++;
            }
        }
        else if(flag.equals("needGoods")){
            System.out.println("...");
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

}
