package com.java.activiti.security.service;

import com.java.activiti.exception.UserInfoServiceException;
import com.java.activiti.pojo.UserInfoVO;

import java.util.List;
import java.util.Set;

/**
 * �û��˻���Ϣ service
 * @author XU
 * @since 2019/7/11.
 */
public interface UserInfoService {

    /**
     * ��ȡָ���û�ID��Ӧ���û��˻���Ϣ
     * @param userID �û�ID
     * @return �����û��˻���Ϣ
     */
    UserInfoVO getUserInfo(String userID) throws UserInfoServiceException;

    /**
     * ��ȡָ�� userName ��Ӧ���û��˻���Ϣ
     * @param userName �û���
     * @return �����û��˻���Ϣ
     */
    UserInfoVO getUserNameInfo(String userName) throws UserInfoServiceException;

    /**
     * ��ȡ�����û��˻���Ϣ
     * @return �������е��û��˻���Ϣ
     */
    List<UserInfoVO> getAllUserInfo() throws UserInfoServiceException;

    /**
     * �����û����˻���Ϣ
     * @param userInfoVO �û��˻���Ϣ
     */
    void updateUserInfo(UserInfoVO userInfoVO) throws UserInfoServiceException;

    /**
     * ɾ��ָ�� userID ���û��˻���Ϣ
     * @param userID ָ�����û�ID
     */
    void deleteUserInfo(String userID) throws UserInfoServiceException;

    /**
     * ���һ���û��˻���Ϣ
     * @param userInfoVO ��Ҫ��ӵ��û��˻���Ϣ
     */
    boolean insertUserInfo(UserInfoVO userInfoVO) throws UserInfoServiceException;

    /**
     * ��ȡ�û���Ȩ�޽�ɫ
     * @param userID �û� ID
     * @return ����һ���������û���ɫ�� Set�������û�û���κν�ɫ���򷵻�һ���������κ�Ԫ�ص� Set
     */
    Set<String> getUserRoles(String userID) throws UserInfoServiceException;
}
