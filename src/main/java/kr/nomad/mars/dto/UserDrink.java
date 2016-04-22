package kr.nomad.mars.dto;

public class UserDrink {
	
	int seq = 0;
	String userId = "";
	int avgPeriodDrinking  = 0;
	int avgDrinkingCapacity= 0;
	int status  = 0;
	String regDate = "";
	
	String statusTxt="";
	
	int medSeq=0;
	String apdtxt="";
	String adctxt="";
	
	
	public String getApdtxt() {
		String txt="";
		if(this.avgPeriodDrinking==0){
			txt="안마심";
		}
		if(this.avgPeriodDrinking==1){
			txt="월 2회";
		}
		if(this.avgPeriodDrinking==2){
			txt="주 1회";
		}
		if(this.avgPeriodDrinking==3){
			txt="주 3회";
		}
		if(this.avgPeriodDrinking==4){
			txt="거의 매일";
		}
		return txt;
	}
	public String getAdctxt() {
		String txt="";
		if(this.avgDrinkingCapacity==0){
			txt="소주반병(맥주 3잔)이하";
		}
		if(this.avgDrinkingCapacity==1){
			txt="소주1병(맥주 7잔)이하";
		}
		if(this.avgDrinkingCapacity==2){
			txt="소주2병(맥주 5병)미만";
		}
		if(this.avgDrinkingCapacity==3){
			txt="소주2병(맥주 5병)이상";
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
	public int getAvgPeriodDrinking() {
		return avgPeriodDrinking;
	}
	public void setAvgPeriodDrinking(int avgPeriodDrinking) {
		this.avgPeriodDrinking = avgPeriodDrinking;
	}
	public int getAvgDrinkingCapacity() {
		return avgDrinkingCapacity;
	}
	public void setAvgDrinkingCapacity(int avgDrinkingCapacity) {
		this.avgDrinkingCapacity = avgDrinkingCapacity;
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
