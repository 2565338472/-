package com.sansan.toothbrush.machine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sansan.toothbrush.machine.base.ApiResponse;
import com.sansan.toothbrush.machine.entity.req.PackProductionDataBaseInfoReq;
import com.sansan.toothbrush.machine.entity.req.PackProductionDataHistoryReq;
import com.sansan.toothbrush.machine.service.PackProductionDataServices;




/**
 * @author Administrator
 * 生产数据
 */
@Controller
@RequestMapping("PackingProductionData")
public class ProductionDataController {
	
	@Autowired
	private PackProductionDataServices packservices;
	
	@GetMapping("findAll")
	@ResponseBody
	public ApiResponse findAll() {
		return packservices.findAll();
	}
	
	@GetMapping("findByPageHistory")
	@ResponseBody
	public ApiResponse findByPageHistory(PackProductionDataHistoryReq req) {
		return packservices.findByPageHistory(req);
	}
	@PostMapping("saveBaseInfo")
	@ResponseBody
	public ApiResponse saveBaseInfo(PackProductionDataBaseInfoReq req) {
		return packservices.saveBaseInfo(req);
	}
	@PostMapping("submit_order")
	@ResponseBody
	public ApiResponse submitOrder(PackProductionDataBaseInfoReq req) {
		return packservices.submitOrder(req);
	}
	
}
