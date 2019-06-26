package main.java.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import main.java.service.MyClientDetailsService;

/**
 * OAuth2認証サーバーの設定
 *
 * @author k80092@hotmail.com
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private MyClientDetailsService clientDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public AuthorizationServerConfiguration() {
		super();
	}

	/**
	 * クライアント情報の挿入
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetailsService);

		// DB を使わない時にこっち
		// clients.inMemory()
		// .withClient("oauth_client")
		// .authorizedGrantTypes("password", "authorization_code",
		// "refresh_token", "implicit")
		// .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT").scopes("read",
		// "write", "trust")
		// .secret("123456")
		// .accessTokenValiditySeconds(120)
		// .refreshTokenValiditySeconds(600);
	}

	/**
	 * TokenStore、TokenGranter、OAuth2RequestFactory等の設定はここ
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		// エンドポイントを設定する
		endpoints
			.pathMapping("/oauth/token", "/url/v1/oauth/token")
			.tokenStore(tokenStore)
			.authenticationManager(authenticationManager);

		// TokenServicesを設定する
		DefaultTokenServices tokenServices = new DefaultTokenServices();
				tokenServices.setTokenStore(endpoints.getTokenStore());
				tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
				tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
				tokenServices.setAccessTokenValiditySeconds(10); // 秒
				tokenServices.setRefreshTokenValiditySeconds(60);
				tokenServices.setSupportRefreshToken(true);
				tokenServices.setReuseRefreshToken(false);
				endpoints.tokenServices(tokenServices);
	}

	/**
	 * 認証サーバーの認証方式設定
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.passwordEncoder(passwordEncoder()).allowFormAuthenticationForClients();
	}
}