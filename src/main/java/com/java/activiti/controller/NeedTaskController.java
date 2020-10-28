package com.java.activiti.controller;

import com.java.activiti.dao.GroupDao;
import com.java.activiti.model.*;
import com.java.activiti.service.NeedGoodsService;
import com.java.activiti.service.NeedService;
import com.java.activiti.service.StorageService;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.UserUtil;
import net.sf.json.JSONObject;
import org.activiti.engine.*;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 历史流程批注管理
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/needTask")
public class NeedTaskController {

    // 引入activiti自带的Service接口
    @Resource
    private TaskService taskService;

    @Resource
    private NeedService needService;

    @Resource
    private NeedGoodsService needGoodsService;

    @Resource
    private StorageService storageService;

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
    @RequestMapping("/needFactoryPurchase")
    public String needFactoryPurchase(@RequestParam("taskId") String taskId, @RequestParam("comment") String comment,
                                      @RequestParam("state") Integer state, @RequestParam(value = "userId", required = false) String userId,
                                      HttpServletResponse response) throws Exception {
        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();
        //取得角色用户登入对象
        User currentUser = UserUtil.getSubjectUser();
        List<Group> currentGroup = groupDao.findByUserId(currentUser.getId());
        String nId = (String) taskService.getVariable(taskId, "id");
        Need need = needService.findNeedById(nId);

        if (currentGroup.get(0).getId().equals("factoryPurchase")) {
            if (state == 1) {
                if (need.getState().equals("质检人员对部分产品质检合格")) {
                    need.setState("仓管人员再次审批通过");
                } else {
                    need.setState("仓管人员审批通过");
                    variables.put("msg", "通过");
                    variables.put("factoryQuality", userId);
                }
            } else {
                if (need.getState().equals("质检人员对部分产品质检合格")) {
                    need.setState("仓管人员再次审批未通过");
                } else {
                    need.setState("仓管人员审批未通过");
                    variables.put("msg", "不通过");
                }
            }
        } else if (currentGroup.get(0).getId().equals("factoryQuality")) {
            if (state == 1) {
                Task taskQuality = taskService.createTaskQuery().taskId(taskId).singleResult();
                List<NeedGoods> needGoodsList = needGoodsService.selectNeedGoods(needService.
                        getNeedByTaskId(taskQuality.getProcessInstanceId()).getNID());
                variables.put("msg", "通过");
                variables.put("flag", "合格");
                // 更新审核信息
                for (NeedGoods needGoods : needGoodsList) {
                    Storage storage = new Storage();
                    int resultMount = needGoods.getStorage().getMountStorage() - needGoods.getCheckout().getCheckoutMount();
                    int resultGoodsMount = needGoods.getPurchase().getPurchaseMount() - needGoods.getCheckout().getCheckoutMount();
                    storage = needGoods.getStorage();
                    if (resultMount == 0) {
                        variables.put("result", "合格");
                        storage.setMountQualified(1);
                    } else if (resultGoodsMount == 0) {
                        variables.put("result", "不合格");
                        storage.setMountQualified(2);
                    } else {
                        variables.put("result", "不合格");
                        storage.setMountQualified(3);
                    }
                    storage.setMountNotQualified(resultMount);
                    storageService.updateStorage(storage);
                }
                if (variables.get("result").equals("合格")) {
                    need.setState("质检人员审批通过");
                } else {
                    need.setState("质检人员对部分产品质检合格");
                }
            } else {
                need.setState("质检人员审批未通过");
                // 更新审核信息
                variables.put("msg", "不通过");
                variables.put("flag", "不合格");
            }
        }
        needService.updateNeed(need);

        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getFirstName() + currentUser.getLastName() + "[" + currentGroup.get(0).getName() + "]");
        // 添加批注信息
        taskService.addComment(taskId, processInstanceId, comment);
        // 完成任务
        taskService.complete(taskId, variables);
        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping("/needGoodsTask")
    public String needGoodsTask(@RequestParam("taskId") String taskId, @RequestParam("comment") String comment,
                            @RequestParam("state") Integer state, @RequestParam(value = "userId", required = false) String userId,
                            HttpServletResponse response) throws Exception {
        //首先根据ID查询任务
        Task task = taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();
        //取得角色用户登入对象
        User currentUser = UserUtil.getSubjectUser();
        List<Group> currentGroup = groupDao.findByUserId(currentUser.getId());
        String nId = (String) taskService.getVariable(taskId, "id");
        Need need = needService.findNeedById(nId);

        if (currentGroup.get(0).getId().equals("factoryPurchase")) {
            if (state == 1) {
                need.setState("仓管人员审批通过");
                variables.put("msg", "通过");
                variables.put("factoryQuality", userId);
            } else {
                need.setState("仓管人员审批未通过");
                variables.put("msg", "不通过");
            }
        } else if (currentGroup.get(0).getId().equals("factoryQuality")) {
            if (state == 1) {
                need.setState("质检人员审批通过");
                variables.put("msg", "通过");
            } else {
                need.setState("质检人员审批未通过");
                variables.put("msg", "不通过");
            }
        }
        needService.updateNeed(need);

        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 设置用户id
        Authentication.setAuthenticatedUserId(currentUser.getFirstName() + currentUser.getLastName() + "[" + currentGroup.get(0).getName() + "]");
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
