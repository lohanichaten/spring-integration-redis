package learning.integration.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.redis.outbound.RedisQueueOutboundChannelAdapter;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class RedisMessageListener {

	@Autowired
	private RedisConnectionFactory factory;
	
	@ServiceActivator(inputChannel = "inputChannel",outputChannel="redisChannel")
	public Message<?> onMessage(Message<?> message){
		System.out.println("receive from message");
		return message;
	}
	
	
	@ServiceActivator(inputChannel = "redisChannel")
	public void sendMessageToQueue(Message<?> message){
		RedisQueueOutboundChannelAdapter adapter=new RedisQueueOutboundChannelAdapter("redis-queue", factory);
		adapter.handleMessage(message);
	}
	
	
	@ServiceActivator(inputChannel="receiverChannel")
	public void receiveFromQueue(Message<?> message) {
		System.out.println("received from redis queue "+message);
	}
	
	
}
