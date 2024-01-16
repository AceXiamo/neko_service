package com.xiamo.config;

import com.xiamo.security.AuthenticationEntryPointImpl;
import com.xiamo.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * The type Security config.
 *
 * @Author: AceXiamo
 * @ClassName: SecurityConfig
 * @Date: 2023 /3/3 22:39
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * The Authentication entry point.
     */
    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    /**
     * The Jwt authentication filter.
     */
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * The Cors filter.
     */
    @Autowired
    private CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/wx/login", "/file/view/**", "/**/lock").anonymous()
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // 添加CORS filter
        httpSecurity.addFilterBefore(corsFilter, ChannelProcessingFilter.class);
        httpSecurity.addFilterBefore(corsFilter, JwtAuthenticationFilter.class);
        httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);
    }

}
