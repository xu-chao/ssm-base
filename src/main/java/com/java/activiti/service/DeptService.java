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
     * @Description: 查找所有数据
     * @author: 许超
     * @return
     */
    List<DeptTree> findDeptList();

    /**
     *
     * @Title: findDeptById
     * @Description: 根据部门id查找部门，显示部门详情
     * @author: 许超
     * @param deptID
     * @return
     */
    List<Dept> findDeptById(String deptID);
    /**
     *
     * @Title: findDeptById
     * @Description: 根据部门id查找子部门，显示子部门详情
     * @author: 许超
     * @param deptID
     * @return
     */
    List<Dept> selectChild(String deptID);

    /**
     *
     * @Title: findDeptByUserId
     * @Description: 根据用户id查找部门，显示部门详情
     * @author: 许超
     * @param userID
     * @return
     */
    Dept findDeptByUserId(String userID);

    /**
     *
     * @Title: addDept
     * @Description: 添加数据
     * @param dept
     * @return
     */
    GlobalResultVO addDept(Dept dept);

    /**
     *
     * @Title: deleteDeptById
     * @Description: 根据id删除数据
     * @author: 许超
     * @param deptid
     * @return
     */
    GlobalResultVO deleteDeptById(String deptid);

    /**
     *
     * @Title: updateDeptById
     * @Description: 根据id修改数据
     * @author: 许超
     * @param dept
     * @return
     */
    GlobalResultVO updateDeptById(Dept dept);
    /**
     *
     * @Title: updateNameById
     * @Description: 根据id修改NAME
     * @author: 许超
     * @return
     */
    GlobalResultVO updateNameById(String deptID,String deptName);
//    /**
//     *
//     * @Title: findMenuByUserid
//     * @Description: 根据userid加载对应部门
//     * @param userid
//     * @return Menu
//     * @author 许超
//     * @date 2019年2月16日下午8:43:39
//     */
//    Menu findMenuByUserid(String userid);
//    /**
//     *
//     * @Title: findMenuListByUserid
//     * @Description: 根据userid加载对应部门无序列表
//     * @param userid
//     * @return List<Menu>
//     * @author 许超
//     * @date 2019年2月17日下午8:55:10
//     */
//    List<Menu> findMenuListByUserid(String userid);

    /**
     *
     * @Title: findUserByDeptId
     * @Description: 根据部门id查找部门，显示用户详情
     * @author: 许超
     * @param deptID
     * @return
     */
    List<User> findUserByDeptId(String deptID);

}
