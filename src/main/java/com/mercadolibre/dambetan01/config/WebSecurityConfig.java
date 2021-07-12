package com.mercadolibre.dambetan01.config;

import com.mercadolibre.dambetan01.model.enums.Roles;
import com.mercadolibre.dambetan01.security.JWTAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/v1/register").permitAll()
                .antMatchers("/api/v1/register/seller").permitAll()
                .antMatchers("/api/v1/sign-in").permitAll()
                .antMatchers("/ping").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                .antMatchers("/fake").permitAll()
                .antMatchers("/swagger-ui**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/favicon**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/fresh-products/warehouse**").hasAuthority(Roles.SUPERVISOR.name())
                .antMatchers(HttpMethod.POST, "/api/v1/warehouse**").hasAuthority(Roles.SUPERVISOR.name())
                .antMatchers(HttpMethod.POST, "/api/v1/fresh-products/product**").hasAuthority(Roles.SELLER.name())
                .antMatchers(HttpMethod.GET, "/api/v1/fresh-products/myproducts**").hasAuthority(Roles.SELLER.name())
                .antMatchers("/api/v1/fresh-products/inboundorder**").hasAuthority(Roles.SUPERVISOR.name())
                .anyRequest().authenticated();
    }

    /*
     * remove the comment of the method bellow to view the database structure created in the h2 database
     */

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/").permitAll()
//               .antMatchers("/h2-console/**").permitAll()
//                .antMatchers(HttpMethod.POST,"/api/v1/fresh-products/inboundorder").permitAll();
//        http.csrf().disable();
//        http.headers().frameOptions().disable();
//    }

}
