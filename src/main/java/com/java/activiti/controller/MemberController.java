package com.java.activiti.controller;

import com.java.activiti.pojo.UserOnlineVO;
import com.java.activiti.security.session.CustomSessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/member")
public class MemberController {

//    @Resource
//    private CustomSessionManager customSessionManager;
//
//    /**
//     * 在线用户管理
//     * @return
//     */
//    @RequestMapping(value="/online")
//    public ModelAndView online(){
//        List<UserOnlineVO> list = customSessionManager.getAllUser();
//        return new ModelAndView("page/onlineManage","list",list);
//    }
//
//    /**
//     * 改变Session状态
//     * @param status
//     * @param sessionIds
//     * @return
//     */
//    @RequestMapping(value="/changeSessionStatus",method= RequestMethod.POST)
//    @ResponseBody
//    public Map<String,Object> changeSessionStatus(Boolean status, String sessionIds){
//        return customSessionManager.changeSessionStatus(status,sessionIds);
//    }
}
