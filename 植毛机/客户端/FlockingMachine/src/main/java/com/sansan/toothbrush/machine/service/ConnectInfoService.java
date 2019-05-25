package com.sansan.toothbrush.machine.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sansan.toothbrush.machine.base.ApiResponse;
import com.sansan.toothbrush.machine.entity.Address;
import com.sansan.toothbrush.machine.entity.ConnectInfo;
import com.sansan.toothbrush.machine.enums.StatusCode;
import com.sansan.toothbrush.machine.repository.ConnectInfoRepository;

@Service
public class ConnectInfoService {
	
	@Autowired
	private ConnectInfoRepository repository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Address> findAll(){
		String sql = "select ci.id id,pd.name name,ci.ip ip,ci.port port from ss_connect_info ci join ss_production_data pd  on ci.id = pd.connect_info_id group by ci.id";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		Address address = null;
		List<Address> AddressList = new ArrayList<Address>();
		for(Map<String,Object> map : list) {
			address = new Address();
			address.setId(map.get("id").toString());
			address.setIp(map.get("ip").toString());
			address.setName(map.get("name").toString());
			address.setPort(map.get("port").toString());
			AddressList.add(address);
		}
		return AddressList;
	}
	

	public ApiResponse editConnectInfo(ConnectInfo e) {
		System.out.println(e.getId());
		if(null == e.getPort() || "".equals(e.getPort())) {
			return ApiResponse.ofParamError("端口号不能为空");
		}
		if(null == e.getIp() || "".equals(e.getIp())) {
			return ApiResponse.ofParamError("ip地址不能为空");
		}
		return ApiResponse.ofSuccess(repository.save(e));
	}

	public ApiResponse delConnectInfo(Integer id) {
		ConnectInfo data = null;
		Optional<ConnectInfo> optional = repository.findById(id);
		if(optional.isPresent()) {
			data = optional.get();
		}
		if (data == null){
			return ApiResponse.ofMessage(StatusCode.PARAM_ERROR, "该产线不存在");
		}
		repository.deleteById(id);
		return ApiResponse.ofSuccess("删除成功");
	}
}
