package com.example.AhoraSiElLoginRepapaeldo.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;


@Configuration
public class JwtConfig {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    public FilterRegistrationBean jwtFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtTokenFilter(jwtTokenProvider));
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }
}
