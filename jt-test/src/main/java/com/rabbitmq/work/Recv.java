/**
 * 
 */
package com.rabbitmq.work;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.simple.ConnectionUtil;

/**
 * @author Airey
 * @date   2017年8月14日
 */
public class Recv {
	private static final String QUEUE_NAME = "test_queue_work";

	public static void main(String[] args) throws Exception {
		// 获取到连接以及mq通道
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();

		// 声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		// 同一时刻服务器只会发一条消息给消费者，每一次服务器只会向客户端发送一条
		channel.basicQos(1);

		// 定义队列的消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		// 监听队列,手动返回完成
		channel.basicConsume(QUEUE_NAME, false, consumer);
		// 获取消息
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(" [x] Received '" + message + "'");
			// 休眠
			Thread.sleep(10);
			// 返回确认状态
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}
}
