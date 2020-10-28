package com.java.activiti.service;

import com.java.activiti.model.Menu;
import com.java.activiti.model.MyType;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.pojo.Tree;

import java.util.List;

public interface MyTypeService {

  /**
   * @author      LIUHD
   * 参数 typeCode
   * @description findMyTypeByTypeCode
   * @date        2020/1/13 16:09
   * @Version     1.0
   */
    public List<MyType> findMyTypeByTypeCode(String typeCode);
    /**
     *
     * @Title: selectMyTypeByTypeCode
     * @Description: 根据枚举类型，显示枚举详情
     * @author: 许超
     * @param typeCode
     * @return
     */
    public List<MyType> selectMyTypeByTypeCode(String typeCode);
//
//    /**
//     *
//     * @Title: addMenu
//     * @Description: 添加数据
//     * @author: 最后的轻语_dd43
//     * @param Menu
//     * @return
//     */
//    GlobalResultVO addMenu(Menu Menu);
//
//    /**
//     *
//     * @Title: deleteMenuById
//     * @Description: 根据id删除数据
//     * @author: 许超
//     * @param menuid
//     * @return
//     */
//    GlobalResultVO deleteMenuById(String menuid);
//
//    /**
//     *
//     * @Title: updateMenuById
//     * @Description: 根据id修改数据
//     * @author: 许超
//     * @param Menu
//     * @return
//     */
//    GlobalResultVO updateMenuById(Menu Menu);
//    /**
//     *
//     * @Title: findMenuByUserid
//     * @Description: 根据userid加载对应菜单
//     * @param userid
//     * @return Menu
//     * @author 许超
//     * @date 2019年2月16日下午8:43:39
//     */
//    Menu findMenuByUserid(String userid);
//    /**
//     *
//     * @Title: findMenuListByUserid
//     * @Description: 根据userid加载对应菜单无序列表
//     * @param userid
//     * @return List<Menu>
//     * @author 许超
//     * @date 2019年2月17日下午8:55:10
//     */
//    List<Menu> findMenuListByUserid(String userid);
}
