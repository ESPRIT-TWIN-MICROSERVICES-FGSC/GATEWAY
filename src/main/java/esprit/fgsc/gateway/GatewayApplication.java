package esprit.fgsc.gateway;

import esprit.fgsc.gateway.config.RibbonEurekaClientConfig;
import esprit.fgsc.gateway.filters.AuthFilter;
import esprit.fgsc.gateway.filters.ErrorFilter;
import esprit.fgsc.gateway.filters.LoggingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableAsync
@EnableZuulProxy
@CrossOrigin("*")
@RestController
@EnableEurekaClient
@SpringBootApplication
@RibbonClients(defaultConfiguration = RibbonEurekaClientConfig.class)
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
    @Bean public AuthFilter preFilter() {return new AuthFilter();}
    @Bean public LoggingFilter postFilter() {return new LoggingFilter();}
    @Bean public ErrorFilter errorFilter() {return new ErrorFilter();}
//    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @GetMapping
    void toSwagger(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }


    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("*", config);
        return new CorsFilter(source);
    }
}

