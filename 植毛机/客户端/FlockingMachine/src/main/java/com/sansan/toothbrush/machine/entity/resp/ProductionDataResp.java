package com.sansan.toothbrush.machine.entity.resp;

public class ProductionDataResp {
	//SELECT Pdata.name,Pdata.ok,Pdata.ng,Pdata.total,Pdata.run_time FROM ss_production_data Pdata;
	private int id;
	private String workOrderNumber;//工单单号
	private String version;//型号
	private String customerNumber;//客户单号
	private int scheduledProduction = 0;//计划产量
	private String name;
	private String ok;
	private String state;//机器状态状态
	private String oweHole;//欠毛数量
	private String longHair;//长毛数量
	private String looseWool;//散毛数量
	private String longShortHair;//长短毛数量
	private String implantationError;//植措毛
	private String differenceHair;//差毛数量
	private String dirty;//脏污
	private String ng;
	private String total;
	private int outstandingAmount;
	private String startTime;//当天机器的开始时间
	private String endTime;//当天机器的最后运行时间
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getOweHole() {
		return oweHole;
	}
	public void setOweHole(String oweHole) {
		this.oweHole = oweHole;
	}
	public String getLongHair() {
		return longHair;
	}
	public void setLongHair(String longHair) {
		this.longHair = longHair;
	}
	public String getLooseWool() {
		return looseWool;
	}
	public void setLooseWool(String looseWool) {
		this.looseWool = looseWool;
	}
	public String getLongShortHair() {
		return longShortHair;
	}
	public void setLongShortHair(String longShortHair) {
		this.longShortHair = longShortHair;
	}
	public String getImplantationError() {
		return implantationError;
	}
	public void setImplantationError(String implantationError) {
		this.implantationError = implantationError;
	}
	public String getDifferenceHair() {
		return differenceHair;
	}
	public void setDifferenceHair(String differenceHair) {
		this.differenceHair = differenceHair;
	}
	public String getDirty() {
		return dirty;
	}
	public void setDirty(String dirty) {
		this.dirty = dirty;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public int getOutstandingAmount() {
		return outstandingAmount;
	}
	public void setOutstandingAmount(int outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}
	
	
}
