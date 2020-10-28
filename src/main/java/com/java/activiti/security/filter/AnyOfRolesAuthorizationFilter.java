package com.java.activiti.security.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * �Զ�����������̳��� RolesAuthorizationFilter ��ʵ���˶����ɫ���Է���ĳһ��url
 * @author XU
 * @since 2019/8/6.
 */
public class AnyOfRolesAuthorizationFilter extends RolesAuthorizationFilter {
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response,
                                   Object mappedValue) throws IOException {

        final Subject subject = getSubject(request, response);
        final String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0){
            // no roles specified, so nothing to check - allow access
//            System.out.println("no roles");
            return true;
        }

        for (String role : rolesArray){
            System.out.println(role);
            if (subject.hasRole(role)){
                return true;
            }
        }

        return false;
    }
}
