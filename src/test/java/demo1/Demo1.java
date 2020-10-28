package demo1;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class Demo1 {
    @Resource
    private HistoryService historyService;

    @Test
    public void test1() {
        HistoricTaskInstanceQuery histList = null;
//        histList = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).processInstanceId(s_name).processUnfinished().listPage(pageInfo.getPageIndex(), pageInfo.getPageSize());
        histList = historyService.createHistoricTaskInstanceQuery().processInstanceId("447501");
        System.out.println(histList);
    }
}

