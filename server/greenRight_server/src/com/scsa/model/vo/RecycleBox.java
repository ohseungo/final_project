package com.scsa.model.vo;

public class RecycleBox {
	private String recycleBoxId;
	private	float recycleBoxLat;
	private float recycleBoxLong;
	private int recycleBoxCount;
	
	public RecycleBox() {
		super();
	}
	
	public RecycleBox(String recycleBoxId, float recycleBoxLat, float recycleBoxLong) {
		super();
		this.recycleBoxId = recycleBoxId;
		this.recycleBoxLat = recycleBoxLat;
		this.recycleBoxLong = recycleBoxLong;
	}

	public int getRecycleBoxCount() {
		return recycleBoxCount;
	}

	public void setRecycleBoxCount(int recycleBoxCount) {
		this.recycleBoxCount = recycleBoxCount;
	}

	public String getRecycleBoxId() {
		return recycleBoxId;
	}

	public void setRecycleBoxId(String recycleBoxId) {
		this.recycleBoxId = recycleBoxId;
	}

	public float getRecycleBoxLat() {
		return recycleBoxLat;
	}
	public void setRecycleBoxLat(float recycleBoxLat) {
		this.recycleBoxLat = recycleBoxLat;
	}
	public float getRecycleBoxLong() {
		return recycleBoxLong;
	}
	public void setRecycleBoxLong(float recycleBoxLong) {
		this.recycleBoxLong = recycleBoxLong;
	}

	@Override
	public String toString() {
		return "RecycleBox [recycleBoxId=" + recycleBoxId + ", recycleBoxLat=" + recycleBoxLat + ", recycleBoxLong="
				+ recycleBoxLong + "]";
	}
	
	
	
	
}
