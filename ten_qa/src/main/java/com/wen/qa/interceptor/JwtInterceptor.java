package com.wen.qa.interceptor;

import com.wen.common.constant.UserConstant;
import com.wen.common.exception.TenException;
import com.wen.common.model.User;
import com.wen.common.result.ResultCode;
import com.wen.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    private static final ThreadLocal<User> userInfo = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String header = request.getHeader(UserConstant.authorization);
        if (handler != null && !handler.equals("")) {
            if (header.startsWith(UserConstant.bearer)) {
                String token = header.substring(7);
                // 对令牌进行验证
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String role = (String) claims.get("roles");
                    if (role != null) {
                        String id = claims.getId();
                        String name = claims.getSubject();
                        User user = new User();
                        user.setId(id);
                        user.setNickname(name);
                        user.setRole(role);
                        user.setJwtToken(token);
                        userInfo.set(user);
                    }
                }catch (Exception e) {
                    throw new TenException(ResultCode.TOKENERROR);
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        userInfo.remove();
    }

    public static User getUserInfo(){
        return userInfo.get();
    }

}
