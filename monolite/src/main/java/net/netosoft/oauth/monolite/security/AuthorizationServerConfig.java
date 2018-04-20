package net.netosoft.oauth.monolite.security;

import java.util.Arrays;
import net.netosoft.oauth.monolite.exceptions.AppExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 *
 * @author ernesto
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Value("${oauth.server.client.id}")
	private String clientId;
	@Value("${oauth.server.client.secret}")
	private String clientSecret;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EncodedClientDetailsService ecds;

	@Bean
	public TokenStore tokenStore(){
		TokenStore tokenStore = new InMemoryTokenStore();
		
		return tokenStore;
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception{
		security.passwordEncoder(passwordEncoder);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception{
		endpoints.authenticationManager(authenticationManager);
		endpoints.tokenStore(tokenStore());
		endpoints.exceptionTranslator(new AppExceptionTranslator());
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
		BaseClientDetails details = new BaseClientDetails();
		details.setClientId(clientId);
		details.setClientSecret(clientSecret);
		details.setScope(Arrays.asList("read", "write"));
		details.setAutoApproveScopes(Arrays.asList(".*"));
		details.setAccessTokenValiditySeconds(Integer.MAX_VALUE);
		details.setRefreshTokenValiditySeconds(Integer.MAX_VALUE);
		details.setAuthorizedGrantTypes(Arrays.asList("authorization_code",
				"password", "client_credentials", "implicit", "refresh_token"));
		
		ecds.addClientDetails(details);
		
		clients.withClientDetails(ecds);
	}
	
	
}