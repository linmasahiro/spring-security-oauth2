package main.java.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * OAuth2認証リソースサーバーの設定
 *
 * @author k80092@hotmail.com
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	public ResourceServerConfiguration() {
		super();
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		super.configure(resources);
	}

	/**
	 * 保護されているURLはここで設定する
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
		.and()
		.requestMatchers()
		.anyRequest().and().anonymous().and().authorizeRequests()
		.antMatchers("/url/v1/api/member/**").authenticated();
	}
}