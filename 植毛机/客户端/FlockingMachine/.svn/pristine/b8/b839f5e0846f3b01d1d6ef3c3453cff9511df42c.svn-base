package com.sansan.toothbrush.machine.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sansan.toothbrush.machine.base.ApiResponse;
import com.sansan.toothbrush.machine.entity.User;
import com.sansan.toothbrush.machine.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService service;
	
	@RequestMapping({"/","/index"})
	public String main() {
		return "index";
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public ApiResponse login(User user, HttpServletRequest request) {
		if (user.getUserName().contains("|")){
			return ApiResponse.ofParamError("用户名不能带有非法字符'|'");
		}
		User u = service.findByName(user.getUserName());
		//内部使用，简单判断就好
/*		System.out.println(u.getPassword());
		System.out.println(user.getPassword());*/
		if(u.getPassword().equals(user.getPassword())) {
			request.getSession().setAttribute("user", u);
			return ApiResponse.ofSuccess("登陆成功！");
		}
		else {
			return ApiResponse.ofError("密码错误，登陆失败！");
		}
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public ApiResponse logout(HttpServletRequest request) {
		//if(request.getSession().getAttribute("user")!=null) {}
		request.getSession().removeAttribute("user");
		request.getSession().invalidate();
		return ApiResponse.ofSuccess("已退出登录");
	}
	
	@RequestMapping("/modifypsw")
	@ResponseBody
	public ApiResponse modifyPSW(String psw, String newpsw, HttpServletRequest request) {
		if(request.getSession().getAttribute("user") == null) {
			return ApiResponse.ofError("未登陆，不允许修改密码");
		}
		User user = (User) request.getSession().getAttribute("user");
		if(!user.getPassword().equals(psw)) {
			return ApiResponse.ofError("原始密码错误，修改密码失败！");
		}
		user.setPassword(newpsw);
		return service.editPSW(user);
	}
}
