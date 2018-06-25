package sample.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
public class AuthServerJwtConfig {
	
	@Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
	    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	    KeyStoreKeyFactory keyStoreKeyFactory = 
	      new KeyStoreKeyFactory(new ClassPathResource("mykeys.jks"), "mypass".toCharArray());
	    converter.setKeyPair(keyStoreKeyFactory.getKeyPair("security-server"));
	    return converter;
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}
}
