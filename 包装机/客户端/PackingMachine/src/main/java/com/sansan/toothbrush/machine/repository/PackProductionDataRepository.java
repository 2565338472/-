package com.sansan.toothbrush.machine.repository;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sansan.toothbrush.machine.entity.PackProductionData;

public interface PackProductionDataRepository extends JpaRepository<PackProductionData, Integer> {
	
	@Query(value = "select production_state from ss_production_data where id=?1",nativeQuery=true)
	public int findProductionStateById(String id);
	
	@Modifying
	@Transactional(rollbackOn=Exception.class)
	@Query(value = "update ss_production_data set customer_number = ?1,work_order_number = ?2,version = ?3,scheduled_production = ?4 where id = ?5",nativeQuery=true)
	public int saveBaseInfo(String customer_number,String work_order_number,String version,int scheduled_production,String id);
	
	
	@Query(value = "select * from ss_production_data where connect_info_id = ?1 and production_state in(0,2)",nativeQuery=true)
	public PackProductionData findFlockingProduction_data_id(int connect_Info_id);
	
	
	@Modifying
	@Transactional(rollbackOn=Exception.class)
	@Query(value = "update ss_production_data pd set pd.start_time = ?1 where pd.id = ?2",nativeQuery=true)
	public void updateStart_time(Timestamp start_time,int id);
	
	@Modifying
	@Transactional(rollbackOn=Exception.class)
	@Query(value = "update ss_production_data pd set pd.machine_state = ?1 where pd.id = ?2",nativeQuery=true)
	public int updateState(String state,int id);
	
	
	@Query(value = "select * from ss_production_data where production_state = 1 and connect_Info_id = ?1 and id in(select max(id) from ss_production_data)",nativeQuery=true)
	public PackProductionData residueProduction(int connect_Info_id);
	
	@Query(value = "select total_completed from ss_exceeding_production where connect_info_id = ?1",nativeQuery=true)
	public Integer findTotalCompleted(int connect_Info_id);
	
}
