package esprit.fgsc.gateway;

import esprit.fgsc.gateway.config.RibbonEurekaClientConfig;

import esprit.fgsc.gateway.filters.AuthFilter;
import esprit.fgsc.gateway.filters.ErrorFilter;
import esprit.fgsc.gateway.filters.LoggingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@EnableZuulProxy
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
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @GetMapping
    void toSwagger(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }


    @Bean
    public FilterRegistrationBean corsFilter() {
        System.out.println("OK COOMER");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);
        return new FilterRegistrationBean(new CorsFilter(source));
    }
}

