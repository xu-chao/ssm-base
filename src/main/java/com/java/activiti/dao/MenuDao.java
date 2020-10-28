package com.java.activiti.dao;

import com.java.activiti.model.Menu;
import com.java.activiti.pojo.Tree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDao {

    /**
     *
     * @Title: selectMenuList
     * @Description: 查找所有数据
     * @author: 许超
     * @return
     */
    List<Tree> selectMenuList();

    /**
     *
     * @Title: selectMenuById
     * @Description: 根据菜单id查找菜单，显示菜单详情
     * @author: 许超
     * @param menuid
     * @return
     */
    List<Menu> selectMenuById(@Param("menuid") String menuid);

    /**
     *
     * @Title: insertMenu
     * @Description: 添加数据
     * @author: 许超
     * @param Menu
     * @return
     */
    Integer insertMenu(Menu Menu);

    /**
     *
     * @Title: deleteMenuById
     * @Description: 根据id删除数据[修改状态]
     * @author: 许超
     * @param menuid
     * @return
     */
    Integer deleteMenuById(String menuid);

    /**
     *
     * @Title: updateMenuById
     * @Description: 根据id修改数据
     * @author: 许超
     * @param Menu
     * @return
     */
    Integer updateMenuById(Menu Menu);

    /**
     *
     * @Title: selectMenuIdName
     * @Description: 根据pid获取所有权限菜单(menuid,menuname)
     * @return List<Menu>
     * @author 许超
     * @date 2019年2月16日下午7:05:10
     */
    public List<Menu> selectMenuIdName(@Param("pid") String pid);

    /**
     *
     * @Title: selectMenuByUserid
     * @Description: 根据userid加载对应菜单
     * @param userid
     * @return List<Menu>
     * @author 许超
     * @date 2019年2月16日下午8:40:39
     */
    public List<Menu> selectMenuByUserid(@Param("userid") String userid);

    /**
     *
     * @Title: selectMenu
     * @Description: 查询所有菜单的所有属性
     * @param pid
     * @return List<Menu>
     * @author 许超
     * @date 2019年2月16日下午9:04:26
     */
    public List<Menu> selectMenu(@Param("pid") String pid);
}
