package com.sardine.gateway;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.sardine.common.constants.MdcConstants;
import com.sardine.common.entity.domain.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.MDC;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author keith
 */
@Component
public class LoginFilter extends ZuulFilter {

    private final AuthProperties authProperties;

    private final UserApi userApi;

    public LoginFilter(AuthProperties authProperties, UserApi userApi) {
        this.authProperties = authProperties;
        this.userApi = userApi;
    }

    /**
     * 过滤器的类型: pre route post error
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 过滤器的执行顺序: 返回值越小，优先级越高
     */
    @Override
    public int filterOrder() {
        return 10;
    }

    /**
     * 是否执行run方法
     */
    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        if (context == null || !context.sendZuulResponse())
            return false;
        String uri = context.getRequest().getRequestURI();
        for (String allowPath : authProperties.getAllowedPaths())
            if(uri.startsWith(allowPath))
                return false;
        return true;
    }

    /**
     * 过滤器的业务逻辑
     */
    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String token = request.getHeader(authProperties.getTokenName());
        if (StringUtils.isBlank(token))
            return noAuthorization(context);
        UserDto user = userApi.identify(token).getRecord();
        if (user == null)
            return noAuthorization(context);
        MDC.put(MdcConstants.MDC_KEY_USERNAME, user.getUsername());
        return null;
    }

    /**
     * 拦截此次请求
     */
    private Object noAuthorization(RequestContext context){
        context.setSendZuulResponse(false);
        context.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
        context.setResponseBody("No Authorization");
        return null;
    }
}
