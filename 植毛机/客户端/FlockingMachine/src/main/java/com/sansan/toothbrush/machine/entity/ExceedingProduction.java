package com.sansan.toothbrush.machine.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @author JR
 *	超出产量实体类
 */
@Entity
@Table(name="ss_exceeding_production")
@DynamicInsert
@DynamicUpdate
public class ExceedingProduction {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int connectInfoId;//连接信息表id
	private int exceedingProduction;//超出的计划产量的已完成数量
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getConnectInfoId() {
		return connectInfoId;
	}
	public void setConnectInfoId(int connectInfoId) {
		this.connectInfoId = connectInfoId;
	}
	public int getExceedingProduction() {
		return exceedingProduction;
	}
	public void setExceedingProduction(int exceedingProduction) {
		this.exceedingProduction = exceedingProduction;
	}
}
