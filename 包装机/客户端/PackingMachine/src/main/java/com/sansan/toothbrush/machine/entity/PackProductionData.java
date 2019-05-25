package com.sansan.toothbrush.machine.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Table(name="ss_production_data")
@DynamicInsert
@DynamicUpdate
public class PackProductionData {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String machineName;//机器名称
	private String machineState;//机器状态
	private int connectInfoId;//连接信息id
	private int scheduledProduction;//计划产量
	private String workOrderNumber;//工单单号
	private String customerNumber;//客户单号
	private String version;//型号 由用户输入
	private int ok;//合格总数
	private int ng;//不合格总数
	private int total;//总数
	private int unfinishedAmount;//未完成量
	private int ngNumberCamera;//相机ng次数
	private int productionState;
	private Timestamp startTime;//软件启动时间
	
	public int getProductionState() {
		return productionState;
	}
	public void setProductionState(int productionState) {
		this.productionState = productionState;
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	private Timestamp createTime;
	/*private int single_piece_time_consuming;
	private int total_working_hours;*/
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	
	/*public int getSingle_piece_time_consuming() {
		return single_piece_time_consuming;
	}
	public void setSingle_piece_time_consuming(int single_piece_time_consuming) {
		this.single_piece_time_consuming = single_piece_time_consuming;
	}
	public int getTotal_working_hours() {
		return total_working_hours;
	}
	public void setTotal_working_hours(int total_working_hours) {
		this.total_working_hours = total_working_hours;
	}*/
	public int getOk() {
		return ok;
	}
	public void setOk(int ok) {
		this.ok = ok;
	}
	public int getNg() {
		return ng;
	}
	public void setNg(int ng) {
		this.ng = ng;
	}
	
	public int getScheduledProduction() {
		return scheduledProduction;
	}
	public void setScheduledProduction(int scheduledProduction) {
		this.scheduledProduction = scheduledProduction;
	}
	public int getUnfinishedAmount() {
		return unfinishedAmount;
	}
	public void setUnfinishedAmount(int unfinishedAmount) {
		this.unfinishedAmount = unfinishedAmount;
	}
	public int getNgNumberCamera() {
		return ngNumberCamera;
	}
	public void setNgNumberCamera(int ngNumberCamera) {
		this.ngNumberCamera = ngNumberCamera;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getConnectInfoId() {
		return connectInfoId;
	}
	public void setConnectInfoId(int connectInfoId) {
		this.connectInfoId = connectInfoId;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getWorkOrderNumber() {
		return workOrderNumber;
	}
	public void setWorkOrderNumber(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public String getMachineState() {
		return machineState;
	}
	public void setMachineState(String machineState) {
		this.machineState = machineState;
	}
	
}
