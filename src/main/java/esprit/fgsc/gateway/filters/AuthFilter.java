package esprit.fgsc.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Bean;

import java.util.Enumeration;
@Slf4j
public class AuthFilter extends ZuulFilter {

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        log.info(requestContext.getRequest().getRequestURI());
        log.info(requestContext.getRequest().getHeader("Authorization"));
//        requestContext.setSendZuulResponse(false);
//        requestContext.setResponseStatusCode(401);
        return null;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_FORWARD_FILTER_ORDER;
    }
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

}
