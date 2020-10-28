package com.java.activiti.dao;

import com.java.activiti.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户账户信息 Mapper
 *
 * @author XU
 * @since 2019/7/11.
 */
public interface UserInfoDao {

    /**
     * 选择指定 id 的 user 信息
     *
     * @param userID 用户ID
     * @return 返回指定 userID 对应的 user 信息
     */
    User selectByUserID(String userID);

    /**
     * 选择指定 userName 的 user 信息
     *
     * @param userName 用户名
     * @return 返回指定 userName 对应的 user 信息
     */
    User selectByName(String userName);

    /**
     * 选择全部的 user 信息
     *
     * @return 返回所有的 user 信息
     */
    List<User> selectAll();


    /**
     * 更新 user 对象信息
     *
     * @param user 更新 user 对象信息
     */
    void updateUser(User user);

    /**
     * 删除指定 id 的user 信息
     *
     * @param id 用户ID
     */
    void deleteById(String id);

    /**
     * 插入一个 user 对象信息
     * 不需指定对象的主键id，数据库自动生成
     *
     * @param user 需要插入的用户信息
     */
    void insert(User user);
}
