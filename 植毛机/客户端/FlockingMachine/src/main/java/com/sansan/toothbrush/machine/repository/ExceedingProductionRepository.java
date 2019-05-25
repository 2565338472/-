package com.sansan.toothbrush.machine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sansan.toothbrush.machine.entity.ExceedingProduction;

public interface ExceedingProductionRepository extends JpaRepository<ExceedingProduction, Integer> {
	
	@Query(value = "select * from ss_exceeding_production where connect_info_id=?1",nativeQuery=true)
	public ExceedingProduction findByConnectInfoId(int connect_info_id);
	
	@Query(value = "select * from ss_exceeding_production where connect_info_id=?1",nativeQuery=true)
	public ExceedingProduction findExceedingProductionByConnectInfoId(int connect_info_id);
}
