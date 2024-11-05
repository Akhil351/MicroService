package org.Akhil.common.config.security.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.Akhil.common.util.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignClientConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes!=null){
            HttpServletRequest request=attributes.getRequest();
            String authToken=request.getHeader(JwtUtils.JWT_HEADER);
            if(authToken!=null){
                requestTemplate.header(JwtUtils.JWT_HEADER,authToken);
            }
        }
    }
}
