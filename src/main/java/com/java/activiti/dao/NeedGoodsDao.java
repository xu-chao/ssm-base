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
	 * Ϊָ�� nId �ɹ�����ָ��ָ�� goodsId ����
	 * @param nId �ɹ�����Id
	 * @param goodsId ����Id
	 */
	void insert(@Param("nId") String nId, @Param("goodsId") String goodsId, @Param("storageId") String storageId, @Param("purchaseId") String purchaseId, @Param("checkoutId") String checkoutId);

	/**
	 * ɾ��ָ���ƻ�����Ĳɹ�����
	 * @param nId �ɹ�����Id
	 */
	void deleteByNId(String nId);

	/**
	 * Ϊָ�� nId �ɹ�����ָ��ָ�� goodsId ����
	 * @param nId �ɹ�����Id
	 * @param goodsId ����Id
	 */
	void updateStorageId(@Param("nId") String nId, @Param("goodsId") String goodsId,@Param("storageId") String storageId);

	/**
	 * Ϊָ�� nId �ɹ�����ָ��ָ�� goodsId ����
	 * @param nId �ɹ�����Id
	 * @param goodsId ����Id
	 */
	void updatePurchaseId(@Param("nId") String nId, @Param("goodsId") String goodsId,@Param("purchaseId") String purchaseId);

	/**
	 * Ϊָ�� nId �ɹ�����ָ��ָ�� goodsId ����
	 * @param nId �ɹ�����Id
	 * @param goodsId ����Id
	 */
	void updateCheckoutId(@Param("nId") String nId, @Param("goodsId") String goodsId,@Param("checkoutId") String checkoutId);

	/**
	 * ��ȡָ�� nId ��Ӧ�ƻ�����ӵ�еĲɹ�����
	 * @param nId �ɹ�����ID
	 * @return ���� nId ָ���ƻ�����Ĳɹ�����
	 */
	List<NeedGoods> selectNeedGoods(String nId);

	/**
	 * ��ȡָ�� nId ��Ӧ�ƻ�����ӵ�еĲɹ�����
	 * @param nId �ɹ�����ID
	 * @return ���� nId ָ���ƻ�����Ĳɹ�����
	 */
	List<NeedGoods> selectNeedGoodsList(String nId);

	NeedGoods findNeedGoodsByPurchaseId(String purchaseId);

	int updateGoods(@Param("nId_tt") String nId_tt, @Param("goodsId_tt") String goodsId_tt, @Param("goodsId") String goodsId);
}
