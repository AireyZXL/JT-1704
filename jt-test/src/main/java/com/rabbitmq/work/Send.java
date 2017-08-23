/**
 * 
 */
package com.rabbitmq.work;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.simple.ConnectionUtil;

/**
 * @author Airey
 * @date   2017年8月14日
 */
public class Send {
	private static final String QUEUE_NAME = "test_queue_work";

	public static void main(String[] args) throws Exception {
		// 获取到连接以及mq通道
		Connection connection = ConnectionUtil.getConnection();
		// 从连接中创建通道
		Channel channel = connection.createChannel();
		// 声明（创建）队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		for (int i = 0; i < 1000; i++) {
			// 消息内容
			String message = "" + i;
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");

			Thread.sleep(10); // 越往后，停歇的时间越长
		}
		// 关闭通道和连接
		channel.close();
		connection.close();
	}
}
