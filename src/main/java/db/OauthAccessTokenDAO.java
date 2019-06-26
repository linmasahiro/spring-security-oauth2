package main.java.db;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

/**
 * oauth_access_token テーブルにおける行ごとの情報を保持するDAOクラス。
 *
 */
@Service("OauthAccessTokenDAO")
public interface OauthAccessTokenDAO {

	/**
	 * 主キーの行に対応する{@link ClientDetailsDTO}を取得する。<br>
	 * 参照対象の行が存在しない場合、nullが返却される。
	 *
	 * @param clientId
	 *            主キー
	 * @return 取得した情報
	 */
	@Select({ "select", "token_id, ", "token, ", "authentication_id, ", "user_name, ", "client_id, ",
			"authentication, ", "refresh_token ", "from oauth_access_token" })
	@Results({ @Result(column = "token_id", property = "tokenId", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "token", property = "token", jdbcType = JdbcType.BINARY),
			@Result(column = "authentication_id", property = "authenticationId", jdbcType = JdbcType.VARCHAR),
			@Result(column = "user_name", property = "userName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "client_id", property = "clientId", jdbcType = JdbcType.VARCHAR),
			@Result(column = "authentication", property = "authentication", jdbcType = JdbcType.BINARY),
			@Result(column = "refresh_token", property = "refreshToken", jdbcType = JdbcType.VARCHAR) })
	List<OauthAccessTokenDTO> selectAccessTokenList();
}