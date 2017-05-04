package org.netosoft.edu.oauth.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableAuthorizationServer
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.antMatcher("/**").authorizeRequests()
//			.antMatchers("/", "/login**", "/webjars/**", "/js/**").permitAll()
			.anyRequest().authenticated().and()
			.exceptionHandling().authenticationEntryPoint(
					new LoginUrlAuthenticationEntryPoint("/")).and()
//			.logout().logoutSuccessUrl("/").permitAll().and()
			.csrf().csrfTokenRepository(
					CookieCsrfTokenRepository.withHttpOnlyFalse());
	}
}
