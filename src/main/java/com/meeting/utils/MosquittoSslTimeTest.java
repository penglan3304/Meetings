package com.meeting.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import net.sf.json.JSONObject;

/***
 * mosquitto下mqtt的tls加密时延测试
 * @author cq
 *
 */

public class MosquittoSslTimeTest {
	public static final String HOST = "ssl://0.0.0.0:8883";
	
	public static String caFilePath = "D:/certs/ca.crt";
	public static String clientCrtFilePath = "D:/certs/client.crt";
	public static String clientKeyFilePath = "D:/certs/client.key";
	private static final String CLIENTID_PUB= "publish";
	private static final String CLIENTID_SUB= "subcribe";
	private static String userName = "admin";
	private static String passWord = "password";

	public  void clientPublish(long i,String TOPIC,String msg) throws MqttException {
		MqttClient client = new MqttClient(HOST, CLIENTID_PUB+String.valueOf(i), new MemoryPersistence());
		MqttConnectOptions options = new MqttConnectOptions();
		options.setCleanSession(false);
		options.setUserName(userName);
		options.setPassword(passWord.toCharArray());
		// 设置超时时间
		options.setConnectionTimeout(10);
		// 设置会话心跳时间
		options.setKeepAliveInterval(20);
		// TLS连接配置
		try {
			options.setSocketFactory(
					SslUtil.getSocketFactory(caFilePath, clientCrtFilePath, clientKeyFilePath, "111111"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			client.setCallback(new PushCallback());
			client.connect(options);
			MqttTopic topic = client.getTopic(TOPIC);

			MqttMessage message = new MqttMessage();
			message.setQos(2);
			message.setRetained(true);

			long l = System.currentTimeMillis();
			Date date = new Date(l);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = dateFormat.format(date);
			String content=time+":    "+msg;
			message.setPayload(content.getBytes());

			topic.publish(message);
			//client.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  void clientSubsreibe(long i,String TOPIC) {
		MqttClient client = null;
		try {
			client = new MqttClient(HOST, CLIENTID_SUB+String.valueOf(i), new MemoryPersistence());
			MqttConnectOptions options = new MqttConnectOptions();
			options.setCleanSession(true);
			options.setUserName(userName);
			options.setPassword(passWord.toCharArray());
			options.setConnectionTimeout(10);
			options.setKeepAliveInterval(20);
			options.setSocketFactory(
					SslUtil.getSocketFactory(caFilePath, clientCrtFilePath, clientKeyFilePath, "111111"));
			client.setCallback(new PushCallback());
			client.connect(options);
			// 订阅消息
			int[] qos = { 2 };
			String[] topic1 = { TOPIC };
			client.subscribe(topic1, qos);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
