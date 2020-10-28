package com.java.activiti.dao;

import com.java.activiti.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * �û��˻���Ϣ Mapper
 *
 * @author XU
 * @since 2019/7/11.
 */
public interface UserInfoDao {

    /**
     * ѡ��ָ�� id �� user ��Ϣ
     *
     * @param userID �û�ID
     * @return ����ָ�� userID ��Ӧ�� user ��Ϣ
     */
    User selectByUserID(String userID);

    /**
     * ѡ��ָ�� userName �� user ��Ϣ
     *
     * @param userName �û���
     * @return ����ָ�� userName ��Ӧ�� user ��Ϣ
     */
    User selectByName(String userName);

    /**
     * ѡ��ȫ���� user ��Ϣ
     *
     * @return �������е� user ��Ϣ
     */
    List<User> selectAll();


    /**
     * ���� user ������Ϣ
     *
     * @param user ���� user ������Ϣ
     */
    void updateUser(User user);

    /**
     * ɾ��ָ�� id ��user ��Ϣ
     *
     * @param id �û�ID
     */
    void deleteById(String id);

    /**
     * ����һ�� user ������Ϣ
     * ����ָ�����������id�����ݿ��Զ�����
     *
     * @param user ��Ҫ������û���Ϣ
     */
    void insert(User user);
}
