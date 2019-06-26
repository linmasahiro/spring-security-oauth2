package main.java.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;

import main.java.service.MyClientDetailsService;
import main.java.service.MyUserDetailsService;

/**
 * OAuth2セキュリティ設定
 *
 * @author k80092@hotmail.com
 *
 */
@Order(100)
@Configuration
@EnableWebSecurity
public class OAuth2SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyClientDetailsService clientDetailsService;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.anonymous().disable()
		.requestMatchers().antMatchers("/url/v1/oauth/token")
		.and()
		.authorizeRequests()
		.anyRequest()
		.authenticated();
	}

	/**
	 * 利用者情報はここから作成する
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);

		// DB使わない場合はこっち
		// auth.inMemoryAuthentication()
		// .withUser("user1")
		// .password("123456")
		// .roles("CLIENT")
		// .and()
		// .withUser("user2")
		// .password("123456")
		// .roles("CLIENT");

	}

	/**
	 * 認証マネージャー
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManager();
	}

	// XMLファイルに設定済みですのでこのBeanは不要
	// @Bean
	// public TokenStore tokenStore() {
	// return new InMemoryTokenStore();
	// }

	/**
	 * 利用者認証ハンドラー
	 *
	 * @param tokenStore
	 * @return
	 */
	@Bean
	@Autowired
	public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore) {
		TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
		handler.setTokenStore(tokenStore);
		handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
		handler.setClientDetailsService(clientDetailsService);
		return handler;
	}

	/**
	 * トークン発行
	 *
	 * @param tokenStore
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Autowired
	public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
		TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
		return store;
	}

}