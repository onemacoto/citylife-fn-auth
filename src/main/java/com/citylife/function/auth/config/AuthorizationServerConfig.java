package com.citylife.function.auth.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.citylife.function.auth.service.UsernameUserDetailService;

@Configuration
@Order(2)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private UsernameUserDetailService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

//    @Bean("jdbcTokenStore")
//    public JdbcTokenStore getJdbcTokenStore() {
//        return new JdbcTokenStore(dataSource);
//    }

	@Bean("jdbcClientDetailsService")
	public JdbcClientDetailsService jdbcClientDetailsService() {
		return new JdbcClientDetailsService(dataSource);
	}

	@Bean
	public TokenStore redisTokenStore() {
		return new RedisTokenStore(redisConnectionFactory);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// 使用JdbcClientDetailsService客户端详情服务
		clients.withClientDetails(jdbcClientDetailsService());
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.authenticationManager(authenticationManager).tokenStore(redisTokenStore());
		// 配置JwtAccessToken转换器
		// .accessTokenConverter(jwtAccessTokenConverter())
		// refresh_token需要userDetailsService
		// .reuseRefreshTokens(false).userDetailsService(userDetailsService);
		// .tokenStore(getJdbcTokenStore());
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer
				// 开启/oauth/token_key验证端口无权限访问
				.tokenKeyAccess("permitAll()")
				// 开启/oauth/check_token验证端口认证权限访问
				.checkTokenAccess("isAuthenticated()");
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

}
