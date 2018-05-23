package net.netosoft.oauth.packt.security.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 *
 * @author ernesto
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private RedisConnectionFactory connectionFactory;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients)
			throws Exception{
		clients.inMemory()
				.withClient("clientapp").secret("123456")
//				.redirectUris("http://localhost:9000/callback")
//				.authorizedGrantTypes("password", "implicit", "authorization_code")
				.authorizedGrantTypes("password")
				.accessTokenValiditySeconds(Integer.MAX_VALUE)
//				.scopes("admin", "operator", "read_profile", "read_contacts")
				.scopes("admin", "operator");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)
			throws Exception{
		endpoints.authenticationManager(authenticationManager)
				.tokenStore(tokenStore());
		
	}

	@Bean
	public TokenStore tokenStore(){
		RedisTokenStore redis = new RedisTokenStore(connectionFactory);
		return redis;
	}

}
