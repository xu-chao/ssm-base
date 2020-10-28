package com.java.activiti.dao;

import com.java.activiti.pojo.GroupPermissionVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色权限信息 Mapper
 *
 * @author Xu
 * @since 2019/8/11..
 */
public interface GroupPermissionDao {

    List<GroupPermissionVO> selectAll();
}
