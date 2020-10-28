package com.java.activiti.service;

import com.java.activiti.model.Permission;
import com.java.activiti.pojo.EasyUIOptionalDataListNode;
import com.java.activiti.pojo.GlobalResultVO;

import java.util.List;
import java.util.Map;

public interface PermissionService {

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Permission> permissionPage(Map<String,Object> map);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int permissionCount(Map<String,Object> map);

    List<Permission> findByGroupId(String id);

    /**
     *
     * @Title: findPermissionByGid
     * @Description: 获取角色菜单权限
    -- 1.根据角色id获取对应的权限菜单id,比如角色id roleuuid=1
    select role_menu.menuuuid from role,role_menu WHERE role_menu.roleuuid=1;
    -- 2.获取所有权限菜单(menuid,menuname)
    SELECT menuid,menuname FROM menu;
     * @param groupID
     * @return List<EasyUIOptionalTreeNode>
     * @author 许超
     * @date 2019年8月14日
     */
    List<EasyUIOptionalDataListNode> findPermissionByGid(String groupID);

    /**
     *
     * @Title: updateGroupAllocation
     * @Description: 更新角色权限
     * @param groupID
     * @param checkedIds
     * @return GlobalResultVO
     * @author 许超
     * @date 2019年8月13日
     */
    GlobalResultVO updateGroupAllocation(String groupID, String checkedIds);

    /**
     *
     * @Title: addPermission
     * @Description: 增加权限
     * @param permission
     * @return int
     * @author 许超
     * @date 2019年8月13日
     */
    int addPermission(Permission permission);

    /**
     *
     * @Title: findById
     * @Description: 通过ID查询权限
     * @param permissionName
     * @return Permission
     * @author 许超
     * @date 2019年8月13日
     */
    Permission findById(String permissionName);

    /**
     *
     * @Title: updatePermission
     * @Description: 更新用户权限
     * @param permission
     * @return Permission
     * @author 许超
     * @date 2019年8月13日
     */
    int updatePermission(Permission permission);

    /**
     *
     * @Title: deletePermission
     * @Description: 更新用户权限
     * @param id
     * @return int
     * @author 许超
     * @date 2019年8月13日
     */
    int deletePermission(List<String> id);
}
