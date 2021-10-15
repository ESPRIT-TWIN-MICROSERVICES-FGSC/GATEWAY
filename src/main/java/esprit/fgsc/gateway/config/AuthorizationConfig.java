//package esprit.fgsc.gateway.config;
//
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@Configuration
//@EnableResourceServer
//public class AuthorizationConfig extends ResourceServerConfigurerAdapter {
//    @Override
//    public void configure(final HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/oauth/**")
//                .permitAll()
//                .antMatchers("/**")
//                .authenticated();
//    }
//}