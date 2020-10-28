package com.java.activiti.dao.swylsb;

import com.java.activiti.model.swylsb.Swylsb;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface SwylsbDao {

    Swylsb findById(int ID);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int swylsbCount(Map<String, Object> map);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Swylsb> swylsbPage(Map<String, Object> map);

    /**
     *
     * @Title: selectSupplierName
     * @Description: ��ѯ���ϣ��Զ���ȫ
     * @param sbName
     * @return
     */
    List<Swylsb> selectSwylsbName(@Param("sbName") String sbName);

    List<Swylsb> selectALL();

    int addSwylsb(Swylsb swylsb);

    int updateSwylsb(Swylsb swylsb);

    int deleteSwylsbById(List<String> id);

    List<Swylsb> findSwylsb(Swylsb swylsb);

    /**
     *
     * @Title: selectSupplierBySupplierID
     * @Description: ֤��ID��ѯ��Ӧ�������
     * @author: ��
     * @param ID ��Ӧ��ID
     * @return
     */
    List<Swylsb> selectSwylsbBySwylsbID(int ID);

    /**
     *
     * @Title: selectGoodsCodeLike
     * @Description: ��ѯ�ͺ�/ͼ�ţ��Զ���ȫ
     * @param SUPPLIER_PERSON
     * @return
     */
    //List<swylsb> selectSUPPLIER_PERSONLike(@Param("SUPPLIER_PERSON") String SUPPLIER_PERSON);

    /**
     * ����
     * @author xuchao
     * @description ��ѯ���й�Ӧ��
     * @date 2019/12/30 0:08
     * @Version     1.0
     */
    List<Swylsb> findAllSwylsb();
}
