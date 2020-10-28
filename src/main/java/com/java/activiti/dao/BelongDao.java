package com.java.activiti.dao;

import com.java.activiti.model.Belong;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BelongDao {

    @Select("select * from act_id_belong where groupid=#{id}")
    @Results({
            @Result(property = "userChat", column = "userId", one = @One(select = "com.java.activiti.dao.UserChatDao.get")),
            //column是执行one = @One(select = "com.java.activiti.dao.UserChatDao.get"))的传入
            @Result(property = "groupInfo", column = "groupId", one = @One(select = "com.java.activiti.dao.GroupInfoDao.getByGroupId"))
    })
    public List<Belong> listByGroupId(int id);

    @Select("select * from act_id_belong where userId=#{id}")
    @Results({
            @Result(property = "userChat",column = "userId",one = @One(select = "com.java.activiti.dao.UserChatDao.get")),
            //column是执行one = @One(select = "com.java.activiti.dao.UserChatDao.get"))的传入
            @Result(property = "groupInfo",column = "groupId",one = @One(select = "com.java.activiti.dao.GroupInfoDao.getByGroupId_1"))
    })
    public List<Belong> listByUserId(int id);
}
