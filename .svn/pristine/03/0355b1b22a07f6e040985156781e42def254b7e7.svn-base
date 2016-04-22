package kr.nomad.mars.dto;

public class UserSmoke {
	
	int seq = 0;
	String userId = "";
	int avgSmoke  = 0;
	int status  = 0;
	String regDate = "";
	
	String statusTxt="";
	
	int medSeq=0;
	
	String avgSmoketxt="";
	
	
	
	
	public String getAvgSmoketxt() {
		String txt="";
		if(this.avgSmoke==0){
			txt="피웠지만 끊었음";
		}
		if(this.avgSmoke==1){
			txt="매일 0.5갑 미만";
		}
		if(this.avgSmoke==2){
			txt="매일 0.5~1갑 미만";
		}
		if(this.avgSmoke==3){
			txt="매일 1~2갑 미만";
		}
		if(this.avgSmoke==4){
			txt="매일 2갑 이상";
		}
		
		return txt;
	}
	public int getMedSeq() {
		return medSeq;
	}
	public void setMedSeq(int medSeq) {
		this.medSeq = medSeq;
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
	public int getAvgSmoke() {
		return avgSmoke;
	}
	public void setAvgSmoke(int avgSmoke) {
		this.avgSmoke = avgSmoke;
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
