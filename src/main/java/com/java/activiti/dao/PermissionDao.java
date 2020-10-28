package com.java.activiti.dao;

import com.java.activiti.model.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PermissionDao {

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

    List<Permission> findByGroupId(String groupID);

    List<Permission> selectAllPermission();

    /**
     *
     * @Title: deleteAllocationdByGid
     * @Description: 根据groupID删除拥有的角色信息
     * @param groupID void
     * @author 许超
     * @date 2019年8月13日
     */
    public void deleteAllocationdByGid(@Param("groupID") String groupID);

    /**
     *
     * @Title: insertGroupAllocation
     * @Description: 新增角色权限关系
     * @param allocationID
     * @param groupID void
     * @author 许超
     * @date 2019年8月13日
     */
    public void insertGroupAllocation(@Param("allocationID") String allocationID, @Param("groupID") String groupID);

    public int addPermission(Permission permission);

    public Permission findById(String permissionName);

    public int updatePermission(Permission permission);

    public int deletePermission(List<String> id);
}
