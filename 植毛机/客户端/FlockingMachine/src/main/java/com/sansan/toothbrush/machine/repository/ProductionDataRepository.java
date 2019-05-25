package com.sansan.toothbrush.machine.repository;


import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sansan.toothbrush.machine.entity.PackProductionData;
import com.sansan.toothbrush.machine.entity.ProductionData;

public interface ProductionDataRepository extends JpaRepository<ProductionData, Integer>{
	
	@Query(value = "select production_state from ss_production_data where id=?1",nativeQuery=true)
	public int findProductionStateById(String id);
	
	@Modifying
	@Transactional(rollbackOn=Exception.class)
	@Query(value = "update ss_production_data set customer_number = ?1,work_order_number = ?2,version = ?3,scheduled_production = ?4 where id = ?5",nativeQuery=true)
	public int saveBaseInfo(String customer_number,String work_order_number,String version,int scheduled_production,String id);
	
	/**
	 *    	植毛机
	 * @param connect_Info_id
	 * @param time
	 * @return
	 */
	@Query(value = "select * from ss_production_data where connect_info_id = ?1  and production_state in(0,2)",nativeQuery=true)
	public ProductionData findFlockingProduction_data_id(int connect_Info_id);
	
	/**
	 * 	植毛机定时器修改方法，因为save方法总是覆盖start_time字段所以创建这个方法
	 * 
	 * @param name
	 * @param connectInfoId
	 * @param ok
	 * @param ng
	 * @param total
	 * @param state
	 * @param oweHole
	 * @param longHair
	 * @param longShortHair
	 * @param looseWool
	 * @param implantationError
	 * @param differenceHair
	 * @param dirty
	 * @param endTime
	 * @param Prodata_id
	 * @return
	 */
	@Modifying
	@Transactional
	@Query(value = "update ss_production_data pd set pd.`name`=?1,pd.connect_Info_id = ?2,pd.ok = ?3,pd.ng = ?4,pd.total = ?5,pd.state = ?6,pd.owe_hole = ?7,pd.long_hair = ?8,pd.Long_short_hair=?9,pd.loose_wool=?10,pd.implantation_error = ?11,pd.difference_hair = ?12,pd.dirty=?13,pd.end_time = ?14 where pd.id = ?15",nativeQuery=true)
	public int update(String name,int connectInfoId,int ok,int ng,int total,String state,int oweHole,int longHair,int longShortHair,int looseWool,int implantationError,int differenceHair,int dirty,Timestamp endTime, int Prodata_id);
	
	
	/**
	 * 	 植毛机
	 * @param state
	 * @param id
	 * @return
	 */
	@Modifying
	@Transactional(rollbackOn=Exception.class)
	@Query(value = "update ss_production_data pd set pd.state = ?1 where pd.id = ?2",nativeQuery=true)
	public int updateState(String state,int id);
	
	/**
	 * 	 植毛机
	 * @param state
	 * @param id
	 * @return
	 */
	@Modifying
	@Transactional(rollbackOn=Exception.class)
	@Query(value = "update ss_production_data pd set pd.start_time = ?1 where pd.id = ?2",nativeQuery=true)
	public void updateStart_time(Timestamp start_time,int id);
	
	

	@Query(value = "select * from ss_production_data where production_state = 1 and connect_Info_id = ?1 and id in(select max(id) from ss_production_data)",nativeQuery=true)
	public ProductionData residueProduction(int connect_Info_id);
}
