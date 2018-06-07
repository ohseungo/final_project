package com.scsa.model.vo;


public class Product {
	private String productId;
	private String productImage;
	private int productValue;
	private String productName;
	private String productContent;
	private String compId;
	
	private String compName;
	
	

	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public int getProductValue() {
		return productValue;
	}

	public void setProductValue(int productValue) {
		this.productValue = productValue;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductContent() {
		return productContent;
	}

	public void setProductContent(String productContent) {
		this.productContent = productContent;
	}

	public String getCompId() {
		return compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}

	
	
	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productImage=" + productImage + ", productValue=" + productValue
				+ ", productName=" + productName + ", productContent=" + productContent + ", compId=" + compId + "]";
	}
	
	
}
