package com.wen.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.wen.common.constant.UserConstant;
import com.wen.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext currentContext = RequestContext.getCurrentContext();

        HttpServletRequest request = currentContext.getRequest();

        if (request.getMethod().equals("OPTIONS")) {
            return null;
        }

        // 登录放行
        String url = request.getRequestURL().toString();
        if (url.indexOf("login") > 0) {
            return null;
        }

        String header = request.getHeader(UserConstant.authorization);
        if (header != null && !"".equals(header)){

            if (header.startsWith(UserConstant.bearer)) {
                String token = header.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String role = (String) claims.get("roles");
                    if (role.equals(UserConstant.admin)) {
                        currentContext.addZuulRequestHeader(UserConstant.authorization, header);
                        return null;
                    }
                }catch (Exception e) {
                    // 终止运行
                    currentContext.setSendZuulResponse(false);
                }
            }
        }
        currentContext.setSendZuulResponse(false);
        currentContext.setResponseStatusCode(403);
        currentContext.setResponseBody("权限不足");
        currentContext.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }
}
