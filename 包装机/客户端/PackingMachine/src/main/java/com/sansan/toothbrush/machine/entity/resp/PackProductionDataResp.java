package com.sansan.toothbrush.machine.entity.resp;

import java.sql.Timestamp;

public class PackProductionDataResp {
	private String id;
	private String machineName;//机器名称
	private String machineState;//机器状态
	private String scheduledProduction;//计划产量
	private String workOrderNumber;//工单单号
	private String customerNumber;//客户单号
	private String version;//产品型号
	private String ok;//合格总数
	private String ng;//不合格总数
	private String total;//总数 相当于已完成量
	private String unfinishedAmount;//未完成量
	private String ngNumberCamera;//相机ng次数
	private String productionState;
	private String startTime;//软件启动时间
	private String endTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public String getMachineState() {
		return machineState;
	}
	public void setMachineState(String machinState) {
		this.machineState = machinState;
	}
	public String getScheduledProduction() {
		return scheduledProduction;
	}
	public void setScheduledProduction(String scheduledProduction) {
		this.scheduledProduction = scheduledProduction;
	}
	public String getWorkOrderNumber() {
		return workOrderNumber;
	}
	public void setWorkOrderNumber(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUnfinishedAmount() {
		return unfinishedAmount;
	}
	public void setUnfinishedAmount(String unfinishedAmount) {
		this.unfinishedAmount = unfinishedAmount;
	}
	public String getNgNumberCamera() {
		return ngNumberCamera;
	}
	public void setNgNumberCamera(String ngNumberCamera) {
		this.ngNumberCamera = ngNumberCamera;
	}
	public String getProductionState() {
		return productionState;
	}
	public void setProductionState(String productionState) {
		this.productionState = productionState;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getOk() {
		return ok;
	}
	public void setOk(String ok) {
		this.ok = ok;
	}
	public String getNg() {
		return ng;
	}
	public void setNg(String ng) {
		this.ng = ng;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
