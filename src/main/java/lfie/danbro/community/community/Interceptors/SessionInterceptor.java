package lfie.danbro.community.community.Interceptors;


import lfie.danbro.community.community.mapper.UserMapper;
import lfie.danbro.community.community.model.User;
import lfie.danbro.community.community.model.UserExample;
import lfie.danbro.community.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    UserMapper userMapper;
    @Autowired
    NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getDomain());

                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    //通过数据库获取到用户 在session设置user
                    if (users.size() > 0){
                        request.getSession().setAttribute("user",users.get(0));
                        Long unReadCount = notificationService.getUnReadCount(users.get(0).getId());
                        request.getSession().setAttribute("unReadCount",unReadCount);
                        return true;
                    }
                    response.sendRedirect("/login");
                    return false;
                }
            }
        }
        response.sendRedirect("/login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}


