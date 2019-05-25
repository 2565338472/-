package com.sansan.toothbrush.machine.service;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sansan.toothbrush.machine.base.ApiResponse;
import com.sansan.toothbrush.machine.entity.req.PackBaseProductionDataInfoReq;
import com.sansan.toothbrush.machine.repository.ProductionDataRepository;

@Service
public class PackProductionDataService {
	@Autowired
	private ProductionDataRepository repository;
	
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/*@Transactional(rollbackOn=Exception.class)
	public ApiResponse saveBaseInfo(PackBaseProductionDataInfoReq req) {
		if(req.getConnectInfoId() == 0) {
			return ApiResponse.ofSuccess("获取连接信息id失败！请重试！");
		}
		if(req.getScheduledProduction() == 0) {
			return ApiResponse.ofParamError("计划生产量不能为0！");
		}
		if(StringUtils.isNotBlank(req.getVersion())) {
			return ApiResponse.ofParamError("错误！型号不能为空！");
		}
		try {
			repository.saveBeseInfo(req.getConnectInfoId(), req.getVersion(), req.getScheduledProduction());
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResponse.ofError("数据保存异常！请重试！");
		}
		return ApiResponse.ofSuccess(null);
	}*/
}
