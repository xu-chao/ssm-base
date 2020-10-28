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
        // 1.���ݽ�ɫid��ȡ��ɫ��Ӧ��Ȩ��id
        List<Permission> permissionList = permissionDao.findByGroupId(groupID);
        // 2.��ȡ����Ȩ��
        List<Permission> allPermissionList = permissionDao.selectAllPermission();
        // 3.��ǰ��ɫ�����Ӧ�Ľ�ɫȨ��
        List<EasyUIOptionalDataListNode> dataList = new ArrayList<EasyUIOptionalDataListNode>();
        // �ݴ�Ȩ��
        EasyUIOptionalDataListNode d = null;
        // Ȩ�ޱ���
        for (Permission allPermission : allPermissionList) {
            d = new EasyUIOptionalDataListNode();
            d.setId(allPermission.getId());
            d.setText(allPermission.getName());
            // �����ɫ�°����������ɫȨ�ޣ�������ѡ��
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
            // ��ս�ɫ�µ�Ȩ��
            permissionDao.deleteAllocationdByGid(groupID);
            // Ȩ�޽�ɫid
            if (checkedIds != null) {
                String[] ids = checkedIds.split(",");
                for (String allocationID : ids) {
                    permissionDao.insertGroupAllocation(allocationID, groupID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GlobalResultVO.build(200, "Ȩ�����óɹ�");
    }

    /**
     * ����Ȩ��
     * @param permission
     * @return
     */
    @Operation(value = "�����û�Ȩ��")
    public int addPermission(Permission permission){
        return permissionDao.addPermission(permission);
    }

    public Permission findById(String permissionName){
        return permissionDao.findById(permissionName);
    }

    /**
     * �޸��û�
     * @param permission
     * @return
     */
    @Operation(value = "�����û�Ȩ����Ϣ")
    public int updatePermission(Permission permission){
        return permissionDao.updatePermission(permission);
    }

    /**
     * ����ɾ���û�Ȩ����Ϣ
     * @param id
     * @return
     */
    @Operation(value = "����ɾ���û�")
    public int deletePermission(List<String> id){
        return permissionDao.deletePermission(id);
    }
}
