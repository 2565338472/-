package com.sansan.toothbrush.machine.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Table(name="ss_connect_Info")
@DynamicUpdate
@DynamicInsert
public class ConnectInfo {	
	@Id //标识主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) //标识自增长主键
	private Integer id; //内码
	@NotBlank(message = "ip不能为空！")
	private String ip;
	@NotBlank(message = "端口号不能为空！")
	private String  port;
	private boolean threadState;
	
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}
	public boolean isThreadState() {
		return threadState;
	}
	public void setThreadState(boolean threadState) {
		this.threadState = threadState;
	}
}
