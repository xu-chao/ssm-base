package com.java.activiti.service.wms;

import com.java.activiti.model.wms.Wmsrecordstorage;
import com.java.activiti.model.wms.Wmssupplier;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface WmsrecordstorageService {

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Wmsrecordstorage> wmsrecordstoragePage(Map<String, Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int wmsrecordstorageCount(Map<String, Object> map);

    /**
     * 货物入库操作
     *
     * @param goodsID      货物ID
     * @param repositoryID 入库仓库ID
     * @param number       入库数量
     * @return 返回一个boolean 值，若值为true表示入库成功，否则表示入库失败
     */
    Map<String,Object>  stockInOperation(String goodsID, Integer repositoryID, long number) throws Exception;

    /**
     * 为指定的货物库存记录增加指定数目
     *
     * @param goodsID      货物ID
     * @param repositoryID 仓库ID
     * @param number       增加的数量
     * @return 返回一个 boolean 值，若值为true表示数目增加成功，否则表示增加失败
     */
    boolean storageIncrease(String goodsID, Integer repositoryID, long number) throws Exception;



    /**
     * 为指定的货物库存记录减少指定的数目
     *
     * @param goodsID      货物ID
     * @param repositoryID 仓库ID
     * @param number       减少的数量
     * @return 返回一个 boolean 值，若值为 true 表示数目减少成功，否则表示增加失败
     */
    boolean storageDecrease(String goodsID, Integer repositoryID, long number) throws Exception;

    /**
     * 更新一条库存记录
     *
     * @param goodsID      指定的货物ID
     * @param repositoryID 指定的仓库ID
     * @param number       更新的库存数量
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    boolean updateStorage(String goodsID, Integer repositoryID, long number) throws Exception;

    /**
     * 添加一条库存记录
     *
     * @param goodsID      指定的货物ID
     * @param repositoryID 指定的仓库ID
     * @param number       库存数量
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    boolean addNewStorage(String goodsID, Integer repositoryID, long number) throws Exception;

}
