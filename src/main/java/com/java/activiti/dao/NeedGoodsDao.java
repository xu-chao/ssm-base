package com.java.activiti.dao;

import com.java.activiti.model.NeedGoods;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface NeedGoodsDao {

	List<NeedGoods> needGoodsPage(Map<String, Object> map);

	int needGoodsCount(Map<String, Object> map);

	LinkedList<NeedGoods> needGoodsThroughPage(Map<String, Object> map);

	int needGoodsThroughCount(Map<String, Object> map);
	
	int deleteAllGoodsByNId(String nId);
	
	int addNeedGoods(NeedGoods needGoods);

	/**
	 * 为指定 nId 采购单号指派指定 goodsId 物料
	 * @param nId 采购单号Id
	 * @param goodsId 物料Id
	 */
	void insert(@Param("nId") String nId, @Param("goodsId") String goodsId, @Param("storageId") String storageId, @Param("purchaseId") String purchaseId, @Param("checkoutId") String checkoutId);

	/**
	 * 删除指定计划需求的采购单号
	 * @param nId 采购单号Id
	 */
	void deleteByNId(String nId);

	/**
	 * 为指定 nId 采购单号指派指定 goodsId 物料
	 * @param nId 采购单号Id
	 * @param goodsId 物料Id
	 */
	void updateStorageId(@Param("nId") String nId, @Param("goodsId") String goodsId,@Param("storageId") String storageId);

	/**
	 * 为指定 nId 采购单号指派指定 goodsId 物料
	 * @param nId 采购单号Id
	 * @param goodsId 物料Id
	 */
	void updatePurchaseId(@Param("nId") String nId, @Param("goodsId") String goodsId,@Param("purchaseId") String purchaseId);

	/**
	 * 为指定 nId 采购单号指派指定 goodsId 物料
	 * @param nId 采购单号Id
	 * @param goodsId 物料Id
	 */
	void updateCheckoutId(@Param("nId") String nId, @Param("goodsId") String goodsId,@Param("checkoutId") String checkoutId);

	/**
	 * 获取指定 nId 对应计划需求拥有的采购单号
	 * @param nId 采购单号ID
	 * @return 返回 nId 指定计划需求的采购单号
	 */
	List<NeedGoods> selectNeedGoods(String nId);

	/**
	 * 获取指定 nId 对应计划需求拥有的采购单号
	 * @param nId 采购单号ID
	 * @return 返回 nId 指定计划需求的采购单号
	 */
	List<NeedGoods> selectNeedGoodsList(String nId);

	NeedGoods findNeedGoodsByPurchaseId(String purchaseId);

	int updateGoods(@Param("nId_tt") String nId_tt, @Param("goodsId_tt") String goodsId_tt, @Param("goodsId") String goodsId);
}
