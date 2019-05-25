package com.sansan.toothbrush.machine.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sansan.toothbrush.machine.base.ApiResponse;
import com.sansan.toothbrush.machine.entity.Address;
import com.sansan.toothbrush.machine.entity.ConnectInfo;
import com.sansan.toothbrush.machine.service.ConnectInfoService;


@Controller
public class ConnectInfoController {
	
	@Autowired
	private ConnectInfoService service;
	
	@GetMapping("/config/list")
	@ResponseBody
	public ApiResponse getDevConfigData(HttpServletRequest request) {
		List<Address> list = service.findAll();
		if(request.getSession().getAttribute("user") == null) {
			return ApiResponse.ofMessage(401, "未授权，请先登录……", list);
		}
		return ApiResponse.ofSuccess(list);
	}
	
	@PostMapping("/config/modify")
	@ResponseBody
	public ApiResponse modifyConfigData(@RequestBody  ConnectInfo config, HttpServletRequest request) {
		
		if(request.getSession().getAttribute("user") == null) {
			return ApiResponse.ofError("用户未登陆，不允许修改配置！");
		}
		return service.editConnectInfo(config);
	}
}
