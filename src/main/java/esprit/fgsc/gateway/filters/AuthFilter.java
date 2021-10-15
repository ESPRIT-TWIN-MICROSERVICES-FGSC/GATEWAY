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
//        RequestContext ctx = RequestContext.getCurrentContext();
//        Enumeration<String> enume = ctx.getRequest().getHeaderNames();
//        String header;
//        while (enume.hasMoreElements()) {
//            header = enume.nextElement();
//            //System.out.println(header + " " + ctx.getRequest().getHeader(header));
//        }
        log.info(RequestContext.getCurrentContext().getRequest().getRequestURI());
        log.info(RequestContext.getCurrentContext().getRequest().getHeader("Authorization"));
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
