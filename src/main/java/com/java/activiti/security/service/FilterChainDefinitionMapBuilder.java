package com.java.activiti.security.service;

import com.java.activiti.dao.GroupPermissionDao;
import com.java.activiti.pojo.GroupPermissionVO;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * ��ȡ URL Ȩ����Ϣ������
 * @author XU
 * @since 2019/7/11.
 */
public class FilterChainDefinitionMapBuilder {
    @Resource
    private GroupPermissionDao groupPermissionDao;
    private StringBuilder builder = new StringBuilder();

    /**
     * ��ȡ��Ȩ��Ϣ
     * @return ������Ȩ��Ϣ�б�
     */
    public LinkedHashMap<String, String> builderFilterChainDefinitionMap(){
        LinkedHashMap<String, String> permissionMap = new LinkedHashMap<>();

        // �̶���Ȩ������
        permissionMap.put("/static/**", "anon");
        permissionMap.put("/page/**", "anon");
        permissionMap.put("/api/**", "anon");
        permissionMap.put("/modeler/**", "anon");
        permissionMap.put("/errorPage/**", "anon");
        permissionMap.put("/login", "anon");
        permissionMap.put("/main", "anon");
        permissionMap.put("/checkCode/**", "anon");

        // �ɱ仯��Ȩ������
        LinkedHashMap<String, String> permissions = getPermissionDataFromDB();
        if (permissions != null){
            permissionMap.putAll(permissions);
        }

        // ����Ȩ������
        permissionMap.put("/", "authc");

        permissionMap.forEach((s, s2) -> {System.out.println(s + ":" + s2);});

        return permissionMap;
    }

    /**
     * ��ȡ���������ݿ��е� URL Ȩ����Ϣ
     * @return �������б��������ݿ��е� URL ������Ϣ
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

                // �жϸ� url �Ƿ��Ѿ�����
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
