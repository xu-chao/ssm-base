package com.java.activiti.security.filter;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ��չ FormAuthenticationFilter ���Բ��ַ�����д��ʹ��֧�� Ajax ����
 *
 * @author Xu
 * @since 2019/8/5.
 */
public class ExtendFormAuthenticationFilter extends FormAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(FormAuthenticationFilter.class);

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception{
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-with"))){
            // ����Ϊ��ͨ����
            if(this.isLoginRequest(request, response)) {
                if(this.isLoginSubmission(request, response)) {
                    if(log.isTraceEnabled()) {
                        log.trace("Login submission detected.  Attempting to execute login.");
                    }

                    return this.executeLogin(request, response);
                } else {
                    if(log.isTraceEnabled()) {
                        log.trace("Login page view.");
                    }

                    return true;
                }
            } else {
                if(log.isTraceEnabled()) {
                    log.trace("Attempting to access a path which requires authentication.  Forwarding to the Authentication url [" + this.getLoginUrl() + "]");
                }

                this.saveRequestAndRedirectToLogin(request, response);
                return false;
            }
        }else{
            // ����Ϊ Ajax ����
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-with"))){
            // ����Ϊ��ͨ����
            this.issueSuccessRedirect(request, response);
        }else{
            // ����Ϊ Ajax ����
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        }

        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        if(log.isDebugEnabled()) {
            log.debug("Authentication exception", e);
        }

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))){
            // ����Ϊ��ͨ����
            this.setFailureAttribute(request, e);
            return true;
        }else{
            // ����Ϊ Ajax ����
            // do some thing to return a json
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
    }
}
