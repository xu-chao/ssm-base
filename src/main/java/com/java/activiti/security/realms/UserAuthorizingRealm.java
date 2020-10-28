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
 * 用户的认证与授权
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
     * 对用户进行角色授权
     *
     * @param principalCollection 用户信息
     * @return 返回用户授权信息
     */
    @SuppressWarnings("unchecked")
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 创建存放用户角色的 Set
        Set<String> roles = new HashSet<>();

        //获取用户角色
        Object principal = principalCollection.getPrimaryPrincipal();
        if (principal instanceof String) {
            String userID = (String) principal;
            if (StringUtils.isNumeric(userID)) {
                try {
                    UserInfoVO userInfo = userInfoService.getUserInfo(userID);
                    if (userInfo != null) {
                        // 设置用户角色
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
     * 对用户进行认证
     *
     * @param authenticationToken 用户凭证
     * @return 返回用户的认证信息
     * @throws AuthenticationException 用户认证异常信息
     * Realm的认证方法，自动将token传入，比较token与数据库的数据是否匹配
     * 验证逻辑是先根据用户名查询用户，
     * 如果查询到的话再将查询到的用户名和密码放到SimpleAuthenticationInfo对象中，
     * Shiro会自动根据用户输入的密码和查询到的密码进行匹配，如果匹配不上就会抛出异常，
     * 匹配上之后就会执行doGetAuthorizationInfo()进行相应的权限验证。
     */
    @SuppressWarnings("unchecked")
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws
            AuthenticationException {

        String realmName = getName();
        String credentials = "";
        Map<String,Object> map=new HashMap<String, Object>();

        // 获取用户名对应的用户账户信息
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

                //获取该用户的所属角色
                map.put("userName", userID);
                map.put("password", userInfoVO.getPassword());
                if(userInfoVO.getGroup().size() != 0){
                    map.put("groupId", userInfoVO.getGroup().get(0));
                }else {
                    throw new DisabledAccountException("帐号无角色");
                }
                if(userInfoVO.getDept().size() != 0){
                    map.put("deptID",userInfoVO.getDept().get(0));
                }else {
                    throw new DisabledAccountException("帐号无部门");
                }
                if(userInfoVO.getPark().size() != 0){
                    map.put("parkID",userInfoVO.getPark().get(0));
                }else {
                    throw new DisabledAccountException("帐号无公园");
                }
                if(userInfoVO.getWarehouse().size() != 0){
                    map.put("whId",1);
                }else {
                    throw new DisabledAccountException("帐号无仓库");
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
                // 结合验证码对密码进行处理
                String checkCode = (String) session.getAttribute("checkCode");
                String password = userInfoVO.getPassword();
                if (checkCode != null && password != null) {
                    checkCode = checkCode.toUpperCase();
                    credentials = encryptingModel.MD5(password + checkCode);
                }
            }else if(userInfoVO == null){
                throw new UnknownAccountException();
            }else if(userInfoVO._0 == (userInfoVO.getStatus())){
                throw new DisabledAccountException("帐号已经禁止登录！");
            }
            //比对账号密码
            return new SimpleAuthenticationInfo(principal, credentials, realmName);

        } catch (UserInfoServiceException | NoSuchAlgorithmException e) {
            throw new AuthenticationException();
        }
    }

//    /**
//     * 清空当前用户权限信息
//     */
//    public  void clearCachedAuthorizationInfo() {
//        PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
//        SimplePrincipalCollection principals = new SimplePrincipalCollection(
//                principalCollection, getName());
//        super.clearCachedAuthorizationInfo(principals);
//    }
//    /**
//     * 指定principalCollection 清除
//     */
//    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
//        SimplePrincipalCollection principals = new SimplePrincipalCollection(
//                principalCollection, getName());
//        super.clearCachedAuthorizationInfo(principals);
//    }
}
