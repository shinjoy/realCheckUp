package kr.nomad.mars.dto;

public class Procedure {
	
	int diseaseType=0;
	String diseaseCode="";
	String diseaseName="";
	double ristRat=0.0;
	
	public int getDiseaseType() {
		return diseaseType;
	}
	public void setDiseaseType(int diseaseType) {
		this.diseaseType = diseaseType;
	}
	public String getDiseaseCode() {
		return diseaseCode;
	}
	public void setDiseaseCode(String diseaseCode) {
		this.diseaseCode = diseaseCode;
	}
	public String getDiseaseName() {
		return diseaseName;
	}
	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}
	public double getRistRat() {
		return ristRat;
	}
	public void setRistRat(double ristRat) {
		this.ristRat = ristRat;
	}
	
	
	
	

}
