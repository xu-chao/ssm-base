package com.java.activiti.dao;

import com.java.activiti.model.Dept;
import com.java.activiti.model.Menu;
import com.java.activiti.model.User;
import com.java.activiti.pojo.DeptTree;
import com.java.activiti.pojo.Tree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeptDao {

    /**
     *
     * @Title: selectDeptList
     * @Description: ������������
     * @author: ��
     * @return
     */
    List<DeptTree> selectDeptList();

    /**
     *
     * @Title: selectDeptById
     * @Description: ���ݲ���id���Ҳ��ţ���ʾ��������
     * @author: ��
     * @param deptID
     * @return
     */
    List<Dept> selectDeptById(@Param("deptID") String deptID);

    /**
     *
     * @Title: findDeptByUserId
     * @Description: �����û�id���Ҳ��ţ���ʾ��������
     * @author: ��
     * @param userID
     * @return
     */
    Dept findDeptByUserId(@Param("userID") String userID);

    //��������deptID�µĲ���
    List<Dept> selectChild(@Param("deptID") String deptID);

    /**
     *
     * @Title: insertDept
     * @Description: �������
     * @author: ��
     * @param dept
     * @return
     */
    Integer insertDept(Dept dept);

    /**
     *
     * @Title: deleteDeptById
     * @Description: ����idɾ������[�޸�״̬]
     * @author: ��
     * @param deptID
     * @return
     */
    Integer deleteDeptById(String deptID);

    /**
     *
     * @Title: updateDeptById
     * @Description: ����id�޸�����
     * @author: ��
     * @param dept
     * @return
     */
    Integer updateDeptById(Dept dept);

    Integer updateNameById(@Param("deptID") String deptID,@Param("deptName") String deptName);

//    /**
//     *
//     * @Title: selectMenuIdName
//     * @Description: ����pid��ȡ����Ȩ�޲���(menuid,menuname)
//     * @return List<Menu>
//     * @author ��
//     * @date 2019��2��16������7:05:10
//     */
//    public List<Menu> selectMenuIdName(@Param("pid") String pid);
//
//    /**
//     *
//     * @Title: selectMenuByUserid
//     * @Description: ����userid���ض�Ӧ����
//     * @param userid
//     * @return List<Menu>
//     * @author ��
//     * @date 2019��2��16������8:40:39
//     */
//    public List<Menu> selectMenuByUserid(@Param("userid") String userid);
//
//    /**
//     *
//     * @Title: selectMenu
//     * @Description: ��ѯ���в��ŵ���������
//     * @param pid
//     * @return List<Menu>
//     * @author ��
//     * @date 2019��2��16������9:04:26
//     */
//    public List<Menu> selectMenu(@Param("pid") String pid);

    /**
     *
     * @Title: findUserByDeptId
     * @Description: ���ݲ���id���Ҳ��ţ���ʾ�û�����
     * @author: ��
     * @param deptID
     * @return
     */
    List<User> findUserByDeptId(@Param("deptID") String deptID);

}
