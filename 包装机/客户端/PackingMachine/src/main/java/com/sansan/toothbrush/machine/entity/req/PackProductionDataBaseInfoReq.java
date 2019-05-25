package com.sansan.toothbrush.machine.entity.req;


/**
 * @author JR
 *	包装机基本信息
 */
public class PackProductionDataBaseInfoReq {
	private String id;
	private String workOrderNumber;//工单单号
	private String version;//型号
	private String customerNumber;//客户单号
	private int scheduledProduction = 0;//计划产量
	private int ok;//已完成数
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWorkOrderNumber() {
		return workOrderNumber;
	}
	public void setWorkOrderNumber(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	public int getScheduledProduction() {
		return scheduledProduction;
	}
	public void setScheduledProduction(int scheduledProduction) {
		this.scheduledProduction = scheduledProduction;
	}
	public int getOk() {
		return ok;
	}
	public void setOk(int ok) {
		this.ok = ok;
	}
	
}
