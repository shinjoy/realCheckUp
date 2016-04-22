package kr.nomad.mars.dto;

public class UserActivity {
	
	int seq = 0;
	String userId = "";
	int avgActivity  = 0;
	int timeActivity  = 0;
	int status  = 0;
	String regDate = "";
	
	String statusTxt="";
	int medSeq=0;
	String avgActivitytxt="";
	String timeActivitytxt="";
	
	
	
	public String getAvgActivitytxt() {
		String txt="";
		if(this.avgActivity==0){
			txt="전혀 하지 않음";
		}
		if(this.avgActivity==1){
			txt="주 1~2회";
		}
		if(this.avgActivity==2){
			txt="주 3~4회";
		}
		if(this.avgActivity==3){
			txt="주 5회 이상";
		}
		
		return txt;
	}
	public String getTimeActivitytxt() {
		String txt="";
		if(this.timeActivity==0){
			txt="30분이하";
		}
		if(this.timeActivity==1){
			txt="30~60분";
		}
		if(this.timeActivity==2){
			txt="60~120분";
		}
		if(this.timeActivity==3){
			txt="120분 이상";
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
	public int getAvgActivity() {
		return avgActivity;
	}
	public void setAvgActivity(int avgActivity) {
		this.avgActivity = avgActivity;
	}
	public int getTimeActivity() {
		return timeActivity;
	}
	public void setTimeActivity(int timeActivity) {
		this.timeActivity = timeActivity;
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
