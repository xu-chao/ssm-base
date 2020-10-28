package com.java.activiti.security.service;

import com.java.activiti.dao.GroupPermissionDao;
import com.java.activiti.pojo.GroupPermissionVO;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 获取 URL 权限信息工厂类
 * @author XU
 * @since 2019/7/11.
 */
public class FilterChainDefinitionMapBuilder {
    @Resource
    private GroupPermissionDao groupPermissionDao;
    private StringBuilder builder = new StringBuilder();

    /**
     * 获取授权信息
     * @return 返回授权信息列表
     */
    public LinkedHashMap<String, String> builderFilterChainDefinitionMap(){
        LinkedHashMap<String, String> permissionMap = new LinkedHashMap<>();

        // 固定的权限配置
        permissionMap.put("/static/**", "anon");
        permissionMap.put("/page/**", "anon");
        permissionMap.put("/api/**", "anon");
        permissionMap.put("/modeler/**", "anon");
        permissionMap.put("/errorPage/**", "anon");
        permissionMap.put("/login", "anon");
        permissionMap.put("/main", "anon");
        permissionMap.put("/checkCode/**", "anon");

        // 可变化的权限配置
        LinkedHashMap<String, String> permissions = getPermissionDataFromDB();
        if (permissions != null){
            permissionMap.putAll(permissions);
        }

        // 其余权限配置
        permissionMap.put("/", "authc");

        permissionMap.forEach((s, s2) -> {System.out.println(s + ":" + s2);});

        return permissionMap;
    }

    /**
     * 获取配置在数据库中的 URL 权限信息
     * @return 返回所有保存在数据库中的 URL 保存信息
     */
    private LinkedHashMap<String, String> getPermissionDataFromDB(){
        LinkedHashMap<String, String> permissionData = null;

        List<GroupPermissionVO> groupPermissionVOS = groupPermissionDao.selectAll();
        if (groupPermissionVOS != null){
            permissionData = new LinkedHashMap<>(groupPermissionVOS.size());
            String url;
            String role;
            String permission;
            for (GroupPermissionVO groupPermissionVO : groupPermissionVOS){
                url = groupPermissionVO.getUrl();
                role = groupPermissionVO.getGroup();

                // 判断该 url 是否已经存在
                if (permissionData.containsKey(url)){
                    builder.delete(0, builder.length());
                    builder.append(permissionData.get(url));
                    builder.insert(builder.length() - 1, ",");
                    builder.insert(builder.length() - 1, role);
                }else{
                    builder.delete(0, builder.length());
                    builder.append("authc,roles[").append(role).append("]");
                }
                permission = builder.toString();
                // System.out.println(url + ":" + permission);
                permissionData.put(url, permission);
            }
        }

        return permissionData;
    }

}
