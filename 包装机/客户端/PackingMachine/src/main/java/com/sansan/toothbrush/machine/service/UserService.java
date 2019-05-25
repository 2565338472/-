package com.sansan.toothbrush.machine.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sansan.toothbrush.machine.base.ApiResponse;
import com.sansan.toothbrush.machine.entity.User;
import com.sansan.toothbrush.machine.enums.StatusCode;
import com.sansan.toothbrush.machine.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;
	
	public ApiResponse findById(Integer id) {
		return ApiResponse.ofSuccess(repository.findById(id));
	}
	
	public User findByName(String name) {
		return repository.findByUsername(name);
	}
	
	public ApiResponse editPSW(User e) {
		User data = null;
		Optional<User> optional = repository.findById(e.getId());
		if(optional.isPresent()) {
			data = optional.get();
		}
		if (data == null){
			return ApiResponse.ofMessage(StatusCode.PARAM_ERROR, "该用户不存在");
		}
		data.setPassword(e.getPassword());
		repository.save(data);
		return ApiResponse.ofSuccess("编缉密码成功");
	}
}
