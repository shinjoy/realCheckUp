package kr.nomad.mars.dto;

public class UserRegData {
	
	int dataSeq = 0;
	String userId = "";
	double stomachCancer = 0.0;
	double liverCancer = 0.0;
	double lugCancer = 0.0;
	double colorectalCancer = 0.0;
	double breastCancer = 0.0;
	double stroke = 0.0;
	double heartdisease = 0.0;
	double diabetesMellitus = 0.0;
	double nephropathy = 0.0;
	double dementia = 0.0;
	int healthScore = 0;
	int userRank = 0;
	String regDate = "";
	int status = 0;
	String statusTxt="";
	//view
	
	int rownum=0;
	
	
	
	public String getStatusTxt() {
		String txt="";
		if(this.status==0){
			txt="데이터 없음";
		}
		if(this.status==1){
			txt="우수";
		}
		if(this.status==2){
			txt="양호";		
		}
		if(this.status==3){
			txt="보통";
		}
		if(this.status==4){
			txt="불량";
		}
		if(this.status==5){
			txt="심각";
		}

		return statusTxt;
	}
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public int getDataSeq() {
		return dataSeq;
	}
	public void setDataSeq(int dataSeq) {
		this.dataSeq = dataSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public double getStomachCancer() {
		return stomachCancer;
	}
	public void setStomachCancer(double stomachCancer) {
		this.stomachCancer = stomachCancer;
	}
	public double getLiverCancer() {
		return liverCancer;
	}
	public void setLiverCancer(double liverCancer) {
		this.liverCancer = liverCancer;
	}
	public double getLugCancer() {
		return lugCancer;
	}
	public void setLugCancer(double lugCancer) {
		this.lugCancer = lugCancer;
	}
	public double getColorectalCancer() {
		return colorectalCancer;
	}
	public void setColorectalCancer(double colorectalCancer) {
		this.colorectalCancer = colorectalCancer;
	}
	public double getBreastCancer() {
		return breastCancer;
	}
	public void setBreastCancer(double breastCancer) {
		this.breastCancer = breastCancer;
	}
	public double getStroke() {
		return stroke;
	}
	public void setStroke(double stroke) {
		this.stroke = stroke;
	}
	public double getHeartdisease() {
		return heartdisease;
	}
	public void setHeartdisease(double heartdisease) {
		this.heartdisease = heartdisease;
	}
	public double getDiabetesMellitus() {
		return diabetesMellitus;
	}
	public void setDiabetesMellitus(double diabetesMellitus) {
		this.diabetesMellitus = diabetesMellitus;
	}
	public double getNephropathy() {
		return nephropathy;
	}
	public void setNephropathy(double nephropathy) {
		this.nephropathy = nephropathy;
	}
	public double getDementia() {
		return dementia;
	}
	public void setDementia(double dementia) {
		this.dementia = dementia;
	}
	
	public int getHealthScore() {
		return healthScore;
	}
	public void setHealthScore(int healthScore) {
		this.healthScore = healthScore;
	}
	public int getUserRank() {
		return userRank;
	}
	public void setUserRank(int userRank) {
		this.userRank = userRank;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	

}
