package com.scsa.model.vo;

public class GreenPoint {
	public String greenPointId;
	public int greenPointValue;
	public int greenPointType; //1이면 취득 -1이면 사용
	public String grrenPointDate;
	
	public String userId;
	public String compId;
	public String getGreenPointId() {
		return greenPointId;
	}
	public void setGreenPointId(String greenPointId) {
		this.greenPointId = greenPointId;
	}
	public int getGreenPointValue() {
		return greenPointValue;
	}
	public void setGreenPointValue(int greenPointValue) {
		this.greenPointValue = greenPointValue;
	}
	public int getGreenPointType() {
		return greenPointType;
	}
	public void setGreenPointType(int greenPointType) {
		this.greenPointType = greenPointType;
	}
	public String getGrrenPointDate() {
		return grrenPointDate;
	}
	public void setGrrenPointDate(String grrenPointDate) {
		this.grrenPointDate = grrenPointDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCompId() {
		return compId;
	}
	public void setCompId(String compId) {
		this.compId = compId;
	}
	
	
}
