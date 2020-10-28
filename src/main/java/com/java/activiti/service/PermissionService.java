package com.java.activiti.service;

import com.java.activiti.model.Permission;
import com.java.activiti.pojo.EasyUIOptionalDataListNode;
import com.java.activiti.pojo.GlobalResultVO;

import java.util.List;
import java.util.Map;

public interface PermissionService {

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Permission> permissionPage(Map<String,Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int permissionCount(Map<String,Object> map);

    List<Permission> findByGroupId(String id);

    /**
     *
     * @Title: findPermissionByGid
     * @Description: ��ȡ��ɫ�˵�Ȩ��
    -- 1.���ݽ�ɫid��ȡ��Ӧ��Ȩ�޲˵�id,�����ɫid roleuuid=1
    select role_menu.menuuuid from role,role_menu WHERE role_menu.roleuuid=1;
    -- 2.��ȡ����Ȩ�޲˵�(menuid,menuname)
    SELECT menuid,menuname FROM menu;
     * @param groupID
     * @return List<EasyUIOptionalTreeNode>
     * @author ��
     * @date 2019��8��14��
     */
    List<EasyUIOptionalDataListNode> findPermissionByGid(String groupID);

    /**
     *
     * @Title: updateGroupAllocation
     * @Description: ���½�ɫȨ��
     * @param groupID
     * @param checkedIds
     * @return GlobalResultVO
     * @author ��
     * @date 2019��8��13��
     */
    GlobalResultVO updateGroupAllocation(String groupID, String checkedIds);

    /**
     *
     * @Title: addPermission
     * @Description: ����Ȩ��
     * @param permission
     * @return int
     * @author ��
     * @date 2019��8��13��
     */
    int addPermission(Permission permission);

    /**
     *
     * @Title: findById
     * @Description: ͨ��ID��ѯȨ��
     * @param permissionName
     * @return Permission
     * @author ��
     * @date 2019��8��13��
     */
    Permission findById(String permissionName);

    /**
     *
     * @Title: updatePermission
     * @Description: �����û�Ȩ��
     * @param permission
     * @return Permission
     * @author ��
     * @date 2019��8��13��
     */
    int updatePermission(Permission permission);

    /**
     *
     * @Title: deletePermission
     * @Description: �����û�Ȩ��
     * @param id
     * @return int
     * @author ��
     * @date 2019��8��13��
     */
    int deletePermission(List<String> id);
}
