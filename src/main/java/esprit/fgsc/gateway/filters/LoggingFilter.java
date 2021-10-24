package esprit.fgsc.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
@Slf4j
public class LoggingFilter extends ZuulFilter {
    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        log.info("Client ip adress : {}",requestContext.getRequest().getRemoteHost());
//        log.info("Is token present ? : {}",requestContext.getRequest().getHeader("Authorization"));
        return null;
    }
    @Override
    public boolean shouldFilter() {
        return true;
    }
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 3;
    }
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

}
