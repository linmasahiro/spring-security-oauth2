package main.java.db;

import java.io.Serializable;

/**
 * oauth_access_token テーブルにおける行ごとの情報を保持するDTOクラス。
 *
 */
public class OauthAccessTokenDTO implements Serializable {

	private String tokenId;

	private byte[] token;

	private String authenticationId;

	private String userName;

	private String clientId;

	private byte[] authentication;

	private String refreshToken;

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public byte[] getToken() {
		return token;
	}

	public void setToken(byte[] token) {
		this.token = token;
	}

	public String getAuthenticationId() {
		return authenticationId;
	}

	public void setAuthenticationId(String authenticationId) {
		this.authenticationId = authenticationId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public byte[] getAuthentication() {
		return authentication;
	}

	public void setAuthentication(byte[] authentication) {
		this.authentication = authentication;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	/**
	 * デフォルトコンストラクタ。
	 */
	public OauthAccessTokenDTO() {
		super();
	}
}
