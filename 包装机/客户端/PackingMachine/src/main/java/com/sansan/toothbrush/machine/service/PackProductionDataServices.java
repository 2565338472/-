package com.sansan.toothbrush.machine.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sansan.toothbrush.machine.base.ApiResponse;
import com.sansan.toothbrush.machine.entity.ExceedingProduction;
import com.sansan.toothbrush.machine.entity.PackProductionData;
import com.sansan.toothbrush.machine.entity.req.PackProductionDataBaseInfoReq;
import com.sansan.toothbrush.machine.entity.req.PackProductionDataHistoryReq;
import com.sansan.toothbrush.machine.entity.resp.PackProductionDataResp;
import com.sansan.toothbrush.machine.repository.ExceedingProductionRepository;
import com.sansan.toothbrush.machine.repository.PackProductionDataRepository;
import com.sansan.toothbrush.machine.thread.SaveDataThread;

@Service
public class PackProductionDataServices {
	
	@Autowired
	private PackProductionDataRepository repository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ExceedingProductionRepository EPRepository;
	//
	@Transactional(rollbackOn=Exception.class)
	public ApiResponse submitOrder(PackProductionDataBaseInfoReq req) {
		if(req.getScheduledProduction() == 0) {
			return ApiResponse.ofParamError("计划产量为空！无法提交！");
		}
		if(req.getCustomerNumber() == "") {
			return ApiResponse.ofParamError("客户单号为空！无法提交！");
		}
		if(req.getWorkOrderNumber() == "") {
			return ApiResponse.ofParamError("工单单号为空！无法提交！");
		}
		if(req.getVersion() == "") {
			return ApiResponse.ofParamError("产品型号为空！无法提交！");
		}
		int i = 0;
		try {
			i = jdbcTemplate.update("update ss_production_data set production_state = 1 where id ="+req.getId());
		} catch (Exception e) {
			e.getLocalizedMessage();
			return ApiResponse.ofError("提交错误！请重试！");
		}
		
		if(i != 1) {
			return ApiResponse.ofError("提交失败！请重试！");
		}
		return ApiResponse.ofSuccess("提交成功！");
	}
	@Transactional(rollbackOn=Exception.class)
	public ApiResponse saveBaseInfo(PackProductionDataBaseInfoReq req) {
		int ProductionState = repository.findProductionStateById(req.getId());
		//如果生产状态为未开始并且预计产量小于实际产量，则返回订单未完成不可修改
		//0 未完成 1 已完成 2 未启动
		if(String.valueOf(req.getScheduledProduction()) == "" ||String.valueOf(req.getScheduledProduction()) == null || ProductionState == 0 && req.getScheduledProduction() > req.getOk()) {
			return ApiResponse.ofMessage(200, "该订单未完成！不能修改！");
		}
		if(req.getScheduledProduction() == 0) {
			return ApiResponse.ofParamError("计划产量不能为空！");
		}
		if(req.getCustomerNumber() == "") {
			return ApiResponse.ofParamError("客户单号不能为空！");
		}
		if(req.getWorkOrderNumber() == "") {
			return ApiResponse.ofParamError("工单单号不能为空！");
		}
		if(req.getVersion() == "") {
			return ApiResponse.ofParamError("产品型号不能为空！");
		}
		
		try {
			repository.saveBaseInfo(req.getCustomerNumber(), req.getWorkOrderNumber(), req.getVersion(), req.getScheduledProduction(), req.getId());
		} catch (Exception e) {
			e.getLocalizedMessage();
			return ApiResponse.ofError("提交错误！请重试！");
		}
		return ApiResponse.ofSuccess("保存成功！");
	}
	
	@Transactional(rollbackOn=Exception.class)
	public void save() {
		//查询所有端口号
		String sql = "select id,ip,port from ss_connect_info";
		List<Map<String, Object>> address = jdbcTemplate.queryForList(sql);
		//创建生产数据对象
		PackProductionData PackProdata = null;
		PackProductionData SelectProdata = null;//创建生产数据查询对象
		//遍历地址list对象
		SaveDataThread saveTeread = null;
		for(Map<String, Object> as : address){
			synchronized (this) {
				try {
					/*json = getPorductionData(as.get("ip").toString(),Integer.parseInt(as.get("port").toString()));*/
					//线程连接过多会导致连接池溢出，从而导致无法插入数据，可以重启电脑 解决
					//创建线程从socket获取数据并保存
					saveTeread = new SaveDataThread(as, repository,jdbcTemplate,EPRepository);
					saveTeread.start();
				} catch (NumberFormatException | IOException e) {
					
					Integer connect_Info_id =  as.get("id") == null || as.get("id") == "" ? 0 : Integer.valueOf(as.get("id").toString());
					jdbcTemplate.update("update ss_connect_info set thread_state = 0 where id = "+connect_Info_id);
					System.out.println("连接已断开，正在尝试再次启动...");
					//查询状态为生产中的数据，一个数据只有一个
					SelectProdata = repository.findFlockingProduction_data_id(connect_Info_id);
					//e.printStackTrace();
					//一旦连接异常，不对数据进行操作
					if(SelectProdata != null) {
						repository.updateState("未连接", SelectProdata.getId());
						System.out.println("连接失败！机器未连接或端口号，ip地址错误！请检查局域网是否畅通或端口号，ip地址是否正确！");
				        continue;
					}
					
					PackProdata = new PackProductionData();
					//如果没有该数据仍对数据进行添加，但所有数据为0
					PackProdata.setMachineName(connect_Info_id+"号机器");//机器名称
					PackProdata.setConnectInfoId(connect_Info_id);//绑定连接信息id
					PackProdata.setOk(0);//合格
			        PackProdata.setNg(0);//不合格
			        PackProdata.setTotal(0);//总数
			        PackProdata.setUnfinishedAmount(0);
			        PackProdata.setNgNumberCamera(0);
			        // 0 暂停 1 运行 2 未连接
			        PackProdata.setMachineState("未连接");
			        PackProdata.setCreateTime(new Timestamp(System.currentTimeMillis()));
			        repository.save(PackProdata);
					System.out.println("连接失败！机器未连接或端口号，ip地址错误！请检查局域网是否畅通或端口号，ip地址是否正确！");
					continue;
				}
			}
		}
	}
	
	//查询所有未完成的信息并提供实时请求刷新的功能
	public ApiResponse findAll() {
		String sql = "SELECT id,machine_name,work_order_number,customer_number,version,scheduled_production,ok,ng,unfinished_amount,total,machine_state,start_time,end_time FROM ss_production_data Pdata where production_state in(0,2)";
		
		List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql);
		List<PackProductionDataResp> resps = new ArrayList<>();
		PackProductionDataResp resp = null;
		String start_time = null;
		String end_time = null;
		for(Map<String,Object> map: list) {
			start_time = map.get("start_time") == null || map.get("start_time") == "" ? "未连接" : map.get("start_time").toString();
			end_time = map.get("end_time") == null || map.get("end_time") == "" ? "未连接" : map.get("end_time").toString();
			if(map.get("machine_state").toString().equals("未连接")) {
				start_time = "未连接";
				end_time = "未连接";
			}
			resp = new PackProductionDataResp();
			resp.setId(map.get("id") == null || map.get("id") == "" ? "0" : map.get("id").toString());
			resp.setMachineName(map.get("machine_name").toString());
			resp.setMachineState(map.get("machine_state").toString());
			resp.setScheduledProduction(map.get("scheduled_production")== null || map.get("scheduled_production") == "" ? "" : map.get("scheduled_production").toString());
			resp.setWorkOrderNumber(map.get("work_order_number")== null || map.get("work_order_number") == "" ? "" : map.get("work_order_number").toString());
			resp.setCustomerNumber(map.get("customer_number")== null || map.get("customer_number") == "" ? "" : map.get("customer_number").toString());
			resp.setVersion(map.get("version") == null || map.get("version") == "" ? "" : map.get("version").toString());
			resp.setOk(map.get("ok").toString());
			resp.setNg(map.get("ng").toString());
			resp.setTotal(map.get("total").toString());
			resp.setUnfinishedAmount(map.get("unfinished_amount")== null || map.get("unfinished_amount") == "" ? "0" : map.get("unfinished_amount").toString());
			resp.setNgNumberCamera(map.get("ng_number_camera")== null || map.get("ng_number_camera") == "" ? "0" : map.get("ng_number_camera").toString());
			resp.setStartTime(start_time);
			resp.setEndTime(end_time);
			resps.add(resp);
		}
		return ApiResponse.ofSuccess(resps);
	}
	
	/**
	 * 包装机历史记录功能
	 * 
	 * @param time
	 * @param req.getPage()
	 * @param limit
	 * @return 
	 */
	public ApiResponse findByPageHistory(PackProductionDataHistoryReq req) {
		String sql = null;
		if(req.getPage() == 0) {
			req.setPage(1);//设置页数默认值为1
		}
		if(req.getLimit() == 0) {
			req.setLimit(5);//设置一页默认显示5
		}
		//获取当天已完成的订单的信息
		if(!StringUtils.isNotBlank(req.getTime())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			req.setTime(sdf.format(new Date())); //设置时间默认值为当天
		}
		/*if(StringUtils.isNotBlank(req.getTime())) {
			sql += " and create_time like '%"+req.getTime()+"%'";
		}*/
		sql = "select * from ss_production_data where production_state = 1 AND create_time LIKE '%"+req.getTime()+"%' limit "+(req.getPage()-1)*req.getLimit()+","+req.getLimit();
		System.out.println(sql);
		List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql);
		List<PackProductionDataResp> resps = new ArrayList<>();
		PackProductionDataResp resp = null;
		
		for(Map<String,Object> map: list) {
			resp = new PackProductionDataResp();
			resp.setMachineName(map.get("machine_name").toString());
			resp.setMachineState(map.get("machine_state").toString());
			resp.setScheduledProduction(map.get("scheduled_production")== null || map.get("scheduled_production") == "" ? "0" : map.get("scheduled_production").toString());
			resp.setWorkOrderNumber(map.get("work_order_number")== null || map.get("work_order_number") == "" ? "0" : map.get("work_order_number").toString());
			resp.setCustomerNumber(map.get("customer_number")== null || map.get("customer_number") == "" ? "0" : map.get("customer_number").toString());
			resp.setVersion(map.get("version")== null || map.get("version") == "" ? "0" : map.get("version").toString());
			System.out.println(sql);
			resp.setOk(map.get("ok").toString());
			resp.setNg(map.get("ng").toString());
			resp.setTotal(map.get("total").toString());
			resp.setUnfinishedAmount(map.get("unfinished_amount")== null || map.get("unfinished_amount") == "" ? "0" : map.get("unfinished_amount").toString());
			resp.setNgNumberCamera(map.get("ng_number_camera")== null || map.get("ng_number_camera") == "" ? "0" : map.get("ng_number_camera").toString());
			resp.setStartTime(map.get("start_time") == null || map.get("start_time") == "" ? "" : map.get("start_time").toString());
			resp.setEndTime(map.get("end_time") == null || map.get("end_time") == "" ? "" : map.get("end_time").toString());
			resps.add(resp);
		}
		return ApiResponse.ofSuccess(resps);
	}
	
	/**
	 * @param ip
	 * @param port
	 * @param machine_name
	 * @param version_id
	 * @return
	 */
	public PackProductionData getPackPorductionData(String ip,int port) {
		Socket socket = null;
		InputStream in = null;
		PackProductionData PackProData = new PackProductionData();
		try {
			socket = new Socket(ip, port);
			in = socket.getInputStream();
		} catch (UnknownHostException e) {
			System.out.println("无法确定主机的ip地址！异常原因："+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("端口号错误或IO操作异常已中断！异常原因："+e.getMessage());
			e.printStackTrace();
		}
		
		byte[] data=new byte[1024];
		int len = 0;
		try {
			len = in.read(data);
		} catch (IOException e) {
			System.out.println("ProductionDataServices的save方法中的in.read(data)调用异常，异常原因："+e.getMessage());
			e.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		//获取包装数据
		String PackData = new String(data,0,len);
		//对数据进行切割，并获取当前数据的数组
		
		//首先去掉,号
		char delStr = ',';
		//在对*号进行切割
		String[] PackDatas = deleteString2(PackData, delStr).split("\\*");
		int completedQuantity = 0;
		for(int i = 0; i < 2; i++) {
			//获取检测次数，也就是已完成量，假如检测是第一个
			if(i == 0) {
				completedQuantity = Integer.parseInt(PackDatas[i]);
				PackProData.setTotal(completedQuantity);
			}
			
			//获取相机ng次数
			if(i == 1) {
				PackProData.setNgNumberCamera(Integer.parseInt(PackDatas[i]));
			}
		}
		
		return PackProData;
	}
	
	//去除指定的字符
	public static String deleteString2(String str, char delChar) {
        StringBuffer stringBuffer = new StringBuffer("");
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != delChar) {
                stringBuffer.append(str.charAt(i));
            }
        }
        return stringBuffer.toString();
    }
	
}
