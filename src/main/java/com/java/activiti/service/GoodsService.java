package com.java.activiti.service;

import com.java.activiti.model.Goods;
import com.java.activiti.pojo.Tree;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface GoodsService {

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Goods> goodsPage(Map<String, Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int goodsCount(Map<String, Object> map);

    /**
     *
     * @Title: findGoodsNameLike
     * @Description: like��ѯ ������
     * @return
     */
    List<Goods> findGoodsNameLike(String q);
    /**
     *
     * @Title: findGoodsCodeLike
     * @Description: like��ѯ �ͺ�/ͼ��
     * @return
     */
    List<Goods> findGoodsCodeLike(String q);
    /**
     *
     * @Title: findGoodsTypeLike
     * @Description: like��ѯ ����
     * @return
     */
    List<Goods> findGoodsTypeLike(String q);
    /**
     *
     * @Title: selectGoodsType
     * @Description: like��ѯ������
     * @return
     */
    List<Tree> selectGoodsType();

    /**
     * �޸�����
     * @param goods
     * @return
     */
    int updateGoods(Goods goods);

    /**
     * ��������
     * @param goods
     * @return
     */
    int addGoods(Goods goods);

    /**
     * ����ɾ������
     * @param id
     * @return
     */
    int deleteGoodsById(List<String> id);

    /**
     * �ж������Ƿ��Ѿ�����
     * @param goods
     * @return
     */
    List<Goods> findGoods(Goods goods);

    /**
     *
     * @Title: export
     * @Description: ����excel
     * @author: ��
     * @param os
     * @param map
     */
    void export(OutputStream os, Map<String, Object> map);

    /**
     *
     * @Title: goodsImport
     * @Description: ����excel
     * @author: ��
     * @param is
     * @throws IOException
     */
    void goodsImport(InputStream is) throws IOException;

    Goods selectGoodsById(String ECodeID);
}
