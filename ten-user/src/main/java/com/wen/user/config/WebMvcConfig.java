package com.wen.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class WebMvcConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http   // 全注解配置实现的开端, 表示开始需要的权限
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                // 认证后才能访问
                .anyRequest().authenticated()
                // 表示使csrf拦截失效
                .and().csrf().disable();
    }
}
