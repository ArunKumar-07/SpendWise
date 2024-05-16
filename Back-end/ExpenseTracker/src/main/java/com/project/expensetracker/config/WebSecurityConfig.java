//package com.project.expensetracker.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private final JwtRequestFilter jwtRequestFilter;
//
//    @Autowired
//    public WebSecurityConfig(JwtRequestFilter jwtRequestFilter) {
//        this.jwtRequestFilter = jwtRequestFilter;
//    }
//    protected void configure(HttpSecurity http) throws Exception {
////        http.authorizeRequests()
////                // Allow access to these endpoints without authentication
////                .antMatchers("/new/signup", "/new/login").permitAll()
////                // All other requests must be authenticated
////                .anyRequest().authenticated()
////                .and()
////                // Configure other security settings as needed
////                .csrf().disable(); // Disable CSRF protection for simplicity
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/new/signup", "/new/login").permitAll() // Allow sign-up and login without authentication
//                .anyRequest().authenticated()
//                .and()
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//}