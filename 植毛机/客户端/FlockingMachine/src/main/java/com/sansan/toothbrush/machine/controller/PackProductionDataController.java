package com.sansan.toothbrush.machine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sansan.toothbrush.machine.base.ApiResponse;
import com.sansan.toothbrush.machine.entity.req.PackBaseProductionDataInfoReq;
import com.sansan.toothbrush.machine.service.PackProductionDataService;

@Controller
@RequestMapping("pack")
public class PackProductionDataController {
	
	private PackProductionDataService packservices;
	
	/*@GetMapping("SaveBaseInfo")
	@ResponseBody
	private ApiResponse saveProductionBaseInfo(PackBaseProductionDataInfoReq req) {
		return packservices.saveBaseInfo(req);
	}*/
}
