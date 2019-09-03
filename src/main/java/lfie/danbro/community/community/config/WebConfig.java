package lfie.danbro.community.community.config;


import lfie.danbro.community.community.Interceptors.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//拦截器
@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Autowired
    SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**").
                excludePathPatterns("/js/*","/css/*","/images/*","/fonts/*","/login","/callback","/question/*","/","/logout","/tag/*","/js/lib/*");
    }
}