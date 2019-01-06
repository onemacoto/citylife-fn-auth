package com.citylife.function.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.citylife.function.auth.provider.UserNameDaoAuthenticationProvider;
import com.citylife.function.auth.service.PhoneUserDetailService;
import com.citylife.function.auth.service.UsernameUserDetailService;

@Configuration
//@Order(ManagementServerProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UsernameUserDetailService usernameUserDetailService;

//	@Autowired
//	private PhoneUserDetailService phoneUserDetailService;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http.addFilterBefore(phoneLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//				// 配置登陆页/login并允许访问
//				.formLogin().loginPage("/login").permitAll()
//				// 登出页
//				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/backReferer")
//				// 其余所有请求全部需要鉴权认证
//				.and().authorizeRequests().anyRequest().authenticated()
//				// 由于使用的是JWT，我们这里不需要csrf
//				.and().csrf().disable();
//	}

//	@Bean
//	public PhoneLoginAuthenticationFilter phoneLoginAuthenticationFilter() {
//		PhoneLoginAuthenticationFilter filter = new PhoneLoginAuthenticationFilter();
//		try {
//			filter.setAuthenticationManager(this.authenticationManagerBean());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		filter.setAuthenticationSuccessHandler(new PhoneLoginAuthSuccessHandler());
//		filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error"));
//		return filter;
//	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) {
	//	auth.authenticationProvider(phoneAuthenticationProvider());
		auth.authenticationProvider(userNameDaoAuthenticationProvider());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(6);
	}

	@Bean
	public UserNameDaoAuthenticationProvider userNameDaoAuthenticationProvider() {
		UserNameDaoAuthenticationProvider provider = new UserNameDaoAuthenticationProvider();
		// 设置userDetailsService
		provider.setUserDetailsService(usernameUserDetailService);
		// 禁止隐藏用户未找到异常
		provider.setHideUserNotFoundExceptions(false);
		// 使用BCrypt进行密码的hash
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

//	@Bean
//	public PhoneAuthenticationProvider phoneAuthenticationProvider() {
//		PhoneAuthenticationProvider provider = new PhoneAuthenticationProvider();
//		// 设置userDetailsService
//		provider.setUserDetailsService(phoneUserDetailService);
//		// 禁止隐藏用户未找到异常
//		provider.setHideUserNotFoundExceptions(false);
//		return provider;
//	}

	@Bean
	public PhoneUserDetailService phoneUserDetailService() {
		PhoneUserDetailService bean = new PhoneUserDetailService();
		return bean;
	}

	@Bean
	public UsernameUserDetailService usernameDetailService() {
		UsernameUserDetailService bean = new UsernameUserDetailService();
		return bean;
	}

}
