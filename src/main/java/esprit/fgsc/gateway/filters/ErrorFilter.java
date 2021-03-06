package esprit.fgsc.gateway.filters;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.util.ReflectionUtils;

import static esprit.fgsc.gateway.filters.FilterConstants.IS_ERROR_CODE_AVAILABLE;
import static esprit.fgsc.gateway.filters.FilterConstants.IS_EXCEPTION_AVAILABLE;

@Slf4j
public class ErrorFilter extends ZuulFilter{


	@Override
    public String filterType() {
		 return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
    	// Needs to run before SendErrorFilter which has filterOrder == 0
        return -1; 
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().containsKey(IS_ERROR_CODE_AVAILABLE);
    }

    @Override
    public Object run() {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            Object e = ctx.get(IS_EXCEPTION_AVAILABLE);
            if (e instanceof ZuulException) {
                ZuulException zuulException = (ZuulException)e;
                // Remove error code to prevent further error handling in follow up filters
                ctx.remove(IS_ERROR_CODE_AVAILABLE);
                // Populate context with new response values
                ctx.setResponseBody(zuulException.errorCause);
                ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_VALUE);
                ctx.setResponseStatusCode(zuulException.nStatusCode);
                log.info("Exception : {}",zuulException.getMessage());
            }
        }
        catch (Exception ex) {
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;
    }

}
