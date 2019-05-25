package com.sansan.toothbrush.machine.base;

import java.util.List;

/**
 * 分页用的一个实体类
 * @author nuc
 * @param <T>
 *
 */
public class ApiData {
	/**
	 * 存放的数据
	 */
	private List<?> item;
	/**
	 * 总数
	 */
	private Long total;
	
	public List<?> getItem() {
		return item;
	}
	public void setItem(List<?> item) {
		this.item = item;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public ApiData(List<?> item, Long total) {
		super();
		this.item = item;
		this.total = total;
	}
}
