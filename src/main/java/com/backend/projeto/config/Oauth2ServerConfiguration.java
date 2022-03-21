package com.backend.projeto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import com.backend.projeto.service.MyUserDetailService;

@SuppressWarnings("deprecation")
@Configuration
@EnableResourceServer
public class Oauth2ServerConfiguration extends ResourceServerConfigurerAdapter {
	
	private static final String RESOURCE_ID = "restservice";

	@Override
	public void configure(ResourceServerSecurityConfigurer resource) {
		resource.resourceId(RESOURCE_ID);
	}
	
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.logout()
		.invalidateHttpSession(true)
		.clearAuthentication(true)
		.and()
		.authorizeRequests()
		.anyRequest()
		.fullyAuthenticated();
	}
	
	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{
		
		private TokenStore tokenStore = new InMemoryTokenStore();
		
		@Autowired
		@Qualifier("authenticationManagerBean")
		private AuthenticationManager authenticationManager;
		
		@Autowired
		private MyUserDetailService userDetailService;
		
		@Autowired
		private PasswordEncoder passwordEnconder;
		
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpointsConfigurer) throws Exception{
			endpointsConfigurer.tokenStore(this.tokenStore).authenticationManager(this.authenticationManager).userDetailsService(userDetailService);
		}
		
		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
			clients.inMemory().withClient("client").authorizedGrantTypes("password", "authorization_code", "refresh_token").scopes("all")
			.refreshTokenValiditySeconds(30000).resourceIds(RESOURCE_ID).secret(passwordEnconder.encode("123")).accessTokenValiditySeconds(50000);
		}
		
		@Bean
		@Primary
		public DefaultTokenServices tokenServices() {
			DefaultTokenServices tokenServices = new DefaultTokenServices();
			tokenServices.setSupportRefreshToken(true);
			tokenServices.setTokenStore(this.tokenStore);
			return tokenServices;
		}
	}
}
