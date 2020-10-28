import com.java.activiti.service.RedisService;
import com.java.activiti.service.impl.RedisServiceImpl;
import org.junit.Test;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class getAutoFlowCodeTest {
    @Test
    public void getAutoFlowCodeTest() {
        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        RedisService redisService = new RedisServiceImpl();
        String newDate ="20191127";
//        Long num = redisService.getIncrementNum();
//        String flowCode = StringUtils.getSequence(num);
//        System.out.println("Á÷Ë®ºÅ: " + flowCode);
    }
}
