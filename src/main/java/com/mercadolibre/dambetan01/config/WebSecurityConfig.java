package com.mercadolibre.dambetan01.config;

import com.mercadolibre.dambetan01.security.JWTAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/v1/sign-in").permitAll()
//                .antMatchers(HttpMethod.GET, "/ping").permitAll()
//                .antMatchers(HttpMethod.GET, "/v3/api-docs").permitAll()
//                .antMatchers(HttpMethod.GET, "/fake").permitAll()
//
//                .anyRequest().authenticated();
//    }

    /*
     * remove the comment of the method bellow to view the database structure created in the h2 database
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/fresh-products/inboundorder").permitAll();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
