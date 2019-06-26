package main.java.db;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

/**
 * t_pharmacist テーブルに対する抽象DAOインターフェース。
 *
 */
public interface UserDetailsDAO {

    /**
     * 主キーの行に対応する{@link TPharmacistDTO}を取得する。<br>
     * 参照対象の行が存在しない場合、nullが返却される。
     *
     * @param key 主キー
     * @return 取得した情報
     */
    @Select({
            "select",
            "contractor_id, store_id, pharmacist_id, password, password_alert_date, password_expiration_date, ",
            "password_init_flag, password_history, login_trial_count, login_trial_recent_date, ",
            "delete_flag, register_timestamp, update_timestamp",
            "from t_pharmacist",
            "where pharmacist_id = #{pharmacistId,jdbcType=VARCHAR}"})
    @Results({
            @Result(column = "contractor_id", property = "contractorId", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "store_id", property = "storeId", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "pharmacist_id", property = "pharmacistId", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "password_alert_date", property = "passwordAlertDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "password_expiration_date", property = "passwordExpirationDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "password_init_flag", property = "passwordInitFlag", jdbcType = JdbcType.BIT),
            @Result(column = "password_history", property = "passwordHistory", jdbcType = JdbcType.VARCHAR),
            @Result(column = "login_trial_count", property = "loginTrialCount", jdbcType = JdbcType.INTEGER),
            @Result(column = "login_trial_recent_date", property = "loginTrialRecentDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "delete_flag", property = "deleteFlag", jdbcType = JdbcType.BIT),
            @Result(column = "register_timestamp", property = "registerTimestamp", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_timestamp", property = "updateTimestamp", jdbcType = JdbcType.TIMESTAMP)})
    UserDetailsDTO select(String pharmacistId);
}
