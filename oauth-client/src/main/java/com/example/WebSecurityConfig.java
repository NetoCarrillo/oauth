package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;

/**
 *
 * @author ernesto
 */
@Configuration
@EnableOAuth2Client
@EnableAuthorizationServer
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private OAuth2ClientContext oAuth2ClientContext;
	
	@Bean
	public FilterRegistrationBean oauth2ClientFilterRegistration(
			OAuth2ClientContextFilter filter){
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}
	
	@Bean
	public AuthoritiesExtractor authoritiesExtractor(final OAuth2RestOperations template){
		return new AuthoritiesExtractor() {
			@Override
			public List<GrantedAuthority> extractAuthorities(Map<String, Object> map){
				String url = (String)map.get("organizations_url");
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> orgs = template.getForObject(url, List.class);
				
				for(Map<String, Object> org : orgs){
					if("spring-projects".equals(org.get("login"))){
						return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
					}
				}
				throw new BadCredentialsException("Not in Spring Projects origanization");
			}
		};
	}	

	@Bean
	public OAuth2RestTemplate oauth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context){
		return new OAuth2RestTemplate(resource, context);
	}

	@Bean
	@ConfigurationProperties("facebook")
	public ClientResources facebook(){
		return new ClientResources();
	}

	@Bean
	@ConfigurationProperties("github")
	public ClientResources github(){
		return new ClientResources();
	}
	
	@Bean
	@ConfigurationProperties("google")
	public ClientResources google(){
		return new ClientResources();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.antMatcher("/**").authorizeRequests()
			.antMatchers("/", "/login**", "/webjars/**", "/js/**").permitAll()
			.anyRequest().authenticated().and()
			.exceptionHandling().authenticationEntryPoint(
					new LoginUrlAuthenticationEntryPoint("/")).and()
			.logout().logoutSuccessUrl("/").permitAll().and()
			.csrf().csrfTokenRepository(
					CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
			.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
	}

	private Filter ssoFilter(){
		
		List<Filter> filters = new ArrayList<>();
		filters.add(ssoFilter(facebook(), "/login/facebook"));
		filters.add(ssoFilter(github(), "/login/github"));
		filters.add(ssoFilter(google(), "/login/google"));

		CompositeFilter filter = new CompositeFilter();
		filter.setFilters(filters);

		return filter;
	}

	private Filter ssoFilter(ClientResources client, String path){
		OAuth2ClientAuthenticationProcessingFilter filter =
				new OAuth2ClientAuthenticationProcessingFilter(path);
		OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oAuth2ClientContext);
		filter.setRestTemplate(template);
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(
				client.getResource().getUserInfoUri(), client.getClient().getClientId());
		tokenServices.setRestTemplate(template);
		filter.setTokenServices(tokenServices);
		
		return filter;
	}

}
