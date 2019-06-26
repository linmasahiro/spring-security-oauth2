package main.java.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import main.java.db.ClientDetailsDAO;
import main.java.db.ClientDetailsDTO;

@Service("MyClientDetailsService")
public class MyClientDetailsService implements ClientDetailsService {

	@Autowired
	private ClientDetailsDAO clientDetailsDAO;

	/**
	 * クライアントID検証
	 */
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

		ClientDetailsDTO clientInfo = clientDetailsDAO.select(clientId);
		if (clientInfo != null) {
			BaseClientDetails details = new BaseClientDetails();
			details.setClientId(clientInfo.getClientId());
			details.setAuthorizedGrantTypes(Arrays.asList(clientInfo.getAuthorizedGrantTypes().split(",")));
			details.setScope(Arrays.asList(clientInfo.getScope().split(",")));
			details.setAutoApproveScopes(Arrays.asList(clientInfo.getScope().split(",")));
			details.setAuthorities(AuthorityUtils.createAuthorityList(clientInfo.getAuthorities().split(",")));
			details.setClientSecret(clientInfo.getClientSecret());
			details.setAccessTokenValiditySeconds(clientInfo.getAccessTokenValidity());
			details.setRefreshTokenValiditySeconds(clientInfo.getRefreshTokenValidity());
			return details;

		} else {
			throw new ClientRegistrationException("not found : " + clientId);

		}
	}

}