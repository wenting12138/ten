package com.wen.encrypt.filter;

import com.google.common.base.Strings;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import com.wen.encrypt.rsa.RsaKeys;
import com.wen.encrypt.service.RsaService;
import org.apache.commons.codec.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RSARequestFilter extends ZuulFilter {

    @Autowired
    private RsaService rsaService;

    @Override
    public String filterType() {
        // 在什么时机进行过滤
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        // 执行顺序
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        // 是否使用这个过滤器
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        // 获取请求
        HttpServletRequest request = currentContext.getRequest();
        // 获取响应
        HttpServletResponse response = currentContext.getResponse();

        String requestData = null;
        String decryptData = null;
        try {
            ServletInputStream inputStream = request.getInputStream();
            // 获取加密后的数据
            requestData = StreamUtils.copyToString(inputStream, Charsets.UTF_8);

            if (!Strings.isNullOrEmpty(requestData)) {
                // 进行解码
                decryptData = rsaService.RSADecryptDataPEM(requestData, RsaKeys.getServerPrvKeyPkcs8());
            }
            if (!Strings.isNullOrEmpty(decryptData)) {
                byte[] decryptDataBytes = decryptData.getBytes();
                // 使用requestContext进行转发
                currentContext.setRequest(new HttpServletRequestWrapper(request){
                    @Override
                    public ServletInputStream getInputStream() throws IOException {

                        return new ServletInputStreamWrapper(decryptDataBytes);
                    }
                    @Override
                    public int getContentLength() {
                        return decryptDataBytes.length;
                    }
                    @Override
                    public long getContentLengthLong() {
                        return decryptDataBytes.length;
                    }
                });
            }
            // 设置request请求头中的Content-Type为json格式
            currentContext.addZuulRequestHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
