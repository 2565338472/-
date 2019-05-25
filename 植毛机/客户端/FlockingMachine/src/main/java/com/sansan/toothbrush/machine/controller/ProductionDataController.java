package com.sansan.toothbrush.machine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sansan.toothbrush.machine.base.ApiResponse;
import com.sansan.toothbrush.machine.entity.req.PackProductionDataHistoryReq;
import com.sansan.toothbrush.machine.entity.req.ProductionDataBaseInfoReq;
import com.sansan.toothbrush.machine.service.ProductionDataServices;



/**
 * @author Administrator
 * 生产数据
 */
@Controller
@RequestMapping("ProductionData")
public class ProductionDataController {
	
	@Autowired
	private ProductionDataServices service;
	
	@GetMapping("findAll")
	@ResponseBody
	public ApiResponse findAll() {
		return service.findAll();
	}
	
	@GetMapping("findByPageHistory")
	@ResponseBody
	public ApiResponse findByPageHistory(PackProductionDataHistoryReq req) {
		return service.findByPageHistory(req);
	}
	@PostMapping("saveBaseInfo")
	@ResponseBody
	public ApiResponse saveBaseInfo(ProductionDataBaseInfoReq req) {
		return service.saveBaseInfo(req);
	}
	@PostMapping("submit_order")
	@ResponseBody
	public ApiResponse submitOrder(ProductionDataBaseInfoReq req) {
		return service.submitOrder(req);
	}
	
}
