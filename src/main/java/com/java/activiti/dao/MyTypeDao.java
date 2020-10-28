package com.java.activiti.dao;

import com.java.activiti.model.MyType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MyTypeDao {

//    MyType findByIDCode(@Param("typeID") int typeID, @Param("typeCode")String typeCode);
/**
 * @author      LIUHD
 * ���� typeID
 * @description findByID
 * @date        2020/1/13 16:08
 * @Version     1.0
 */
    MyType findById(@Param("typeID") int typeID);
/**
 * @author      LIUHD
 * ���� typeCode
 * @description findMyTypeByTypeCode
 * @date        2020/1/13 16:09
 * @Version     1.0
 */
    List<MyType> findMyTypeByTypeCode(@Param("typeCode") String typeCode);
    /**
     *
     * @Title: selectMyTypeByTypeCode
     * @Description: ����ö�����ͣ���ʾö������
     * @author: LIUHD
     * @param typeCode
     * @return
     */
    List<MyType> selectMyTypeByTypeCode(@Param("typeCode") String typeCode);

//    MyType findByIDCode(Map<String, Object> map);

}
