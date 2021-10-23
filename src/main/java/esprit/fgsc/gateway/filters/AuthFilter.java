package esprit.fgsc.gateway.filters;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.eureka.EurekaServerContext;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import esprit.fgsc.gateway.models.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.ZuulRouteApplicationContextInitializer;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AuthFilter extends ZuulFilter {
    @Autowired
    private RestTemplate template;
    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        String token = requestContext.getRequest().getHeader("Authorization");
        if(token != null){
            token = token.replaceFirst("Bearer ","");
            log.info(token);
            UserAccount response = template.getForObject("https://fgsc-auth-service.herokuapp.com/user-from-jwt-token?token="+token, UserAccount.class);
            if (response == null) {
                denyAccess();
            } else {
                log.info("User {} is accessing : {}", response.getEmail(), requestContext.getRequest().getRequestURI());
            }
        } else {
            denyAccess();
        }
        return null;
    }

    private void denyAccess() {
        RequestContext context = RequestContext.getCurrentContext();
        context.setSendZuulResponse(false);
        context.setResponseStatusCode(401);
        Map<String, String> response = new HashMap<>();
        context.setResponseBody("Unauthorized request");
        context.sendZuulResponse();
    }

    @Override
    public boolean shouldFilter() {
        return !RequestContext.getCurrentContext().getRequest().getRequestURI().startsWith("/api/auth");
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
