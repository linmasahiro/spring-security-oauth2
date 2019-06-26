
package main.java.test;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import main.java.db.ClientDetailsDAO;
import main.java.db.ClientDetailsDTO;
import main.java.db.OauthAccessTokenDAO;
import main.java.db.OauthAccessTokenDTO;

/**
 * OAuth認証テストコントローラー
 *
 * @author k80092@hotmail.com
 *
 */
@Controller
@RequestMapping(value = "/v1")
public class TestController {

	@Autowired
	private TokenStore tokenStore;

	/** 処方せん受付情報DAO */
	@Autowired
	private ClientDetailsDAO oauthClientDetailsDAO;

	/** アクセストークン情報DAO */
	@Autowired
	private OauthAccessTokenDAO oauthAccessTokenDAO;

	/**
	 * テスト画面表示
	 *
	 * @return
	 */
	@RequestMapping(value = "/", method = GET)
	public String index() {
		return "index";
	}

	/**
	 * クライアント情報作成
	 *
	 * @return
	 */
	@RequestMapping(value = "/api/publishClientInfo", method = GET)
	public void publishClientInfo(HttpServletResponse response) throws IOException {
		// クライアントID、クライアントPW発行する
		String clientId = this.generateClientId();
		String clientSecret = "123456";
		// クライアントPW 暗号化
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPw = passwordEncoder.encode(clientSecret);

		// 認証情報作成
		ClientDetailsDTO oauthClientDetails = new ClientDetailsDTO();
		oauthClientDetails.setClientSecret(hashedPw);
		oauthClientDetails.setScope("trust");
		oauthClientDetails.setAuthorizedGrantTypes("password,refresh_token,client_credentials");
		oauthClientDetails.setAuthorities("ROLE_CLIENT");
		oauthClientDetails.setAccessTokenValidity(60);
		oauthClientDetails.setRefreshTokenValidity(120);
		oauthClientDetails.setClientId(clientId);

		// 認証情報作成・挿入
		oauthClientDetailsDAO.insert(oauthClientDetails);

		response.getWriter().write("{\"client_id\":\"" + clientId + "\",\"client_secret\":\"" + clientSecret + "\"}");
	}

	/**
	 * ClientIDを生成します．
	 *
	 * @return ClientID
	 */
	private String generateClientId() {
		UUID uuid = null;
		// TODO 使えるUIDをチェックする、無限ループにならない？
		while (true) {
			uuid = UUID.randomUUID();
			ClientDetailsDTO ret = oauthClientDetailsDAO.select(uuid.toString());
			if (ret == null) {
				break;
			}
		}

		return uuid.toString();
	}

	/**
	 * 認証を通している画面
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/api/member/{id}", method = GET)
	public String getProductInfo(@PathVariable String id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("xxxxxxxxxxxxxxxxxxxxxx");
		UserDetails userDetail = (UserDetails) authentication.getPrincipal();
		String username = userDetail.getUsername();
		String password = userDetail.getPassword();
		System.out.println("Username:" + username);
		System.out.println("Password:" + password);
		System.out.println("xxxxxxxxxxxxxxxxxxxxxx");
		return "user";
	}

	/**
	 * 認証が通していない画面
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/api/noauth/{id}", method = GET)
	public String getProductInfoNoAuth(@PathVariable String id) {
		return "user";

	}

	/**
	 * 期限切れのレコードをクリア
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/api/clear_oauth", method = GET)
	@ResponseBody
	public void clearOauthRecords() {

		List<OauthAccessTokenDTO> list = oauthAccessTokenDAO.selectAccessTokenList();
		for (OauthAccessTokenDTO accessTokenInfo : list) {
			OAuth2AccessToken accessToken = SerializationUtils.deserialize(accessTokenInfo.getToken());
			if (accessToken.isExpired()) {
				tokenStore.removeRefreshToken(accessToken.getRefreshToken());
				tokenStore.removeAccessToken(accessToken);
				oauthClientDetailsDAO.delete(accessTokenInfo.getClientId());
				System.out.println("xxxxxxxxxx OAuth Logout Remove AccessToken xxxxxxxxxxx");
			}
		}

		List<ClientDetailsDTO> clientDetailsList = oauthClientDetailsDAO.selectNoAccessTokenClientDetailsList();
		for (ClientDetailsDTO clientDetails : clientDetailsList) {
			oauthClientDetailsDAO.delete(clientDetails.getClientId());
		}
	}

	/**
	 * ログアウト処理
	 *
	 * @return
	 */
	@RequestMapping(value = "/logout", method = GET)
	public ResponseEntity<String> logOut(@RequestParam("access_token") String token) {
		// アクセストークン存在チェック
		OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
		if (accessToken != null) {
			// アクセストークンを削除する
			tokenStore.removeAccessToken(accessToken);
			System.out.println("xxxxxxxxxx OAuth Logout Remove AccessToken xxxxxxxxxxx");

		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}
}
