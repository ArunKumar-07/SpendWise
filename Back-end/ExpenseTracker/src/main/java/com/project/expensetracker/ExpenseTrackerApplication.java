package com.project.expensetracker;

import com.project.expensetracker.config.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class ExpenseTrackerApplication {
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerApplication.class, args);

	}


	@Bean
	public FilterRegistrationBean<JwtRequestFilter> jwtFilter() {
		final FilterRegistrationBean<JwtRequestFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(jwtRequestFilter);
		registrationBean.addUrlPatterns("/new/*"); // Apply the filter to all requests under /api
		return registrationBean;
	}

}
