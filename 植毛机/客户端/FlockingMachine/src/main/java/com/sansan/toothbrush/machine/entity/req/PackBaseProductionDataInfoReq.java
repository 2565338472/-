package com.sansan.toothbrush.machine.entity.req;


/**
 * 	获取基本生产信息
 * @author JR
 *
 */
public class PackBaseProductionDataInfoReq {
	private int connectInfoId;//连接信息id
	private String version;//型号
	private int scheduledProduction;//计划产量
	public int getConnectInfoId() {
		return connectInfoId;
	}
	public void setConnectInfoId(int connectInfoId) {
		this.connectInfoId = connectInfoId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getScheduledProduction() {
		return scheduledProduction;
	}
	public void setScheduledProduction(int scheduledProduction) {
		this.scheduledProduction = scheduledProduction;
	}
}
