package com.java.activiti.controller.gaizao;

import com.java.activiti.dao.GroupDao;
import com.java.activiti.model.*;
import com.java.activiti.model.gaizao.Huiyishi;
import com.java.activiti.service.gaizao.HuiyishiService;
import com.java.activiti.util.DateJsonValueProcessor;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.StringUtil;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ��ʷ������ע����
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/huiyishiTask")
public class HuiyishiTaskController {

    // ����activiti�Դ���Service�ӿ�
    @Resource
    private TaskService taskService;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private FormService formService;

    @Resource
    private HuiyishiService huiyishiService;

    @Resource
    private HistoryService historyService;

    @Resource
    private GroupDao groupDao;


    /**
     * ��ѯ��ʷ������ע
     *
     * @param response
     * @param processInstanceId ����ID
     * @return
     * @throws Exception
     */
    @RequestMapping("/listHistoryCommentWithProcessInstanceId")
    public String listHistoryCommentWithProcessInstanceId(
            HttpServletResponse response, String processInstanceId) throws Exception {
        if (processInstanceId == null) {
            return null;
        }
        List<Comment> commentList = taskService
                .getProcessInstanceComments(processInstanceId);
        // �ı�˳�򣬰�ԭ˳��ķ���˳�򷵻�list
        Collections.reverse(commentList); //����Ԫ�ط�ת
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class,
                //ʱ���ʽת��
                new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(commentList, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * �ض�����˴���ҳ��
     *
     * @param taskId
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/redirectPage")
    public String redirectPage(String taskId, HttpServletResponse response) throws Exception {
        TaskFormData formData = formService.getTaskFormData(taskId);
        String url = formData.getFormKey();
        System.out.println("*********************" + url + "*********************");
        JSONObject result = new JSONObject();
        result.put("url", url);
        ResponseUtil.write(response, result);
        return null;
    }


    /**
     * �������̷�ҳ��ѯ
     *
     * @param response
     * @param page     ��ǰҳ��
     * @param rows     ÿҳ��ʾҳ��
     * @param s_name   ��������
     * @param userId   ����ID
     * @return
     * @throws Exception
     */
    @RequestMapping("/taskPage")
    public String taskPage(HttpServletResponse response, String page, String rows, String s_name, String userId, String groupId) throws Exception {
        PageInfo pageInfo = new PageInfo();
        Integer pageSize = Integer.parseInt(rows);
        pageInfo.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        pageInfo.setPageIndex((Integer.parseInt(page) - 1) * pageInfo.getPageSize());
        // ��ȡ�ܼ�¼��
        long total = 0;
        //���뷨�Ļ�������ȥ���ݿ�۲�  ACT_RU_TASK �ı仯
        List<Task> taskList = null;
        if (s_name == null || s_name == "") {
            // ��ȡ�ܼ�¼��
            total = taskService.createTaskQuery().taskAssignee(userId).count();
            // ���ش���ҳ�Ľ������
            taskList = taskService.createTaskQuery().taskAssignee(userId).listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());

        } else {
            // ��ȡ�ܼ�¼��
            total = taskService.createTaskQuery().taskAssignee(userId).processInstanceId(s_name).count();
            // ���ش���ҳ�Ľ������
            taskList = taskService.createTaskQuery().taskAssignee(userId).processInstanceId(s_name).listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
        }
        //��ȡ�������̵����ⵥ
        List<String> processIDs = new ArrayList<>();
        for (Task t : taskList) {//��ȡ�������� id
            processIDs.add(t.getProcessInstanceId());
        }
        List<Huiyishi> huiyishis = null;
        if (processIDs.size() > 0) {//��������ʾ ���ⵥ��Ӧ��һЩ�ֶ�
            huiyishis = huiyishiService.selectTaskByProcessID(processIDs);
        }

        //������Ҫʹ��һ����������ת��һ����Ҫ��ת��JSON��ʽ
        List<MyTaskInfo> MyTaskList = new ArrayList<MyTaskInfo>();
        for (Task t : taskList) {
            MyTaskInfo myTask = new MyTaskInfo();
            for (Huiyishi huiyishi : huiyishis) {
                if (huiyishi.getProcessInstanceId().equals(t.getProcessInstanceId())) {
                    myTask.setStr1(String.valueOf(huiyishi.getHysID()));
                    myTask.setStr2(huiyishi.getUserID().getAllName());
                    myTask.setStr3(String.valueOf(huiyishi.getHysText()));
                }
            }
            myTask.setId(t.getId());
            myTask.setTaskID(t.getProcessInstanceId());
            myTask.setName(t.getName());
            myTask.setCreateTime(t.getCreateTime());
            MyTaskList.add(myTask);
        }

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(MyTaskList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", total);
        ResponseUtil.write(response, result);
        return null;
    }

//    /**
//     * table table-border table-bordered table-striped
//     * ��ѯ������ϸ��Ϣ
//     *
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/getHuiyishiById")
//    public String getHuiyishiById(HttpServletResponse response, String id) throws Exception {
//        //�ȸ�������ID��ѯ
//        Huiyishi huiyishi = huiyishiService.findById(id);
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String str = "<table  width='100%' class=\"gridtable\"><tr>" +
//                "<td>" + "����: " + huiyishi.getHuiyishiID() + "</td>" + "<td>" + "��������: " + huiyishi.getPark().getParkName() + "</td>" + "<td>" + "��Ŀ����: " + huiyishi.getProject().getProjectName() + "</td>" + "<td>" + "רҵ����: " + huiyishi.getSubjectID() + "</td>" + "<td>" + "�������: " + huiyishi.getProblemTitle() + "</td>" + "<td>" + "�ύ��: " + huiyishi.getUser().getAllName() + "</td>" + "<td>" + "�ύʱ��: " + ft.format(huiyishi.getHuiyishiDate()) + "</td>" + "</tr>" +
//                "<tr>" + "<td  colspan=\"9\">" + "��������: " + huiyishi.getProblemText() + "</td>" + "</tr>" +
//                "</table>";
//        ResponseUtil.write(response, str);
//        return null;
//    }

    /**
     * ��ѯ��ǰ����ͼ
     *
     * @return
     */
    @RequestMapping("/showCurrentView")
    public String showCurrentView(HttpServletResponse response, String taskId) {
        //��ͼ
        ModelAndView mav = new ModelAndView();

        Task task = taskService.createTaskQuery() // ���������ѯ
                .taskId(taskId) // ��������id��ѯ
                .singleResult();
        // ��ȡ���̶���id
        String processDefinitionId = task.getProcessDefinitionId();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery() // �������̶����ѯ
                // �������̶���id��ѯ
                .processDefinitionId(processDefinitionId)
                .singleResult();
        // ����id
        mav.addObject("deploymentId", processDefinition.getDeploymentId());
        mav.addObject("diagramResourceName", processDefinition.getDiagramResourceName()); // ͼƬ��Դ�ļ�����

        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)
                repositoryService.getProcessDefinition(processDefinitionId);
        // ��ȡ����ʵ��id
        String processInstanceId = task.getProcessInstanceId();
        // ��������ʵ��id��ȡ����ʵ��
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();

        // ���ݻid��ȡ�ʵ��
        ActivityImpl activityImpl = processDefinitionEntity.findActivity(pi.getActivityId());
        //������View��ͼ���ص���ʾҳ��
        mav.addObject("x", activityImpl.getX()); // x����
        mav.addObject("y", activityImpl.getY()); // y����
        mav.addObject("width", activityImpl.getWidth()); // ����
        mav.addObject("height", activityImpl.getHeight()); // �߶�
        mav.setViewName("page/currentView");
        return null;
    }

    /**
     * ��ѯ��ʷ��ע
     *
     * @param response
     * @param taskId   ����ID
     * @return
     * @throws Exception
     */
    @RequestMapping("/listHistoryComment")
    public String listHistoryComment(HttpServletResponse response, String taskId) throws Exception {
        if (taskId == null) {
            taskId = "";
            return null;
        }
        HistoricTaskInstance hti = historyService.createHistoricTaskInstanceQuery()
                .taskId(taskId)
                .singleResult();
        JsonConfig jsonConfig = new JsonConfig();
        JSONObject result = new JSONObject();
        List<Comment> commentList = null;
        if (hti != null) {
            commentList = taskService.getProcessInstanceComments(hti.getProcessInstanceId());
            // ����Ԫ�ط�ת
            Collections.reverse(commentList);

            //���ڸ�ʽת��
            jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        }
        JSONArray jsonArray = JSONArray.fromObject(commentList, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * @author LIUHD
     * ���� taskId
     * ���� state
     * ���� huiyishi
     * ���� response
     * @description ����
     * @date 2020/1/3 15:08
     * @Version 1.0
     */
    @RequestMapping("/auditOperators")
    public String auditOperators(@RequestParam("taskId") String taskId,
                                 Huiyishi huiyishi,
                                 HttpServletResponse response) throws Exception {
        //���ȸ���ID��ѯ����
        Task task = taskService.createTaskQuery() // ���������ѯ
                .taskId(taskId) // ��������id��ѯ
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();
        //ȡ�ý�ɫ�û��������
        User currentUser = UserUtil.getSubjectUser();
        List<Group> currentGroup = groupDao.findByUserId(currentUser.getId());

        huiyishi.setState("������ͨ��");
        huiyishi.setStateID(2);

        int succss = huiyishiService.updateHuiyishi(huiyishi);
        // ��ȡ����ʵ��id
        String processInstanceId = task.getProcessInstanceId();
        // �����û�id
        Authentication.setAuthenticatedUserId(currentUser.getFirstName() + currentUser.getLastName() + "[" + currentGroup.get(0).

                getName() + "]");
//        // ������ע��Ϣ
//        taskService.addComment(taskId, processInstanceId, comment);
        // �������
        taskService.complete(taskId, variables);
        JSONObject result = new JSONObject();

        if (succss > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * ��ԃ���������������ʷ���̱� :  act_hi_actinst
     *
     * @param response
     * @param rows
     * @param page
     * @param s_name
     * @param groupId
     * @return
     * @throws Exception
     */
    @RequestMapping("/finishedList")
    public String finishedList(HttpServletResponse response, String rows, String page,
                               String s_name, String groupId, String userId, String type) throws Exception {
        //ΪʲôҪ�����أ���Ϊ�����״����н����̨ʱ��
        //s_name�ض��ǵ���null�ģ����ֱ��������д����ѯ����оͻ����  % null %�����ͻᵼ�²�ѯ�������
        PageInfo pageInfo = new PageInfo();
        Integer pageSize = Integer.parseInt(rows);
        pageInfo.setPageSize(pageSize);
        if (page == null || page.equals("")) {
            page = "1";
        }
        pageInfo.setPageIndex((Integer.parseInt(page) - 1) * pageSize);
        //����������ʷʵ����ѯ
        List<HistoricTaskInstance> histList = null;
        long histCount = 0;
        if (s_name == null || s_name == "") {
            if ("1".equals(type)) { //����������
                histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processUnfinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processUnfinished().count();
            } else {
                histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processFinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processFinished().count();
            }
        } else {
            if ("1".equals(type)) {//����������
                histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processUnfinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processUnfinished().count();
            } else {//ָ����ȥ��
                histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processFinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
                histCount = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processFinished().count();
            }
        }
        // ���н������� �о�ʱ��
        Collections.sort(histList, new Comparator<HistoricTaskInstance>() {
            public int compare(HistoricTaskInstance o1, HistoricTaskInstance o2) {
                return o2.getStartTime().compareTo(o1.getStartTime());
            }
        });
        //��ȡ�������̵����ⵥ
        List<String> processIDs = new ArrayList<>();
        for (HistoricTaskInstance t : histList) {
            processIDs.add(t.getProcessInstanceId());
        }
        List<Huiyishi> huiyishis = null;
        if (processIDs.size() > 0) {
            huiyishis = huiyishiService.selectTaskByProcessID(processIDs);
        }

        List<MyTaskInfo> taskList = new ArrayList<>();
        //����ݳ�û���õ��ֶΣ���ø�ǰ��ҳ�����ɼ���ѹ��
        for (HistoricTaskInstance hti : histList) {
            MyTaskInfo myTask = new MyTaskInfo();
            for (Huiyishi huiyishi : huiyishis) {
                if (huiyishi.getProcessInstanceId().equals(hti.getProcessInstanceId())) {
                    myTask.setStr1(String.valueOf(huiyishi.getHysID()));
                    myTask.setStr2(huiyishi.getUserID().getAllName());
                    myTask.setStr3(String.valueOf(huiyishi.getHysText()));
                }
            }
            myTask.setId(hti.getId());
            myTask.setTaskID(hti.getProcessInstanceId());
            myTask.setName(hti.getName());
            myTask.setCreateTime(hti.getCreateTime());
            myTask.setEndTime(hti.getEndTime());
            taskList.add(myTask);
        }
// ����getProcessInstanceIdȥ��
        List<MyTaskInfo> unique = taskList.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(
                        MyTaskInfo::getTaskID
                ))), ArrayList::new
        ));

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(unique, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", histCount);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * ��������id��ѯ����ʵ���ľ���ִ�й���
     *
     * @param taskId
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/listAction")
    public String listAction(String taskId, HttpServletResponse response) throws Exception {
        List<HistoricTaskInstance> hti = historyService.createHistoricTaskInstanceQuery().taskId(taskId).list();
        String processInstanceId = hti.get(0).getProcessInstanceId(); // ��ȡ����ʵ��id
        List<HistoricActivityInstance> haiList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(haiList, jsonConfig);
        result.put("rows", jsonArray);
        ResponseUtil.write(response, result);
        return null;
    }

}