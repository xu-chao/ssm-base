package com.java.activiti.dao;

import com.java.activiti.model.Goods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GoodsDao {

    Goods findById(String goodsId);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int goodsCount(Map<String, Object> map);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Goods> goodsPage(Map<String, Object> map);

    /**
     *
     * @Title: selectGoodsNameLike
     * @Description: ��ѯ���ϣ��Զ���ȫ
     * @param goodsName
     * @return
     */
    List<Goods> selectGoodsNameLike(@Param("goodsName") String goodsName);

    /**
     *
     * @Title: selectGoodsCodeLike
     * @Description: ��ѯ�ͺ�/ͼ�ţ��Զ���ȫ
     * @param goodsCode
     * @return
     */
    List<Goods> selectGoodsCodeLike(@Param("goodsCode") String goodsCode);

    /**
     *
     * @Title: selectGoodsTypeLike
     * @Description: ��ѯ���ͣ��Զ���ȫ
     * @param goodsType
     * @return
     */
    List<Goods> selectGoodsTypeLike(@Param("goodsType") String goodsType);
    /**
     *
     * @Title: selectGoodsType
     * @Description: ��ѯ���ϣ��Զ���ȫ
     * @return
     */
    List<String> selectGoodsTypeAll();

    List<Goods> selectALL();

    int addGoods(Goods goods);

    int updateGoods(Goods goods);

    int deleteGoodsById(List<String> id);

    List<Goods> findGoods(Goods goods);

    /**
     *
     * @Title: selectGoodsByGoodsID
     * @Description: ֤��ID��ѯ��Ӧ�������
     * @author: ��
     * @param goodsId ����ID
     * @return
     */
    List<Goods> selectGoodsByGoodsID(String goodsId);

    Goods findGoodsByExcel(Goods goods);

}
