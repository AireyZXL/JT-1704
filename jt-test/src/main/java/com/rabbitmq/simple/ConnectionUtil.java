/**
 * 
 */
package com.rabbitmq.simple;

import java.io.IOException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author Airey
 * @date   2017年8月14日
 */
public class ConnectionUtil {
	public static Connection getConnection() throws IOException {
		// 定义连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		// 设置服务器地址
		factory.setHost("192.168.247.70");
		// 端口，HTTP管理端口默认15672，访问端口默认5672
		factory.setPort(5672);
		// 设置账号信息，用户名、密码、vhost
		factory.setVirtualHost("/jt");
		factory.setUsername("sysdubge");
		factory.setPassword("123456");
		// 通过工程获取连接
		Connection conn = factory.newConnection();
		return conn;
	}
}
