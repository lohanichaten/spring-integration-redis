package learning.integration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.redis.inbound.RedisQueueMessageDrivenEndpoint;
import org.springframework.scheduling.annotation.AsyncConfigurer;

@EnableIntegration
@Configuration
public class IntegrationConfig implements AsyncConfigurer{

	@Autowired
	private RedisProperties redisProperties;
	
	@Bean
	public RedisStandaloneConfiguration  redisClientConfiguration() {
		RedisStandaloneConfiguration redisConfig=new RedisStandaloneConfiguration();
		redisConfig.setHostName(redisProperties.getHost());
		redisConfig.setPort(redisProperties.getPort());
		
		return redisConfig;
	}
	
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory factory=new JedisConnectionFactory(redisClientConfiguration());
		return factory;
	}
	
	
	@Bean
	public DirectChannel receiverChannel() {
		return new DirectChannel();
	}
	
	
	@Bean
	public RedisQueueMessageDrivenEndpoint consumerEndPoint() {
		RedisQueueMessageDrivenEndpoint endpoint=new RedisQueueMessageDrivenEndpoint("redis-queue",jedisConnectionFactory());
		endpoint.setOutputChannel(receiverChannel());
		return endpoint;
	}
	
	
}
