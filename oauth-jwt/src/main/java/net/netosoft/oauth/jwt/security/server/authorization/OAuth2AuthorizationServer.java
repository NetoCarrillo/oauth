package net.netosoft.oauth.jwt.security.server.authorization;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 *
 * @author ernesto
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer
				extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AddtionalClaimsTokenEnhancer enhancer;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)
					throws Exception{
		
		TokenEnhancerChain chain = new TokenEnhancerChain();
		chain.setTokenEnhancers(
				Arrays.asList(enhancer, accessTokenConverter()));
		
		endpoints
				.authenticationManager(authenticationManager)
				.tokenStore(jwtTokenStore())
				.tokenEnhancer(chain)
				.accessTokenConverter(accessTokenConverter());
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
		
		clients.inMemory()
				.withClient("clientapp")
				.secret("123456")
				.scopes("read_profile")
				.authorizedGrantTypes("password", "authorization_code");
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security)
					throws Exception{
		security.tokenKeyAccess("permitAll()");
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter()
					throws GeneralSecurityException{
		
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(1024, random);
		KeyPair keyPair = keyGen.generateKeyPair();
		
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setKeyPair(keyPair);
		
		return converter;
	}
	
	@Bean
	public JwtTokenStore jwtTokenStore() throws GeneralSecurityException{
		return new JwtTokenStore(accessTokenConverter());
	}
}
