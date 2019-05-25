package com.sansan.toothbrush.machine.thread;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sansan.toothbrush.machine.entity.ExceedingProduction;
import com.sansan.toothbrush.machine.entity.ProductionData;
import com.sansan.toothbrush.machine.repository.ExceedingProductionRepository;
import com.sansan.toothbrush.machine.repository.ProductionDataRepository;

import net.sf.json.JSONObject;


/**
 * @author Administrator
 * 保存数据的线程 暂时未启用，先保留如果需要再进行调用
 */
public class SaveDataThread extends Thread{

	private JdbcTemplate jdbcTemplate = null;
	private InputStream in;
	private Socket socket = null;
	private String ip = null;
	private int port = 0;
	private ProductionDataRepository repository;
	private JSONObject json = null;
	private ProductionData SelectProdata = null;
	private ExceedingProductionRepository EPRepository;
	Map<String, Object> as = null;
	ProductionData Prodata = null;
	
	 public SaveDataThread(Map<String, Object> as,ProductionDataRepository repository,JdbcTemplate jdbcTemplate,ExceedingProductionRepository EPRepository) throws UnknownHostException, IOException {
	    	this.repository = repository;
	        this.jdbcTemplate = jdbcTemplate;
	        this.as = as;
	        this.port = as.get("port") == null || as.get("port") == "" ? 0 : Integer.valueOf(as.get("port").toString());
	        this.ip = as.get("ip") == null || as.get("ip") == "" ? "" : as.get("ip").toString();
	        this.EPRepository = EPRepository;
	        System.out.println("连接中...");
	        socket = new Socket(this.ip, this.port);
	        System.out.println("连接成功！");
	        System.out.println("正在启动中,请稍后..");
	        jdbcTemplate.update("update ss_connect_info set thread_state = 1 where id = "+as.get("id"));
	}
    @Transactional(rollbackOn=Exception.class)
    public synchronized void run() {
    	int CompletedQuantity = 0;//获取本次实际累计的已完成量
    	System.out.println("启动成功！");
    	System.out.println("线程-" + Thread.currentThread().getId());
		System.out.println("IP地址:"+ip);
		System.out.println("端口号:"+port);
		
		boolean connectStatus = true;
		while(connectStatus) {
	    	System.out.println("线程-" + Thread.currentThread().getId());
			System.out.println("IP地址:"+ip);
			System.out.println("端口号:"+port);
			 Integer connect_Info_id =  as.get("id") == null || as.get("id") == "" ? 0 : Integer.valueOf(as.get("id").toString());
			 /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			 String date = sdf.format(System.currentTimeMillis());
			 System.out.println(date);*/

			//通过连接信息id查询该IP地址当天是否已存在生产状态为未完成数据
			//这里应该改正一下，只需要查询指定机器生产状态未完成的数据，因为一台机器只能由一个订单为生产中
			//而因为前端页面进行了判断，如果未生产完成不允许修改，所以生产状态为生产中的订单一台机器只会有一个
			//所以不再查询当天时间而是获取该机器当前的生产状态。
			//SelectProdata = repository.findFlockingProduction_data_id(connect_Info_id,date);
			SelectProdata = repository.findFlockingProduction_data_id(connect_Info_id);
			//如果查询的数据不为空
			try {
				if(socket.getInputStream() == null) {
					continue;
				}
				in = socket.getInputStream();
			} catch (IOException e2) {
				connectStatus = false;
				// TODO Auto-generated catch block
				jdbcTemplate.update("update ss_connect_info set thread_state = 0 where id = "+as.get("id"));
				e2.printStackTrace();
			}
			try {
				socket.sendUrgentData(0xFF);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				jdbcTemplate.update("update ss_connect_info set thread_state = 0 where id = "+as.get("id"));
				e1.printStackTrace();
			    socket = null;
			    connectStatus = false;
			}
			byte[] data=new byte[1024];
	        int len = 0;
			try {
				len = in.read(data);
			} catch (IOException e) {
				connectStatus = false;
				socket = null;
			    jdbcTemplate.update("update ss_connect_info set thread_state = 0 where id = "+connect_Info_id);
				System.out.println("ProductionDataServices的save方法中的in.read(data)调用异常，异常原因："+e.getMessage());
				e.printStackTrace();
			}
			//防止获取数据错误
			if(len == -1) {
				continue;
			}
			//打印获取到的数据
			System.out.println(new String(data,0,len));
			try {
				json = JSONObject.fromObject(new String(data,0,len));
			} catch (Exception e) {
				connectStatus = false;
				socket = null;
			    jdbcTemplate.update("update ss_connect_info set thread_state = 0 where id = "+connect_Info_id);
				System.out.println("SaveDataThread 类 JSON数据获取异常，异常原因："+e.getMessage());
				e.printStackTrace();
			}
			
			/*json = getPorductionData(as.get("ip").toString(),Integer.parseInt(as.get("port").toString()));*/
			if(json == null) {
				System.out.println("json对象为 null,未获取到数据！");
				continue;
			}
			String sql = null;
			//查询出超出产量表中指定机器的超出产量
			ExceedingProduction ep = EPRepository.findByConnectInfoId(connect_Info_id);
			int DatabaseExceedingProduction = 0;
			//查询生产数据id 等于 production_data_id的数据
			synchronized (this) {
				//如果当天存在数据则对数据进行修改
				String state = null;
				
				if(ep == null) {
					ep = new  ExceedingProduction();
					ep.setConnectInfoId(connect_Info_id);
					ep.setExceedingProduction(0);
					EPRepository.save(ep);
				}
				DatabaseExceedingProduction = ep.getExceedingProduction();
				if(SelectProdata != null) {
					
					//如果存在但是预计产量为0或，客户单号为空，型号为空，工单单号为空，不对数据进行操作
					if(SelectProdata.getScheduledProduction() == 0 
							|| !StringUtils.isNotBlank(SelectProdata.getCustomerNumber()) 
							|| !StringUtils.isNotBlank(SelectProdata.getVersion()) 
							|| !StringUtils.isNotBlank(SelectProdata.getWorkOrderNumber())) {
						DatabaseExceedingProduction += json.getInt("OK")+json.getInt("NG");//累计超出产量
						sql = "update ss_exceeding_production set exceeding_production = "+(DatabaseExceedingProduction)+" where connect_info_id = "+connect_Info_id;
						jdbcTemplate.update(sql);//修改超出产量并进行累计
						continue;
					}
					if(SelectProdata.getProductionState() == 2) {
						jdbcTemplate.update("update ss_production_data set production_state = 0 where id="+SelectProdata.getId());
					}
					 // 0 暂停 1 运行
					if(json.getInt("State") == 0) {
			        	state = "暂停";
			        } else if(json.getInt("State") == 1) {
			        	state = "运行";
			        }
					//判断是否为空，防止未连接时存在对象但是开始时间为空
					if(SelectProdata.getStartTime() == null) {
						repository.updateStart_time(new Timestamp(System.currentTimeMillis()),SelectProdata.getId());
					}
					//如果客户要求自动改变订单完成状态可以加上这个判断
					//0 未完成 1 已完成 2 未开始
					//默认为未开始
					/*int ProductionState = 2;
					//如果预计产量大于已完成量，状态为未完成
					if(SelectProdata.getScheduledProduction() >= SelectProdata.getTotal()) {
						ProductionState = 0;
					}
					if(SelectProdata.getScheduledProduction() <= SelectProdata.getTotal()) {
						ProductionState = 1;
					}*/
					CompletedQuantity = SelectProdata.getTotal();//已完成量
					long ActualUnfinished = SelectProdata.getScheduledProduction() - CompletedQuantity;//未完成量

					if(SelectProdata.getScheduledProduction() < CompletedQuantity) {
						//剩余已完成量 = 已完成量 减去 计划产量
						sql = "update ss_exceeding_production set exceeding_production = "+(CompletedQuantity - SelectProdata.getScheduledProduction() )+" where connect_info_id = "+connect_Info_id;
						jdbcTemplate.update(sql);//修改超出产量并进行累计
					}
					//当创建一个新的订单的时候，将超出产量清零并将超出产量放入已完成量中
					int ExceededTotal = 0;
					if(SelectProdata.getTotal() == 0 ) {
						//获取超出产量
						ExceedingProduction ExceedingProduction = EPRepository.findExceedingProductionByConnectInfoId(connect_Info_id);
						if(ExceedingProduction != null)
							System.out.println(ExceedingProduction.getExceedingProduction());
							SelectProdata.setTotal(ExceedingProduction.getExceedingProduction()); 
						//将超出产量清零
						jdbcTemplate.update("update ss_exceeding_production set exceeding_production = 0 where connect_info_id = "+connect_Info_id);
					}
					//如果实际未完成量小于等于0
					if(ActualUnfinished <= 0) {
						ActualUnfinished = 0;
					}
					System.out.println(json.getInt("OK"));
					System.out.println(SelectProdata.getOk()+json.getInt("OK")+SelectProdata.getTotal());
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					sql = "update ss_production_data  set "
							+ "ok = "+(SelectProdata.getOk()+json.getInt("OK"))+","
							+ "ng = "+(SelectProdata.getNg()+json.getInt("NG"))+","
							+ "total = "+(json.getInt("OK")+json.getInt("NG")+SelectProdata.getTotal())+","
							+ "state = '"+state+"',"
							+ "owe_hole = "+(SelectProdata.getOweHole()+json.getInt("Hole"))+","
							+ "long_hair = "+(SelectProdata.getLongHair()+json.getInt("LongHair"))+","
							+ "Long_short_hair="+(SelectProdata.getLongShortHair()+json.getInt("UnevenHair"))+","
							+ "loose_wool="+(SelectProdata.getLooseWool()+json.getInt("LooseHair"))+","
							+ "implantation_error ="+(SelectProdata.getImplantationError()+json.getInt("WrongHair"))+","
							+ "difference_hair = "+(SelectProdata.getDifferenceHair()+json.getInt("CrushHair"))+","
							+ "dirty= "+(SelectProdata.getDirty()+json.getInt("Dirt"))+","
							+ "end_time = '"+df.format(new Date())+"'"
							+ "where id = "+SelectProdata.getId()+"";
					System.out.println(sql);
					int i = jdbcTemplate.update(sql);
					continue;
				}
				
				//如果计划产量,工单单号，产品型号，客户单号为空则不开始监控
				//如果为空同样创建数据，并初始化数据。
				
				Prodata = new  ProductionData();
				//否则因为不存在id修改将变成添加
				Prodata.setName(connect_Info_id+"号机器");//机器名称
				Prodata.setConnectInfoId(connect_Info_id);//绑定连接信息id
				Prodata.setOk(0);//合格
		        Prodata.setNg(0);//不合格
		        Prodata.setTotal(0);//总数
		        // 0 暂停 1 运行 2 未连接
		        if(json.getInt("State") == 0) {
		        	Prodata.setState("暂停");
		        } else if(json.getInt("State") == 1) {
		        	Prodata.setState("运行");
		        }
		        // 0 未完成 1 已完成 2 未开始 这里的未完成是指订单的完成状态
		        Prodata.setProductionState(2);
		        Prodata.setOweHole(0);//欠孔
		        Prodata.setLongHair(0);//长毛
		        Prodata.setLongShortHair(0);//长短毛
		        Prodata.setLooseWool(0);//散毛
		        Prodata.setImplantationError(0);//植错毛
		        Prodata.setDifferenceHair(0);//差毛
		        Prodata.setDirty(0);//脏毛
		        Prodata.setStartTime(new Timestamp(System.currentTimeMillis()));
		        Prodata.setCreateTime(new Timestamp(System.currentTimeMillis()));
		        repository.save(Prodata);
			}
		}
    }
    public void save(String sql) {
    	String driver = "com.mysql.cj.jdbc.Driver";
	    String url = "jdbc:mysql://127.0.0.1:3306/hair_planting_machine?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true";
	    String username = "root";
	    String password = "123456";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    int i = 0;
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        i = pstmt.executeUpdate();
	        System.out.println("resutl: " + i);
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
    }
}
