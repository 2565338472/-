package com.sansan;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.sansan.toothbrush.machine.service.ProductionDataServices;

@Component
public class SocketInit implements ApplicationRunner{
	private static final Logger LOGGER = LogManager.getLogger(SocketInit.class);
	@Autowired
	private ProductionDataServices ProDataService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		//初始化线程状态为正常
		jdbcTemplate.update("UPDATE ss_connect_info SET thread_state = 1");
		ProDataService.save();
	}

}
