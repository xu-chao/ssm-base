import com.java.activiti.security.realms.UserAuthorizingRealm;
import com.java.activiti.service.RedisService;
import com.java.activiti.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import javax.annotation.Resource;

public class UserAuthorizingRealmTest {
    @Resource
    RedisService redisService;
    @Test
    public void test01() {
        UserAuthorizingRealm mySimpleRealm = new UserAuthorizingRealm();
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(mySimpleRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("1001", "123456");
        subject.login(token);
        System.out.println(String.format("认证结果：%s", subject.isAuthenticated()));

    }

    @Test
    public void test02(){
        String s ="test";
        System.out.println(StringUtil.isContainsChinese(s));
    }
    @Test
    public void test03(){
    }

}
