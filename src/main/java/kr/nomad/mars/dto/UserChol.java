package kr.nomad.mars.dto;

public class UserChol {
	
	int seq = 0;
	String userId = "";
	int ldl = 0;
	int cholesterol = 0;
	int status  = 0;
	int status2=0;
	String regDate = "";
	
	String statusTxt="";
	
	int tg=0;
	int hdl=0;
	int type=0;
	
	int checkSeq=0;
	
	
	public int getCheckSeq() {
		return checkSeq;
	}
	public void setCheckSeq(int checkSeq) {
		this.checkSeq = checkSeq;
	}
	public int getTg() {
		return tg;
	}
	public void setTg(int tg) {
		this.tg = tg;
	}
	public int getHdl() {
		return hdl;
	}
	public void setHdl(int hdl) {
		this.hdl = hdl;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus2() {
		return status2;
	}
	public void setStatus2(int status2) {
		this.status2 = status2;
	}
	public String getStatusTxt() {
		return statusTxt;
	}
	public void setStatusTxt(String statusTxt) {
		this.statusTxt = statusTxt;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getLdl() {
		return ldl;
	}
	public void setLdl(int ldl) {
		this.ldl = ldl;
	}
	
	public int getCholesterol() {
		return cholesterol;
	}
	public void setCholesterol(int cholesterol) {
		this.cholesterol = cholesterol;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	

}
