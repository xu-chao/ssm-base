package com.java.activiti.dao;

import com.java.activiti.pojo.UserChat;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserChatDao {

    @Insert("insert into act_id_user (ALLNAME_,PWD_,PICTURE_ID_,EMAIL_) values (#{userName},#{password},#{avatarUrl},#{email})")
    void insert(UserChat userChat);

    @Update("update act_id_user set ALLNAME_=#{userName},PWD_=#{password},PICTURE_ID_=#{avatarUrl} where ID_=#{userId}")
    void update(UserChat userChat);

    @Delete("delete from act_id_user where ID_= #{id}")
    void delete(String id);

    @Select("select ID_ as userId,ALLNAME_ as userName,PWD_ as password,PICTURE_ID_ as avatarUrl,EMAIL_ as email from act_id_user where ID_=#{id}")//���ﲻ�ܻ�ȡ�����ֶ� ���ﷵ�ص�Ӧ����һ���򵥵�UserInfo����
    UserChat get(String id);

    @Select("select ID_ as userId,ALLNAME_ as userName,PWD_ as password,PICTURE_ID_ as avatarUrl,EMAIL_ as email from act_id_user")
    List<UserChat> listAll();

    @Select("select ID_ as userId,ALLNAME_ as userName,PWD_ as password,PICTURE_ID_ as avatarUrl,EMAIL_ as email from act_id_user where ALLNAME_=#{username}")
    UserChat getByUsername(String username);//�����û���¼���Ի�ȡ����  �����ʼ�ҲӦʹ������


    @Select("select ID_ as userId,ALLNAME_ as userName,PWD_ as password,PICTURE_ID_ as avatarUrl,EMAIL_ as email from act_id_user where ID_=#{id}")
    @Results({
            @Result(property = "userId", column = "userId"),
            @Result(property = "friendList", javaType = List.class, column = "userId",
                    many = @Many(select = "com.java.activiti.dao.ContactDao.listByUserId")),
            @Result(property = "groupList", javaType = List.class, column = "userId",
                    many = @Many(select = "com.java.activiti.dao.BelongDao.listByUserId")),
            @Result(property = "messageList", javaType = List.class, column = "userId",
                    many = @Many(select = "com.java.activiti.dao.MessageInfoDao.listByUserId"))
    })
    UserChat getByUserId(String id);
}
