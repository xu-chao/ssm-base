package com.java.activiti.controller;

import com.java.activiti.dao.GroupDao;
import com.java.activiti.model.*;
import com.java.activiti.service.EquitService;
import com.java.activiti.service.WorkOrderService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.activiti.engine.*;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 历史流程批注管理
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/equitTask")
public class EquitTaskController {

    // 引入activiti自带的Service接口
    @Resource
    private TaskService taskService;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private FormService formService;

    @Resource
    private EquitService equitService;

    @Resource
    private WorkOrderService workOrderService;

    @Resource
    private HistoryService historyService;

    @Resource
    private GroupDao groupDao;

    /**
     * 审批
     *
     * @param taskId   任务id
     * @param comment  批注信息
     * @param state    审核状态 1 通过 2 驳回
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/equitOperators")
    public String equitOperators(@RequestParam("taskId") String taskId, @RequestParam("comment") String comment,
                                 @RequestParam("state") Integer state, @RequestParam(value = "userId",required = false) String userId,
//                                 @RequestParam(value = "fileAfterID", required = false) String fileAfterID,
                                 HttpServletResponse response) throws Exception {
        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();
        //取得角色用户登入对象
        User currentUser = UserUtil.getSubjectUser();
        List<Group> currentGroup = groupDao.findByUserId(currentUser.getId());
        String equitID = (String) taskService.getVariable(taskId, "id");
        Equit equit = equitService.findById(equitID);


        if (currentGroup.get(0).getId().equals("factoryPurchase")) {
            if (state == 1) {
                equit.setState("采购人员审批通过");
                variables.put("msg", "通过");
                // 更新审核信息
                // 设置-下一个审批人员
                variables.put("factoryQuality", userId);
            } else {
                equit.setState("采购人员审批未通过");
                // 更新审核信息
                variables.put("msg", "不通过");
                equit.setEndDate(new Date());
            }


        }else if (currentGroup.get(0).getId().equals("factoryQuality")){
            if (state == 1) {
                variables.put("msg", "通过");
                equit.setState("质检人员审批通过");
                equit.setEndDate(new Date());
                // 更新审核信息
            } else {
                equit.setState("质检人员审批未通过");
                // 更新审核信息
                variables.put("msg", "不通过");
                equit.setEndDate(new Date());
            }
        }
        equitService.updateEquit(equit);

        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getFirstName() + currentUser.getLastName() + "[" + currentGroup.get(0).

                getName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        taskService.complete(taskId, variables);
        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }
}
