package com.ntc.fraud.whois;

public class WhoisEntity {

	private String domainName;
	private String RegistryExpiryDate;
	private String CreationDate;
	private String UpdatedDate;
	private String recordCreatedOn;
	private String recordEexpiresOn;
	private long updateToNow;//更新日期距今時間
	private long CreationDateToNow;//創建日期距今
	private long NowToRegistryExpiryDate;// 現在到註冊到期日
	private String ScoreExp; //到期分數
	private String ScoreUpd; //更新分數
	private String ScoreCre; //創立分數
	private String mxScore;
	private String dicScore;
	private String prediction;
	private boolean notExist;


	public String getMxScore() {
		return mxScore;
	}



	public void setMxScore(String mxScore) {
		this.mxScore = mxScore;
	}



	public String getDicScore() {
		return dicScore;
	}



	public void setDicScore(String dicScore) {
		this.dicScore = dicScore;
	}



	public String getPrediction() {
		return prediction;
	}



	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}



	public WhoisEntity() {
	}



	public WhoisEntity(String domainName, String registryExpiryDate, String creationDate, String updatedDate,
					   String recordCreatedOn, String recordEexpiresOn, long updateToNow, long creationDateToNow,
					   long nowToRegistryExpiryDate, String scoreExp, String scoreUpd, String scoreCre, boolean notExist) {
		this.domainName = domainName;
		RegistryExpiryDate = registryExpiryDate;
		CreationDate = creationDate;
		UpdatedDate = updatedDate;
		this.recordCreatedOn = recordCreatedOn;
		this.recordEexpiresOn = recordEexpiresOn;
		this.updateToNow = updateToNow;
		CreationDateToNow = creationDateToNow;
		NowToRegistryExpiryDate = nowToRegistryExpiryDate;
		ScoreExp = scoreExp;
		ScoreUpd = scoreUpd;
		ScoreCre = scoreCre;
		this.notExist = notExist;
	}

	public boolean isNotExist() {
		return notExist;
	}



	public void setNotExist(boolean notExist) {
		this.notExist = notExist;
	}



	public long getNowToRegistryExpiryDate() {
		return NowToRegistryExpiryDate;
	}








	public String getScoreExp() {
		return ScoreExp;
	}






	public void setScoreExp(String scoreExp) {
		ScoreExp = scoreExp;
	}






	public String getScoreUpd() {
		return ScoreUpd;
	}






	public void setScoreUpd(String scoreUpd) {
		ScoreUpd = scoreUpd;
	}






	public String getScoreCre() {
		return ScoreCre;
	}






	public void setScoreCre(String scoreCre) {
		ScoreCre = scoreCre;
	}






	public void setNowToRegistryExpiryDate(long nowToRegistryExpiryDate) {
		NowToRegistryExpiryDate = nowToRegistryExpiryDate;
	}









	public long getUpdateToNow() {
		return updateToNow;
	}


	public void setUpdateToNow(long updateToNow) {
		this.updateToNow = updateToNow;
	}


	public long getCreationDateToNow() {
		return CreationDateToNow;
	}


	public void setCreationDateToNow(long creationDateToNow) {
		CreationDateToNow = creationDateToNow;
	}


	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getRegistryExpiryDate() {
		return RegistryExpiryDate;
	}

	public void setRegistryExpiryDate(String registryExpiryDate) {
		RegistryExpiryDate = registryExpiryDate;
	}

	public String getCreationDate() {
		return CreationDate;
	}

	public void setCreationDate(String creationDate) {
		CreationDate = creationDate;
	}

	public String getUpdatedDate() {
		return UpdatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		UpdatedDate = updatedDate;
	}

	public String getRecordCreatedOn() {
		return recordCreatedOn;
	}

	public void setRecordCreatedOn(String recordCreatedOn) {
		this.recordCreatedOn = recordCreatedOn;
	}

	public String getRecordEexpiresOn() {
		return recordEexpiresOn;
	}

	public void setRecordEexpiresOn(String recordEexpiresOn) {
		this.recordEexpiresOn = recordEexpiresOn;
	}



	@Override
	public String toString() {
		return "WhoisEntity [domainName=" + domainName + ", RegistryExpiryDate=" + RegistryExpiryDate
				+ ", CreationDate=" + CreationDate + ", UpdatedDate=" + UpdatedDate + ", recordCreatedOn="
				+ recordCreatedOn + ", recordEexpiresOn=" + recordEexpiresOn + ", updateToNow=" + updateToNow
				+ ", CreationDateToNow=" + CreationDateToNow + ", NowToRegistryExpiryDate=" + NowToRegistryExpiryDate
				+ ", ScoreExp=" + ScoreExp + ", ScoreUpd=" + ScoreUpd + ", ScoreCre=" + ScoreCre + ", mxScore="
				+ mxScore + ", dicScore=" + dicScore + ", prediction=" + prediction + ", notExist=" + notExist + "]";
	}







}
