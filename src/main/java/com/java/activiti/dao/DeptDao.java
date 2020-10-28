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
     * @Description: 查找所有数据
     * @author: 许超
     * @return
     */
    List<DeptTree> selectDeptList();

    /**
     *
     * @Title: selectDeptById
     * @Description: 根据部门id查找部门，显示部门详情
     * @author: 许超
     * @param deptID
     * @return
     */
    List<Dept> selectDeptById(@Param("deptID") String deptID);

    /**
     *
     * @Title: findDeptByUserId
     * @Description: 根据用户id查找部门，显示部门详情
     * @author: 许超
     * @param userID
     * @return
     */
    Dept findDeptByUserId(@Param("userID") String userID);

    //查找属于deptID下的部门
    List<Dept> selectChild(@Param("deptID") String deptID);

    /**
     *
     * @Title: insertDept
     * @Description: 添加数据
     * @author: 许超
     * @param dept
     * @return
     */
    Integer insertDept(Dept dept);

    /**
     *
     * @Title: deleteDeptById
     * @Description: 根据id删除数据[修改状态]
     * @author: 许超
     * @param deptID
     * @return
     */
    Integer deleteDeptById(String deptID);

    /**
     *
     * @Title: updateDeptById
     * @Description: 根据id修改数据
     * @author: 许超
     * @param dept
     * @return
     */
    Integer updateDeptById(Dept dept);

    Integer updateNameById(@Param("deptID") String deptID,@Param("deptName") String deptName);

//    /**
//     *
//     * @Title: selectMenuIdName
//     * @Description: 根据pid获取所有权限部门(menuid,menuname)
//     * @return List<Menu>
//     * @author 许超
//     * @date 2019年2月16日下午7:05:10
//     */
//    public List<Menu> selectMenuIdName(@Param("pid") String pid);
//
//    /**
//     *
//     * @Title: selectMenuByUserid
//     * @Description: 根据userid加载对应部门
//     * @param userid
//     * @return List<Menu>
//     * @author 许超
//     * @date 2019年2月16日下午8:40:39
//     */
//    public List<Menu> selectMenuByUserid(@Param("userid") String userid);
//
//    /**
//     *
//     * @Title: selectMenu
//     * @Description: 查询所有部门的所有属性
//     * @param pid
//     * @return List<Menu>
//     * @author 许超
//     * @date 2019年2月16日下午9:04:26
//     */
//    public List<Menu> selectMenu(@Param("pid") String pid);

    /**
     *
     * @Title: findUserByDeptId
     * @Description: 根据部门id查找部门，显示用户详情
     * @author: 许超
     * @param deptID
     * @return
     */
    List<User> findUserByDeptId(@Param("deptID") String deptID);

}
