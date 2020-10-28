package com.java.activiti.dao;

import com.java.activiti.model.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PermissionDao {

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

    List<Permission> findByGroupId(String groupID);

    List<Permission> selectAllPermission();

    /**
     *
     * @Title: deleteAllocationdByGid
     * @Description: ����groupIDɾ��ӵ�еĽ�ɫ��Ϣ
     * @param groupID void
     * @author ��
     * @date 2019��8��13��
     */
    public void deleteAllocationdByGid(@Param("groupID") String groupID);

    /**
     *
     * @Title: insertGroupAllocation
     * @Description: ������ɫȨ�޹�ϵ
     * @param allocationID
     * @param groupID void
     * @author ��
     * @date 2019��8��13��
     */
    public void insertGroupAllocation(@Param("allocationID") String allocationID, @Param("groupID") String groupID);

    public int addPermission(Permission permission);

    public Permission findById(String permissionName);

    public int updatePermission(Permission permission);

    public int deletePermission(List<String> id);
}
