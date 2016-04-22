package kr.nomad.mars.dto;

import java.util.ArrayList;
import java.util.List;

public class MedExam {
	
	int seq = 0;
	int sort = 0;
	String comment = "";
	String value = "";
	int askind = 0;
	int pseq = 0;
	int move = 0;
	String ansType = "";
	String ansvalue = "";
	int isLast = 0;
	List answerList = new ArrayList();
	
	
	public List getAnswerList() {
		return answerList;
	}
	public void setAnswerList(List answerList) {
		this.answerList = answerList;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getAskind() {
		return askind;
	}
	public void setAskind(int askind) {
		this.askind = askind;
	}
	public int getPseq() {
		return pseq;
	}
	public void setPseq(int pseq) {
		this.pseq = pseq;
	}
	public int getMove() {
		return move;
	}
	public void setMove(int move) {
		this.move = move;
	}
	
	public String getAnsType() {
		return ansType;
	}
	public void setAnsType(String ansType) {
		this.ansType = ansType;
	}
	public String getAnsvalue() {
		return ansvalue;
	}
	public void setAnsvalue(String ansvalue) {
		this.ansvalue = ansvalue;
	}
	public int getIsLast() {
		return isLast;
	}
	public void setIsLast(int isLast) {
		this.isLast = isLast;
	}
	
	

}
