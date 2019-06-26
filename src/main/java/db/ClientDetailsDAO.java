package main.java.db;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;

/**
 * oauth_client_details テーブルにおける行ごとの情報を保持するDAOクラス。
 *
 */
@Service("ClientDetailsDAO")
public interface ClientDetailsDAO {

	/**
	 * 主キーに対応する行を削除する。<br>
	 * 削除対象の行が存在しない場合、戻り値として0が返却される。
	 *
	 * @param clientId
	 *            主キー
	 * @return 削除した行数
	 */
	@Delete({ "delete from oauth_client_details", "where client_id = #{clientId,jdbcType=VARCHAR}" })
	int delete(String clientId);

	/**
	 * {@link ClientDetailsDTO}に格納された情報を挿入する。<br>
	 * 主キーに該当するフィールドがnullでないこと。<br>
	 * 主キーが重複する場合、<br>
	 * {@link org.springframework.dao.DuplicateKeyException}がスローされる。
	 *
	 * @param record
	 *            挿入する情報
	 * @return 挿入した行数
	 */
	@Insert({ "insert into oauth_client_details (client_id, ", "client_secret, ", "resource_ids, ", "scope, ",
			"authorized_grant_types, ", "web_server_redirect_uri, ", "authorities, ", "access_token_validity, ",
			"refresh_token_validity, ", "additional_information)", "values (#{clientId,jdbcType=VARCHAR}, ",
			"#{clientSecret,jdbcType=VARCHAR},  ", "#{resourceIds,jdbcType=VARCHAR},  ", "#{scope,jdbcType=VARCHAR},  ",
			"#{authorizedGrantTypes,jdbcType=VARCHAR},  ", "#{webServerRedirectUri,jdbcType=VARCHAR},  ",
			"#{authorities,jdbcType=VARCHAR},  ", "#{accessTokenValidity,jdbcType=INTEGER}, ",
			"#{refreshTokenValidity,jdbcType=INTEGER}, ", "#{additionalInformation,jdbcType=VARCHAR})" })
	int insert(ClientDetailsDTO record);

	/**
	 * 主キーの行に対応する{@link ClientDetailsDTO}を取得する。<br>
	 * 参照対象の行が存在しない場合、nullが返却される。
	 *
	 * @param clientId
	 *            主キー
	 * @return 取得した情報
	 */
	@Select({ "select", "client_id, ", "client_secret, ", "resource_ids, ", "scope, ", "authorized_grant_types, ",
			"web_server_redirect_uri, ", "authorities, ", "access_token_validity, ", "refresh_token_validity, ",
			"additional_information", "from oauth_client_details", "where client_id = #{clientId,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "client_id", property = "clientId", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "client_secret", property = "clientSecret", jdbcType = JdbcType.VARCHAR),
			@Result(column = "resource_ids", property = "resourceIds", jdbcType = JdbcType.VARCHAR),
			@Result(column = "scope", property = "scope", jdbcType = JdbcType.VARCHAR),
			@Result(column = "authorized_grant_types", property = "authorizedGrantTypes", jdbcType = JdbcType.VARCHAR),
			@Result(column = "web_server_redirect_uri", property = "webServerRedirectUri", jdbcType = JdbcType.VARCHAR),
			@Result(column = "authorities", property = "authorities", jdbcType = JdbcType.VARCHAR),
			@Result(column = "access_token_validity", property = "accessTokenValidity", jdbcType = JdbcType.INTEGER),
			@Result(column = "refresh_token_validity", property = "refreshTokenValidity", jdbcType = JdbcType.INTEGER),
			@Result(column = "additional_information", property = "additionalInformation", jdbcType = JdbcType.VARCHAR) })
	ClientDetailsDTO select(String clientId);

    /**
     * トークンキーを持っていないクライアントの抽出 <br>
     * 参照対象の行が存在しない場合、nullが返却される。
     *
     * @return 取得した情報
     */
    @Select({
            "select",
            "oauth_client_details.client_id, ",
            "oauth_client_details.client_secret, ",
            "oauth_client_details.resource_ids, ",
            "oauth_client_details.scope, ",
            "oauth_client_details.authorized_grant_types, ",
            "oauth_client_details.web_server_redirect_uri, ",
            "oauth_client_details.authorities, ",
            "oauth_client_details.access_token_validity, ",
            "oauth_client_details.refresh_token_validity, ",
            "oauth_client_details.additional_information, ",
            "oauth_access_token.token_id",
            "from oauth_client_details ",
            "left join oauth_access_token ",
            "on oauth_client_details.client_id = oauth_access_token.client_id ",
            "where oauth_access_token.token_id is null"})
    @Results({
            @Result(column = "client_id", property = "clientId", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "client_secret", property = "clientSecret", jdbcType = JdbcType.VARCHAR),
            @Result(column = "resource_ids", property = "resourceIds", jdbcType = JdbcType.VARCHAR),
            @Result(column = "scope", property = "scope", jdbcType = JdbcType.VARCHAR),
            @Result(column = "authorized_grant_types", property = "authorizedGrantTypes", jdbcType = JdbcType.VARCHAR),
            @Result(column = "web_server_redirect_uri", property = "webServerRedirectUri", jdbcType = JdbcType.VARCHAR),
            @Result(column = "authorities", property = "authorities", jdbcType = JdbcType.VARCHAR),
            @Result(column = "access_token_validity", property = "accessTokenValidity", jdbcType = JdbcType.INTEGER),
            @Result(column = "refresh_token_validity", property = "refreshTokenValidity", jdbcType = JdbcType.INTEGER),
            @Result(column = "additional_information", property = "additionalInformation", jdbcType = JdbcType.VARCHAR) })
    List<ClientDetailsDTO> selectNoAccessTokenClientDetailsList();
}