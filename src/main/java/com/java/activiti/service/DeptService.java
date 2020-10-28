package com.java.activiti.service;

import com.java.activiti.model.Dept;
import com.java.activiti.model.Menu;
import com.java.activiti.model.User;
import com.java.activiti.pojo.DeptTree;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.pojo.Tree;

import java.util.List;

public interface DeptService {

    /**
     *
     * @Title: findDeptList
     * @Description: ������������
     * @author: ��
     * @return
     */
    List<DeptTree> findDeptList();

    /**
     *
     * @Title: findDeptById
     * @Description: ���ݲ���id���Ҳ��ţ���ʾ��������
     * @author: ��
     * @param deptID
     * @return
     */
    List<Dept> findDeptById(String deptID);
    /**
     *
     * @Title: findDeptById
     * @Description: ���ݲ���id�����Ӳ��ţ���ʾ�Ӳ�������
     * @author: ��
     * @param deptID
     * @return
     */
    List<Dept> selectChild(String deptID);

    /**
     *
     * @Title: findDeptByUserId
     * @Description: �����û�id���Ҳ��ţ���ʾ��������
     * @author: ��
     * @param userID
     * @return
     */
    Dept findDeptByUserId(String userID);

    /**
     *
     * @Title: addDept
     * @Description: �������
     * @param dept
     * @return
     */
    GlobalResultVO addDept(Dept dept);

    /**
     *
     * @Title: deleteDeptById
     * @Description: ����idɾ������
     * @author: ��
     * @param deptid
     * @return
     */
    GlobalResultVO deleteDeptById(String deptid);

    /**
     *
     * @Title: updateDeptById
     * @Description: ����id�޸�����
     * @author: ��
     * @param dept
     * @return
     */
    GlobalResultVO updateDeptById(Dept dept);
    /**
     *
     * @Title: updateNameById
     * @Description: ����id�޸�NAME
     * @author: ��
     * @return
     */
    GlobalResultVO updateNameById(String deptID,String deptName);
//    /**
//     *
//     * @Title: findMenuByUserid
//     * @Description: ����userid���ض�Ӧ����
//     * @param userid
//     * @return Menu
//     * @author ��
//     * @date 2019��2��16������8:43:39
//     */
//    Menu findMenuByUserid(String userid);
//    /**
//     *
//     * @Title: findMenuListByUserid
//     * @Description: ����userid���ض�Ӧ���������б�
//     * @param userid
//     * @return List<Menu>
//     * @author ��
//     * @date 2019��2��17������8:55:10
//     */
//    List<Menu> findMenuListByUserid(String userid);

    /**
     *
     * @Title: findUserByDeptId
     * @Description: ���ݲ���id���Ҳ��ţ���ʾ�û�����
     * @author: ��
     * @param deptID
     * @return
     */
    List<User> findUserByDeptId(String deptID);

}
