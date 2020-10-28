package com.java.activiti.service.impl;

import com.java.activiti.dao.PermissionDao;
import com.java.activiti.model.Menu;
import com.java.activiti.model.Permission;
import com.java.activiti.pojo.EasyUIOptionalDataListNode;
import com.java.activiti.pojo.EasyUIOptionalTreeNode;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.PermissionService;
import com.java.activiti.util.aop.Operation;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("permission")
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionDao permissionDao;

    @Override
    public List<Permission> permissionPage(Map<String, Object> map) {
        return permissionDao.permissionPage(map);
    }

    @Override
    public int permissionCount(Map<String, Object> map) {
        return permissionDao.permissionCount(map);
    }

    public List<Permission> findByGroupId(String id){
        return permissionDao.findByGroupId(id);
    }

    @Override
    public List<EasyUIOptionalDataListNode> findPermissionByGid(String groupID) {
        // 1.根据角色id获取角色对应的权限id
        List<Permission> permissionList = permissionDao.findByGroupId(groupID);
        // 2.获取所有权限
        List<Permission> allPermissionList = permissionDao.selectAllPermission();
        // 3.当前角色对象对应的角色权限
        List<EasyUIOptionalDataListNode> dataList = new ArrayList<EasyUIOptionalDataListNode>();
        // 暂存权限
        EasyUIOptionalDataListNode d = null;
        // 权限遍历
        for (Permission allPermission : allPermissionList) {
            d = new EasyUIOptionalDataListNode();
            d.setId(allPermission.getId());
            d.setText(allPermission.getName());
            // 如果角色下包含有这个角色权限，让它勾选上
            for (Permission p  : permissionList) {
                if (d.getId() == (p.getId())) {
                    d.setChecked(true);
                }
            }
            dataList.add(d);
        }
        return dataList;
    }

    @Override
    public GlobalResultVO updateGroupAllocation(String groupID, String checkedIds) {
        try {
            // 清空角色下的权限
            permissionDao.deleteAllocationdByGid(groupID);
            // 权限角色id
            if (checkedIds != null) {
                String[] ids = checkedIds.split(",");
                for (String allocationID : ids) {
                    permissionDao.insertGroupAllocation(allocationID, groupID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GlobalResultVO.build(200, "权限设置成功");
    }

    /**
     * 新增权限
     * @param permission
     * @return
     */
    @Operation(value = "新增用户权限")
    public int addPermission(Permission permission){
        return permissionDao.addPermission(permission);
    }

    public Permission findById(String permissionName){
        return permissionDao.findById(permissionName);
    }

    /**
     * 修改用户
     * @param permission
     * @return
     */
    @Operation(value = "更新用户权限信息")
    public int updatePermission(Permission permission){
        return permissionDao.updatePermission(permission);
    }

    /**
     * 批量删除用户权限信息
     * @param id
     * @return
     */
    @Operation(value = "批量删除用户")
    public int deletePermission(List<String> id){
        return permissionDao.deletePermission(id);
    }
}
