package com.java.activiti.controller;

import com.java.activiti.model.Group;
import com.java.activiti.model.PageInfo;
import com.java.activiti.model.Permission;
import com.java.activiti.pojo.EasyUIOptionalDataListNode;
import com.java.activiti.pojo.GlobalResultVO;
import com.java.activiti.service.GroupService;
import com.java.activiti.service.PermissionService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/allocation")
public class AllocationController {

    @Resource
    GroupService groupService;

    @Resource
    PermissionService permissionService;

    /**
     * 分页查询系统日志
     * @return
     * @throws Exception
     */
    @RequestMapping("/allocationPage")
    public String allocationPage(HttpServletResponse response, String rows,
                            String page) throws Exception {
        PageInfo<Group> groupPage = new PageInfo<Group>();
        Map<String, Object> map = new HashMap<String, Object>();
        Integer pageSize = Integer.parseInt(rows);
        groupPage.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        groupPage.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        map.put("pageIndex", groupPage.getPageIndex());
        map.put("pageSize", groupPage.getPageSize());
        int groupCount = groupService.groupCount(map);
        List<Group> groupList = groupService.groupPage(map);
        for(Group groups:groupList){
            StringBuffer buffer=new StringBuffer();
            List<Permission> permissionsList = permissionService.findByGroupId(groups.getId());
            for(Permission p:permissionsList){
                buffer.append(p.getName()+",");
            }
            if(buffer.length()>0){
                //deleteCharAt 删除最后一个元素
                groups.setPermissions(buffer.deleteCharAt(buffer.length()-1).toString());
            }else{
                groups.setPermissions(buffer.toString());
            }
        }
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result=new JSONObject();
        JSONArray jsonArray=JSONArray.fromObject(groupList,jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", groupCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     *
     * @Title: findPermissionByGid
     * @Description: 根据角色id加载对应权限菜单
     * @param groupID
     * @return List<EasyUIOptionalDataListNode>
     * @author 许超
     * @date 2019年8月13日
     */
    @RequestMapping(value = "/findPermissionByGid" , method = RequestMethod.POST)
    @ResponseBody
    public List<EasyUIOptionalDataListNode> findPermissionByGid(
            @RequestParam(value = "id", required = true)String groupID) {
        return permissionService.findPermissionByGid(groupID);
    }

    /**
     *
     * @Title: allocationExport
     * @Description: 根据搜索条件，导出对应的数据
     * @param userID startDate endDate 封装的条件
     * @return
     */
    @RequestMapping(value = "/allocationExport", method = RequestMethod.POST)
    @ResponseBody
    public void allocationExport(String userID, String startDate, String endDate, HttpServletResponse response) {
        return;
    }

    /**
     *
     * @Title: updateAllocation
     * @Description: 更新角色访问权限
     * @param groupID
     * @param checkedIds
     * @return GlobalResultVO
     * @author 许超
     * @date 2019年8月14日
     */
    @RequestMapping(value = "/updateAllocation" , method = RequestMethod.POST)
    @ResponseBody
    public GlobalResultVO updateAllocation(
            @RequestParam(value = "id", required = true) String groupID,
            @RequestParam(value = "checkedIds", required = true) String checkedIds) {
        GlobalResultVO result = permissionService.updateGroupAllocation(groupID, checkedIds);
        return result;
    }
}
