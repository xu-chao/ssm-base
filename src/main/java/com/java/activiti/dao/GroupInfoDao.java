package com.java.activiti.dao;

import com.java.activiti.model.GroupInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GroupInfoDao {

    @Insert("insert into act_id_groupinfo (groupId,groupName,groupAvatarUrl) values (#{groupId},#{groupName},#{groupAvatarUrl})")
    void insert(GroupInfo groupInfo);

    @Update("update act_id_groupinfo set groupName=#{groupName},groupAvatarUrl=#{groupAvatarUrl}  where groupId=#{groupId}")
    void update(GroupInfo groupInfo);

    @Delete("delete from act_id_groupinfo where groupId= #{id}")
    void delete(long id);

    @Select("select * from act_id_groupinfo where groupId=#{id}")
    GroupInfo get(Integer id);

    @Select("select * from act_id_groupinfo")
    @Results({
            @Result(property = "groupId",column = "groupId"),
            @Result(property = "members",javaType = List.class,column ="groupId",
                    many =@Many(select = "com.java.activiti.dao.BelongDao.listByGroupId"))
    })
    List<GroupInfo> listAll();

    @Select("select * from act_id_groupinfo where groupId = #{id}")
    @Results({
            @Result(property = "groupId", column = "groupId"),
            @Result(property = "members", javaType = List.class, column = "groupId",
                    many = @Many(select = "com.java.activiti.dao.BelongDao.listByGroupId")),
            @Result(property = "messageList",javaType = List.class,column = "groupId",
                    many = @Many(select = "com.java.activiti.dao.MessageInfoDao.listByGroupId"))
    })
    GroupInfo getByGroupId(Integer id);


    @Select("select * from act_id_groupinfo where groupId = #{id}")
    @Results({
            @Result(property = "groupId", column = "groupId"),
            @Result(property = "messageList",javaType = List.class,column = "groupId",
                    many = @Many(select = "com.java.activiti.dao.MessageInfoDao.listByGroupId"))
    })
    GroupInfo getByGroupId_1(Integer id);
}
