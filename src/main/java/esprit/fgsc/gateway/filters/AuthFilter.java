package esprit.fgsc.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import esprit.fgsc.gateway.models.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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
            UserAccount response = template.getForObject("https://fgsc-auth-service.herokuapp.com/user-from-jwt-token?token="+token, UserAccount.class);
            if (response == null) {
                denyAccess();
                log.info("Access denied");
            } else {
                log.info("User {} is accessing : {}", response.getEmail(), requestContext.getRequest().getRequestURI());
            }
        } else {
            denyAccess();
            log.info("Access denied");
        }
        return null;
    }

    private void denyAccess() {
        RequestContext context = RequestContext.getCurrentContext();
        context.setSendZuulResponse(false);
        context.setResponseStatusCode(401);
        context.setResponseBody("Unauthorized request");
        context.sendZuulResponse();
    }

    @Override
    public boolean shouldFilter() {
        boolean shouldFilter = true;
        for (String authorizedPath : AUTHORIZED_ROUTES){
            if(RequestContext.getCurrentContext().getRequest().getRequestURI().startsWith(authorizedPath))
                shouldFilter = false;
        }
        return shouldFilter;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 3;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    private static final List<String> AUTHORIZED_ROUTES = Arrays.asList("/api/auth/account");

}
