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
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int swylsbCount(Map<String, Object> map);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Swylsb> swylsbPage(Map<String, Object> map);

    /**
     *
     * @Title: selectSupplierName
     * @Description: 查询物料，自动补全
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
     * @Description: 证据ID查询对应存货编码
     * @author: 许超
     * @param ID 供应商ID
     * @return
     */
    List<Swylsb> selectSwylsbBySwylsbID(int ID);

    /**
     *
     * @Title: selectGoodsCodeLike
     * @Description: 查询型号/图号，自动补全
     * @param SUPPLIER_PERSON
     * @return
     */
    //List<swylsb> selectSUPPLIER_PERSONLike(@Param("SUPPLIER_PERSON") String SUPPLIER_PERSON);

    /**
     * 参数
     * @author xuchao
     * @description 查询所有供应商
     * @date 2019/12/30 0:08
     * @Version     1.0
     */
    List<Swylsb> findAllSwylsb();
}
