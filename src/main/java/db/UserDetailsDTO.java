package main.java.db;

import java.io.Serializable;
import java.util.Date;

/**
 * t_pharmacist テーブルにおける行ごとの情報を保持するDTOクラス。
 *
 */
public class UserDetailsDTO  implements Serializable {

	private String contractorId;

	private String storeId;

	private String pharmacistId;

	private String password;

	private Date passwordAlertDate;

	private Date passwordExpirationDate;

	private Boolean passwordInitFlag;

	private String passwordHistory;

	private Integer loginTrialCount;

	private Date loginTrialRecentDate;

	private Boolean deleteFlag;

	private Date registerTimestamp;

	private Date updateTimestamp;

	/** シリアルID */
	private static final long serialVersionUID = 1L;

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getPharmacistId() {
		return pharmacistId;
	}

	public void setPharmacistId(String pharmacistId) {
		this.pharmacistId = pharmacistId;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getPasswordAlertDate() {
		return passwordAlertDate;
	}

	public void setPasswordAlertDate(Date passwordAlertDate) {
		this.passwordAlertDate = passwordAlertDate;
	}

	public Date getPasswordExpirationDate() {
		return passwordExpirationDate;
	}

	public void setPasswordExpirationDate(Date passwordExpirationDate) {
		this.passwordExpirationDate = passwordExpirationDate;
	}

	public Boolean getPasswordInitFlag() {
		return passwordInitFlag;
	}

	public void setPasswordInitFlag(Boolean passwordInitFlag) {
		this.passwordInitFlag = passwordInitFlag;
	}

	public String getPasswordHistory() {
		return passwordHistory;
	}

	public void setPasswordHistory(String passwordHistory) {
		this.passwordHistory = passwordHistory;
	}

	public Integer getLoginTrialCount() {
		return loginTrialCount;
	}

	public void setLoginTrialCount(Integer loginTrialCount) {
		this.loginTrialCount = loginTrialCount;
	}

	public Date getLoginTrialRecentDate() {
		return loginTrialRecentDate;
	}

	public void setLoginTrialRecentDate(Date loginTrialRecentDate) {
		this.loginTrialRecentDate = loginTrialRecentDate;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Date getRegisterTimestamp() {
		return registerTimestamp;
	}

	public void setRegisterTimestamp(Date registerTimestamp) {
		this.registerTimestamp = registerTimestamp;
	}

	public Date getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Date updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	/**
     * デフォルトコンストラクタ。
     */
    public UserDetailsDTO() {
        super();
    }
}
