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
public class ProductionData {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private String workOrderNumber;//工单单号
	private String version;//型号
	private String customerNumber;//客户单号
	private int productionState = 2;//0 未完成 1 已完成 2 未开始 默认为2 
	private int scheduledProduction = 0;//计划产量
	private int connectInfoId = 0;//默认为0
	private String state;//机器状态状态
	private int oweHole;//欠毛数量
	private int longHair;//长毛数量
	private int looseWool;//散毛数量
	private int longShortHair;//长短毛数量
	private int implantationError;//植措毛
	private int differenceHair;//差毛数量
	private int dirty;//脏污
	private Timestamp startTime;//软件启动时间
	private Timestamp endTime;//软件关闭时间
	private int ok;//合格总数
	private int ng;//不合格总数
	private int total;//总数
	private String runTime;//运行时间
	private Timestamp createTime;
	
	
	public int getConnectInfoId() {
		return connectInfoId;
	}
	public void setConnectInfoId(int connectInfoId) {
		this.connectInfoId = connectInfoId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the ok
	 */
	public int getOk() {
		return ok;
	}
	/**
	 * @param ok the ok to set
	 */
	public void setOk(int ok) {
		this.ok = ok;
	}
	/**
	 * @return the ng
	 */
	public int getNg() {
		return ng;
	}
	/**
	 * @param ng the ng to set
	 */
	public void setNg(int ng) {
		this.ng = ng;
	}
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	/**
	 * @return the runTime
	 */
	public String getRunTime() {
		return runTime;
	}
	/**
	 * @param runTime the runTime to set
	 */
	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	public int getOweHole() {
		return oweHole;
	}
	public void setOweHole(int oweHole) {
		this.oweHole = oweHole;
	}
	public int getLongHair() {
		return longHair;
	}
	public void setLongHair(int longHair) {
		this.longHair = longHair;
	}
	public int getLooseWool() {
		return looseWool;
	}
	public void setLooseWool(int looseWool) {
		this.looseWool = looseWool;
	}
	
	public int getImplantationError() {
		return implantationError;
	}
	public void setImplantationError(int implantationError) {
		this.implantationError = implantationError;
	}
	public int getDifferenceHair() {
		return differenceHair;
	}
	public void setDifferenceHair(int differenceHair) {
		this.differenceHair = differenceHair;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public int getLongShortHair() {
		return longShortHair;
	}
	public void setLongShortHair(int longShortHair) {
		this.longShortHair = longShortHair;
	}
	
	public int getDirty() {
		return dirty;
	}
	public void setDirty(int dirty) {
		this.dirty = dirty;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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
	public int getProductionState() {
		return productionState;
	}
	public void setProductionState(int productionState) {
		this.productionState = productionState;
	}																																																																																				
	
}
