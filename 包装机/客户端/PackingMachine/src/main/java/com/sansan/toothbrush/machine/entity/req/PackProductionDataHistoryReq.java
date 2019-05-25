package com.sansan.toothbrush.machine.entity.req;

public class PackProductionDataHistoryReq {
	private String time;
	private int page;
	private int limit;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
}
