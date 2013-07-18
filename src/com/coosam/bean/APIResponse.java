package com.coosam.bean;

import java.util.List;

public class APIResponse {

	private int count;
	
	private int start;
	
	private int total;
	
	private List<BookCollection> collections;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<BookCollection> getCollections() {
		return collections;
	}

	public void setCollections(List<BookCollection> collections) {
		this.collections = collections;
	}
	
	
}
