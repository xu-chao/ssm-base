package com.java.activiti.dao;

import com.java.activiti.model.MessageInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MessageInfoDao {

    @Insert("insert into act_id_message (fromUserId,toUserId,toGroupId,content,type,fileUrl,originalFilename,fileSize) values(#{fromUserId,jdbcType=VARCHAR },#{toUserId,jdbcType=VARCHAR},#{toGroupId,jdbcType=INTEGER},#{content,jdbcType=VARCHAR},#{type,jdbcType=VARCHAR},#{fileUrl,jdbcType=VARCHAR},#{originalFilename,jdbcType=VARCHAR},#{fileSize,jdbcType=VARCHAR})")
    public void insert(MessageInfo messageInfo);

    @Select("select * from act_id_message where fromUserId=#{id} and toUserId!=0 or toUserId=#{id} and fromUserId!=0")
    public List<MessageInfo> listByUserId(Integer id);//保证筛选到的都是SINGLE_SENDING

    @Select("select * from act_id_message where toGroupId=#{id}")
    public List<MessageInfo> listByGroupId(Integer id);
}
