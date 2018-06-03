package com.scsa.model.vo;

public class GreenPoint {
	private String greenPointId;
	private int greenPointValue;
	private int greenPointType; //1�̸� ��� -1�̸� ���
	private String greenPointDate;
	
	private String greenPointContent;
	private String userId;
	private String compId;
	
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
	public String getGreenPointDate() {
		return greenPointDate;
	}
	public void setGreenPointDate(String greenPointDate) {
		this.greenPointDate = greenPointDate;
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
	public String getGreenPointContent() {
		return greenPointContent;
	}
	public void setGreenPointContent(String greenPointContent) {
		this.greenPointContent = greenPointContent;
	}
	
	
}
