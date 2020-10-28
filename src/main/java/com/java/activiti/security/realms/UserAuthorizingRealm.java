package com.java.activiti.security.realms;

import com.java.activiti.exception.UserInfoServiceException;
import com.java.activiti.model.MemberShip;
import com.java.activiti.model.UserDept;
import com.java.activiti.model.UserPark;
import com.java.activiti.pojo.UserInfoVO;
import com.java.activiti.pojo.wms.UserWarehouse;
import com.java.activiti.security.service.UserInfoService;
import com.java.activiti.security.util.EncryptingModel;
import com.java.activiti.service.AccessRecordService;
import com.java.activiti.service.MemberShipService;
import com.java.activiti.service.UserDeptService;
import com.java.activiti.service.UserParkService;
import com.java.activiti.service.wms.UserWarehouseService;
import com.java.activiti.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * �û�����֤����Ȩ
 *
 * @author Xu
 * @since 2019/7/11.
 */
public class UserAuthorizingRealm extends AuthorizingRealm {
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private EncryptingModel encryptingModel;
    @Resource
    private MemberShipService memberShipService;
    @Resource
    private UserDeptService userDeptService;
    @Resource
    private UserParkService userParkService;
    @Resource
    private UserWarehouseService userWarehouseService;

    /**
     * ���û����н�ɫ��Ȩ
     *
     * @param principalCollection �û���Ϣ
     * @return �����û���Ȩ��Ϣ
     */
    @SuppressWarnings("unchecked")
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // ��������û���ɫ�� Set
        Set<String> roles = new HashSet<>();

        //��ȡ�û���ɫ
        Object principal = principalCollection.getPrimaryPrincipal();
        if (principal instanceof String) {
            String userID = (String) principal;
            if (StringUtils.isNumeric(userID)) {
                try {
                    UserInfoVO userInfo = userInfoService.getUserInfo(userID);
                    if (userInfo != null) {
                        // �����û���ɫ
                        roles.addAll(userInfo.getGroup());
                    }
                } catch (UserInfoServiceException e) {
                    // do logger
                }
            }
        }

        return new SimpleAuthorizationInfo(roles);
    }

    /**
     * ���û�������֤
     *
     * @param authenticationToken �û�ƾ֤
     * @return �����û�����֤��Ϣ
     * @throws AuthenticationException �û���֤�쳣��Ϣ
     * Realm����֤�������Զ���token���룬�Ƚ�token�����ݿ�������Ƿ�ƥ��
     * ��֤�߼����ȸ����û�����ѯ�û���
     * �����ѯ���Ļ��ٽ���ѯ�����û���������ŵ�SimpleAuthenticationInfo�����У�
     * Shiro���Զ������û����������Ͳ�ѯ�����������ƥ�䣬���ƥ�䲻�Ͼͻ��׳��쳣��
     * ƥ����֮��ͻ�ִ��doGetAuthorizationInfo()������Ӧ��Ȩ����֤��
     */
    @SuppressWarnings("unchecked")
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws
            AuthenticationException {

        String realmName = getName();
        String credentials = "";
        Map<String,Object> map=new HashMap<String, Object>();

        // ��ȡ�û�����Ӧ���û��˻���Ϣ
        try {
            UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
            String principal = usernamePasswordToken.getUsername();

            if (!StringUtils.isNumeric(principal))
                throw new AuthenticationException();
            String userID = principal;
            UserInfoVO userInfoVO = userInfoService.getUserInfo(userID);

            if (userInfoVO != null && (userInfoVO._1 == userInfoVO.getStatus())) {
                Subject currentSubject = SecurityUtils.getSubject();
                Session session = currentSubject.getSession();

                userInfoVO.setLastLoginTime(new Date());
                userInfoService.updateUserInfo(userInfoVO);

                //��ȡ���û���������ɫ
                map.put("userName", userID);
                map.put("password", userInfoVO.getPassword());
                if(userInfoVO.getGroup().size() != 0){
                    map.put("groupId", userInfoVO.getGroup().get(0));
                }else {
                    throw new DisabledAccountException("�ʺ��޽�ɫ");
                }
                if(userInfoVO.getDept().size() != 0){
                    map.put("deptID",userInfoVO.getDept().get(0));
                }else {
                    throw new DisabledAccountException("�ʺ��޲���");
                }
                if(userInfoVO.getPark().size() != 0){
                    map.put("parkID",userInfoVO.getPark().get(0));
                }else {
                    throw new DisabledAccountException("�ʺ��޹�԰");
                }
                if(userInfoVO.getWarehouse().size() != 0){
                    map.put("whId",1);
                }else {
                    throw new DisabledAccountException("�ʺ��޲ֿ�");
                }
                MemberShip sessionInfo=memberShipService.sessionInfo(map);
                UserDept sessionDept=userDeptService.sessionDept(map);
                UserPark sessionPark=userParkService.sessionPark(map);
                UserWarehouse sessionWarehouse=userWarehouseService.sessionWarehouse(map);
                session.setAttribute("sessionInfo",sessionInfo);
                session.setAttribute("sessionDept",sessionDept);
                session.setAttribute("sessionPark",sessionPark);
                session.setAttribute("sessionWarehouse",sessionWarehouse);
                session.setAttribute("sessionFile","http://localhost/fangteFile");
                // �����֤���������д���
                String checkCode = (String) session.getAttribute("checkCode");
                String password = userInfoVO.getPassword();
                if (checkCode != null && password != null) {
                    checkCode = checkCode.toUpperCase();
                    credentials = encryptingModel.MD5(password + checkCode);
                }
            }else if(userInfoVO == null){
                throw new UnknownAccountException();
            }else if(userInfoVO._0 == (userInfoVO.getStatus())){
                throw new DisabledAccountException("�ʺ��Ѿ���ֹ��¼��");
            }
            //�ȶ��˺�����
            return new SimpleAuthenticationInfo(principal, credentials, realmName);

        } catch (UserInfoServiceException | NoSuchAlgorithmException e) {
            throw new AuthenticationException();
        }
    }

//    /**
//     * ��յ�ǰ�û�Ȩ����Ϣ
//     */
//    public  void clearCachedAuthorizationInfo() {
//        PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
//        SimplePrincipalCollection principals = new SimplePrincipalCollection(
//                principalCollection, getName());
//        super.clearCachedAuthorizationInfo(principals);
//    }
//    /**
//     * ָ��principalCollection ���
//     */
//    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
//        SimplePrincipalCollection principals = new SimplePrincipalCollection(
//                principalCollection, getName());
//        super.clearCachedAuthorizationInfo(principals);
//    }
}
