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
        // 1.����excel������
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.����һ��������
        HSSFSheet sheet = wk.createSheet("ϵͳ�û�");
        // 3.д���ͷ
        HSSFRow row = sheet.createRow(0);
        if(flag.equals("goods")){
            // ��ͷ
            String[] headerName = {
                    "�������", "�ɹ�����", "��������",
                    "��������", "ͼ��", "��λ"
            };
            // �п�
            int[] columnWidths = {
                    6000, 6000, 6000,
                    6000 ,6000, 6000
            };
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
            for (NeedGoods needGoods : needGoodsList) {
                row = sheet.createRow(i);
                row.createCell(0).setCellValue((needGoods.getGoods() == null ? "" : needGoods.getGoods().getGoodsId()));// �������
                row.createCell(1).setCellValue(needGoods.getnId());// �ɹ�����
                if (needGoods.getNeed().getEApplicantData() != null) {
                    HSSFCellStyle style_date = wk.createCellStyle();
                    DataFormat df = wk.createDataFormat();
                    style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));
                    row.createCell(2).setCellValue(needGoods.getNeed().getEApplicantData());// ��������
                    sheet.getRow(i).getCell(2).setCellStyle(style_date);
                }
                row.createCell(4).setCellValue((needGoods.getGoods() == null ? "" : needGoods.getGoods().getGoodsName()));// ��������
                row.createCell(5).setCellValue((needGoods.getGoods() == null ? "" : needGoods.getGoods().getGoodsCode()));// ͼ��
                row.createCell(6).setCellValue((needGoods.getGoods() == null ? "" : needGoods.getGoods().getGoodsUnit()));// ��λ
                i++;
            }
        }
        else if(flag.equals("storage")){
            // ��ͷ
            String[] headerName = {
                    "����", "�ɹ�����", "��������", "�������",
                    "��������", "ʵ������", "��������", "�깺����",
                    "��ʱ��ע", "�ƻ���ע", "������ʶ"
            };
            // �п�
            int[] columnWidths = {
                    6000, 6000, 6000, 6000,
                    6000 ,6000, 6000, 6000,
                    6000, 6000, 6000
            };
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
            for (NeedGoods needGoods : needGoodsList) {
                row = sheet.createRow(i);
                row.createCell(0).setCellValue((needGoods.getStorage() == null ? "" : needGoods.getStorage().getStorageId()));// ����
                row.createCell(1).setCellValue(needGoods.getnId());// �ɹ�����
                if (needGoods.getNeed().getEApplicantData() != null) {
                    HSSFCellStyle style_date = wk.createCellStyle();
                    DataFormat df = wk.createDataFormat();
                    style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));
                    row.createCell(2).setCellValue(needGoods.getNeed().getEApplicantData());// ��������
                    sheet.getRow(i).getCell(2).setCellStyle(style_date);
                }
                row.createCell(3).setCellValue(needGoods.getGoods().getGoodsId());// �������
                row.createCell(4).setCellValue((needGoods.getStorage() == null ? 0 : needGoods.getStorage().getMount()));// ��������
                row.createCell(5).setCellValue((needGoods.getStorage() == null ? 0 : needGoods.getStorage().getMountIn()));// ʵ������
                row.createCell(6).setCellValue((needGoods.getStorage() == null ? 0 : needGoods.getStorage().getMountBack()));// ��������
                row.createCell(7).setCellValue((needGoods.getStorage() == null ? 0 : needGoods.getStorage().getMountStorage()));// �깺����
                row.createCell(8).setCellValue((needGoods.getStorage() == null ? "" : needGoods.getStorage().getETemp()));// ��ʱ��ע
                row.createCell(9).setCellValue((needGoods.getStorage() == null ? "" : needGoods.getStorage().getEPlan()));// �ƻ���ע
                row.createCell(10).setCellValue((needGoods.getStorage() == null ? "" : needGoods.getStorage().getEIsRun()));// ������ʶ
                i++;
            }
        }
        else if(flag.equals("need")){
            System.out.println("...");
        }
        else if(flag.equals(("purchase"))){
            // ��ͷ
            String[] headerName = {
                    "�ջ���ˮ��", "�ɹ�����", "��������", "�������",
                    "�ջ�����", "��Ӧ����Ϣ", "�ջ�����", "�ɹ���Ա",
                    "�ջ���Ա", "�ֿ���Ϣ"
            };
            // �п�
            int[] columnWidths = {
                    6000, 6000, 6000, 6000,
                    6000, 6000, 6000, 6000,
                    6000 ,6000
            };
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
            for (NeedGoods needGoods : needGoodsList) {
                row = sheet.createRow(i);
                row.createCell(0).setCellValue((needGoods.getPurchase() == null ? "" : needGoods.getPurchase().getPurchaseId()));// �ջ���ˮ��Id
                row.createCell(1).setCellValue(needGoods.getnId());// �ɹ�����
                if (needGoods.getNeed().getEApplicantData() != null) {
                    HSSFCellStyle style_date = wk.createCellStyle();
                    DataFormat df = wk.createDataFormat();
                    style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));
                    row.createCell(2).setCellValue(needGoods.getNeed().getEApplicantData());// ��������
                    sheet.getRow(i).getCell(2).setCellStyle(style_date);
                }
                row.createCell(3).setCellValue(needGoods.getGoods().getGoodsId());// �������
                if (needGoods.getPurchase().getPurchaseDate() != null) {
                    HSSFCellStyle style_date = wk.createCellStyle();
                    DataFormat df = wk.createDataFormat();
                    style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));
                    row.createCell(4).setCellValue(needGoods.getPurchase().getPurchaseDate());// �ջ�����
                    sheet.getRow(i).getCell(4).setCellStyle(style_date);
                }
                row.createCell(5).setCellValue(needGoods.getPurchase().getSupplier().getSUPPLIER_NAME());// ��Ӧ����Ϣ
                row.createCell(6).setCellValue((needGoods.getPurchase() == null ? 0 : needGoods.getPurchase().getPurchaseMount()));// �ջ�����
                row.createCell(7).setCellValue((needGoods.getPurchase() == null ? "" : needGoods.getPurchase().getPurchasePerson()));// �ɹ���Ա
                row.createCell(8).setCellValue(needGoods.getPurchase().getUser().getAllName());// �ջ���Ա
                row.createCell(9).setCellValue(needGoods.getPurchase().getRespository().getREPO_ADDRESS());// �ֿ���Ϣ
                i++;
            }
        }
        else if(flag.equals(("checkout"))){
            // ��ͷ
            String[] headerName = {
                    "�ʼ���ˮ��", "�ɹ�����", "��������", "�������",
                    "�ʼ�����", "��Ӧ����Ϣ", "�ϸ�����", "���ϸ�����",
                    "�ͼ�����", "�ͼ�����", "�ͼ���Ա", "�״��ͼ�״̬",
                    "����״̬", "��ǩ״̬"
            };
            // �п�
            int[] columnWidths = {
                    6000, 6000, 6000, 6000,
                    6000 ,6000, 6000, 6000,
                    6000 ,6000, 6000, 6000,
                    6000, 6000
            };
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
            for (NeedGoods needGoods : needGoodsList) {
                row = sheet.createRow(i);
                row.createCell(0).setCellValue((needGoods.getCheckout() == null ? "" : needGoods.getCheckout().getCheckoutId()));// �ʼ���ˮ��Id
                row.createCell(1).setCellValue(needGoods.getnId());// �ɹ�����
                if (needGoods.getNeed().getEApplicantData() != null) {
                    HSSFCellStyle style_date = wk.createCellStyle();
                    DataFormat df = wk.createDataFormat();
                    style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));
                    row.createCell(2).setCellValue(needGoods.getNeed().getEApplicantData());// ��������
                    sheet.getRow(i).getCell(2).setCellStyle(style_date);
                }
                row.createCell(3).setCellValue(needGoods.getGoods().getGoodsId());// �������
                if (needGoods.getNeed().getEApplicantData() != null) {
                    HSSFCellStyle style_date = wk.createCellStyle();
                    DataFormat df = wk.createDataFormat();
                    style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));
                    row.createCell(4).setCellValue(needGoods.getCheckout().getCheckoutDate());// �ʼ�����
                    sheet.getRow(i).getCell(4).setCellStyle(style_date);
                }
                row.createCell(5).setCellValue(needGoods.getCheckout().getSupplier().getSUPPLIER_NAME());// ��Ӧ����Ϣ
                row.createCell(6).setCellValue((needGoods.getCheckout() == null ? 0 : needGoods.getCheckout().getCheckoutMount()));// �ϸ�����
                row.createCell(7).setCellValue((needGoods.getCheckout() == null ? 0 : needGoods.getCheckout().getCheckoutNotQuaMount()));// ���ϸ�����
                row.createCell(8).setCellValue((needGoods.getCheckout() == null ? "" : needGoods.getCheckout().getCheckoutType()));// �ͼ�����
                row.createCell(9).setCellValue((needGoods.getCheckout() == null ? "" : needGoods.getCheckout().getCheckoutMaterial()));// �ͼ�����
                row.createCell(10).setCellValue(needGoods.getCheckout().getUser().getAllName());// �ͼ���Ա
                row.createCell(11).setCellValue((needGoods.getCheckout() == null ? "" : needGoods.getCheckout().getCheckoutStatus()));// �״��ͼ�״̬
                row.createCell(12).setCellValue((needGoods.getCheckout() == null ? "" : needGoods.getCheckout().getCheckoutDataStatus()));// ����״̬
                row.createCell(13).setCellValue((needGoods.getCheckout() == null ? "" : needGoods.getCheckout().getCheckoutLabelStatus()));// ��ǩ״̬
                i++;
            }
        }
        else if(flag.equals(("RCheckout"))){
            // ��ͷ
            String[] headerName = {
                    "���ϸ���", "�ɹ�����", "��������", "�������",
                    "���", "�ߴ�", "Ӳ��", "�ͺ�",
                    "����", "̽��", "����", "����",
                    "����"
            };
            // �п�
            int[] columnWidths = {
                    6000, 6000, 6000, 6000,
                    6000 ,6000, 6000, 6000,
                    6000, 6000, 6000, 6000
            };
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
            for (NeedGoods needGoods : needGoodsList) {
                row = sheet.createRow(i);
                row.createCell(0).setCellValue(((needGoods.getCheckout() == null || needGoods.getCheckout().getNotQualified() == null) ? "" : needGoods.getCheckout().getNotQualified().getNotQuaId()));// ���ϸ���
                row.createCell(1).setCellValue(needGoods.getnId());// �ɹ�����
                if (needGoods.getPurchase().getPurchaseDate() != null) {
                    HSSFCellStyle style_date = wk.createCellStyle();
                    DataFormat df = wk.createDataFormat();
                    style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));
                    row.createCell(2).setCellValue(needGoods.getNeed().getEApplicantData());// ��������
                    sheet.getRow(i).getCell(2).setCellStyle(style_date);
                }
                row.createCell(3).setCellValue(needGoods.getGoods().getGoodsId());// �������
                row.createCell(4).setCellValue(((needGoods.getCheckout() == null || needGoods.getCheckout().getNotQualified() == null) ? 0 : needGoods.getCheckout().getNotQualified().getNotQuaLook()));// ���
                row.createCell(5).setCellValue(((needGoods.getCheckout() == null || needGoods.getCheckout().getNotQualified() == null) ? 0 : needGoods.getCheckout().getNotQualified().getNotQuaSize()));// �ߴ�
                row.createCell(6).setCellValue(((needGoods.getCheckout() == null || needGoods.getCheckout().getNotQualified() == null) ? 0 : needGoods.getCheckout().getNotQualified().getNotQuaYd()));// Ӳ��
                row.createCell(7).setCellValue(((needGoods.getCheckout() == null || needGoods.getCheckout().getNotQualified() == null) ? 0 : needGoods.getCheckout().getNotQualified().getNotQuaType()));// �ͺ�
                row.createCell(8).setCellValue(((needGoods.getCheckout() == null || needGoods.getCheckout().getNotQualified() == null) ? 0 : needGoods.getCheckout().getNotQualified().getNotQuaXn()));// ����
                row.createCell(9).setCellValue(((needGoods.getCheckout() == null || needGoods.getCheckout().getNotQualified() == null) ? 0 : needGoods.getCheckout().getNotQualified().getNotQuaTs()));// ̽��
                row.createCell(10).setCellValue(((needGoods.getCheckout() == null || needGoods.getCheckout().getNotQualified() == null) ? 0 : needGoods.getCheckout().getNotQualified().getNotQuaZl()));// ����
                row.createCell(11).setCellValue(((needGoods.getCheckout() == null || needGoods.getCheckout().getNotQualified() == null) ? 0 : needGoods.getCheckout().getNotQualified().getNotQuaQt()));// ����
                row.createCell(12).setCellValue(((needGoods.getCheckout() == null || needGoods.getCheckout().getNotQualified() == null) ? "" : needGoods.getCheckout().getNotQualified().getNotQuaDesc()));// ����
                i++;
            }
        }
        else if(flag.equals("needGoods")){
            System.out.println("...");
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

}
