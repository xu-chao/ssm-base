package com.java.activiti.dao;

import com.java.activiti.model.Contact;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ContactDao {

    @Select("select * from act_id_contact where userId1=#{id}")
    @Results({
            @Result(property = "userChat1", column = "userId1", one = @One(select = "com.java.activiti.dao.UserChatDao.get")),
            //column是执行one = @One(select = "model.mapper.UserInfoMapper.get"))的传入
            @Result(property = "userChat2", column = "userId2", one = @One(select = "com.java.activiti.dao.UserChatDao.get"))
    })
    public List<Contact> listByUserId(String id);

    @Insert("insert into act_id_contact (userId1,userId2) values (#{userId},#{addUserId})")
    public void join(@Param("userId") String userId, @Param("addUserId") String addUserId);
}
