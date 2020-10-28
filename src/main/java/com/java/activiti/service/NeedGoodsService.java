package com.java.activiti.service;

import com.java.activiti.model.NeedGoods;

import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface NeedGoodsService {

	List<NeedGoods> needGoodsPage(Map<String, Object> map);

	int needGoodsCount(Map<String, Object> map);

	LinkedList<NeedGoods> needGoodsThroughPage(Map<String, Object> map);

	int needGoodsThroughCount(Map<String, Object> map);
	
	int deleteAllGoodsByNId(String nId);
	
	int addNeedGoods(NeedGoods needGoods);

	/**
	 * 获取指定 nId 对应计划需求拥有的采购单号
	 * @param nId 采购单号ID
	 * @return 返回 nId 指定计划需求的采购单号
	 */
	List<NeedGoods> selectNeedGoods(String nId);

	NeedGoods findNeedGoodsByPurchaseId(String purchaseId);

	int updateGoods(String nId_tt,String goodsId_tt, String goodsId);

    void needGoodsExport(OutputStream os, Map<String, Object> map, String flag);
}
