package com.sansan.toothbrush.machine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sansan.toothbrush.machine.entity.ConnectInfo;

public interface ConnectInfoRepository extends JpaRepository<ConnectInfo, Integer> {

/*	@Query(value="select ci.id,pd.name name,ci.ip ip,ci.port port from ss_connect_info ci join ss_production_data pd  on ci.id = pd.connect_info_id",nativeQuery=true)
	public List<Address> findAddressInfo();*/
}
