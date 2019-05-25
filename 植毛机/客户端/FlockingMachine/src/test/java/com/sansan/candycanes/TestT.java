/*package com.sansan.candycanes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.sansan.toothbrush.machine.service.ProductionDataServices;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Component
public class TestT {
	@Autowired
	private ProductionDataServices ProDataService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	@Transactional
	public void test() {
		String str = "1234";
		jdbcTemplate.update("update ss_production_data set version=? where id=1","java");
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
	    String sql = "update ss_production_data set version='"+str+"' where id=1";
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
		ProDataService.save();
	}
}
*/