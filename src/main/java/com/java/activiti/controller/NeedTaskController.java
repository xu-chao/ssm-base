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
 * ��ʷ������ע����
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/needTask")
public class NeedTaskController {

    // ����activiti�Դ���Service�ӿ�
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
     * ����
     *
     * @param taskId   ����id
     * @param comment  ��ע��Ϣ
     * @param state    ���״̬ 1 ͨ�� 2 ����
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/needFactoryPurchase")
    public String needFactoryPurchase(@RequestParam("taskId") String taskId, @RequestParam("comment") String comment,
                                      @RequestParam("state") Integer state, @RequestParam(value = "userId", required = false) String userId,
                                      HttpServletResponse response) throws Exception {
        //���ȸ���ID��ѯ����
        Task task = taskService.createTaskQuery() // ���������ѯ
                .taskId(taskId) // ��������id��ѯ
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();
        //ȡ�ý�ɫ�û��������
        User currentUser = UserUtil.getSubjectUser();
        List<Group> currentGroup = groupDao.findByUserId(currentUser.getId());
        String nId = (String) taskService.getVariable(taskId, "id");
        Need need = needService.findNeedById(nId);

        if (currentGroup.get(0).getId().equals("factoryPurchase")) {
            if (state == 1) {
                if (need.getState().equals("�ʼ���Ա�Բ��ֲ�Ʒ�ʼ�ϸ�")) {
                    need.setState("�ֹ���Ա�ٴ�����ͨ��");
                } else {
                    need.setState("�ֹ���Ա����ͨ��");
                    variables.put("msg", "ͨ��");
                    variables.put("factoryQuality", userId);
                }
            } else {
                if (need.getState().equals("�ʼ���Ա�Բ��ֲ�Ʒ�ʼ�ϸ�")) {
                    need.setState("�ֹ���Ա�ٴ�����δͨ��");
                } else {
                    need.setState("�ֹ���Ա����δͨ��");
                    variables.put("msg", "��ͨ��");
                }
            }
        } else if (currentGroup.get(0).getId().equals("factoryQuality")) {
            if (state == 1) {
                Task taskQuality = taskService.createTaskQuery().taskId(taskId).singleResult();
                List<NeedGoods> needGoodsList = needGoodsService.selectNeedGoods(needService.
                        getNeedByTaskId(taskQuality.getProcessInstanceId()).getNID());
                variables.put("msg", "ͨ��");
                variables.put("flag", "�ϸ�");
                // ���������Ϣ
                for (NeedGoods needGoods : needGoodsList) {
                    Storage storage = new Storage();
                    int resultMount = needGoods.getStorage().getMountStorage() - needGoods.getCheckout().getCheckoutMount();
                    int resultGoodsMount = needGoods.getPurchase().getPurchaseMount() - needGoods.getCheckout().getCheckoutMount();
                    storage = needGoods.getStorage();
                    if (resultMount == 0) {
                        variables.put("result", "�ϸ�");
                        storage.setMountQualified(1);
                    } else if (resultGoodsMount == 0) {
                        variables.put("result", "���ϸ�");
                        storage.setMountQualified(2);
                    } else {
                        variables.put("result", "���ϸ�");
                        storage.setMountQualified(3);
                    }
                    storage.setMountNotQualified(resultMount);
                    storageService.updateStorage(storage);
                }
                if (variables.get("result").equals("�ϸ�")) {
                    need.setState("�ʼ���Ա����ͨ��");
                } else {
                    need.setState("�ʼ���Ա�Բ��ֲ�Ʒ�ʼ�ϸ�");
                }
            } else {
                need.setState("�ʼ���Ա����δͨ��");
                // ���������Ϣ
                variables.put("msg", "��ͨ��");
                variables.put("flag", "���ϸ�");
            }
        }
        needService.updateNeed(need);

        // ��ȡ����ʵ��id
        String processInstanceId = task.getProcessInstanceId();
        // �����û�id
        Authentication.setAuthenticatedUserId(currentUser.getFirstName() + currentUser.getLastName() + "[" + currentGroup.get(0).getName() + "]");
        // �����ע��Ϣ
        taskService.addComment(taskId, processInstanceId, comment);
        // �������
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
        //���ȸ���ID��ѯ����
        Task task = taskService.createTaskQuery() // ���������ѯ
                .taskId(taskId) // ��������id��ѯ
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();
        //ȡ�ý�ɫ�û��������
        User currentUser = UserUtil.getSubjectUser();
        List<Group> currentGroup = groupDao.findByUserId(currentUser.getId());
        String nId = (String) taskService.getVariable(taskId, "id");
        Need need = needService.findNeedById(nId);

        if (currentGroup.get(0).getId().equals("factoryPurchase")) {
            if (state == 1) {
                need.setState("�ֹ���Ա����ͨ��");
                variables.put("msg", "ͨ��");
                variables.put("factoryQuality", userId);
            } else {
                need.setState("�ֹ���Ա����δͨ��");
                variables.put("msg", "��ͨ��");
            }
        } else if (currentGroup.get(0).getId().equals("factoryQuality")) {
            if (state == 1) {
                need.setState("�ʼ���Ա����ͨ��");
                variables.put("msg", "ͨ��");
            } else {
                need.setState("�ʼ���Ա����δͨ��");
                variables.put("msg", "��ͨ��");
            }
        }
        needService.updateNeed(need);

        // ��ȡ����ʵ��id
        String processInstanceId = task.getProcessInstanceId();
        // �����û�id
        Authentication.setAuthenticatedUserId(currentUser.getFirstName() + currentUser.getLastName() + "[" + currentGroup.get(0).getName() + "]");
        // �����ע��Ϣ
        taskService.addComment(taskId, processInstanceId, comment);
        // �������
        taskService.complete(taskId, variables);
        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }
}
