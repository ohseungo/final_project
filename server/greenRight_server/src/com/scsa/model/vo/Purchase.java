package com.scsa.model.vo;

public class Purchase{
	
	private String purchaseId;
	private int purchaseCount;
	private String purchaseStatus;
	private String purchaseDate;
	private String productId;
	private String userId;
	private int purchaseType;
	private String userAddress;
	private int productValue;
	private String userName;
	private int purchasePoint;
	
	
	public Purchase() {
		super();
	}
	public Purchase(String purchaseId, int purchaseCount, String purchaseStatus, String purchaseDate, String productId,
			String userId, int purchaseType, String userAddress, int productValue, String userName, int purchasePoint) {
		super();
		this.purchaseId = purchaseId;
		this.purchaseCount = purchaseCount;
		this.purchaseStatus = purchaseStatus;
		this.purchaseDate = purchaseDate;
		this.productId = productId;
		this.userId = userId;
		this.purchaseType = purchaseType;
		this.userAddress = userAddress;
		this.productValue = productValue;
		this.userName = userName;
		this.purchasePoint = purchasePoint;
	}
	public int getPurchaseType() {
		return purchaseType;
	}
	public void setPurchaseType(int purchaseType) {
		this.purchaseType = purchaseType;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public int getProductValue() {
		return productValue;
	}
	public void setProductValue(int productValue) {
		this.productValue = productValue;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getPurchasePoint() {
		return purchasePoint;
	}
	public void setPurchasePoint(int purchasePoint) {
		this.purchasePoint = purchasePoint;
	}
	public String getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}
	public int getPurchaseCount() {
		return purchaseCount;
	}
	public void setPurchaseCount(int purchaseCount) {
		this.purchaseCount = purchaseCount;
	}
	public String getPurchaseStatus() {
		return purchaseStatus;
	}
	public void setPurchaseStatus(String purchaseStatus) {
		this.purchaseStatus = purchaseStatus;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
