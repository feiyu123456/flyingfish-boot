package com.flyingfish.redis.core.autoconfiguration;

import com.flyingfish.redis.core.properties.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;

@SpringBootConfiguration
@EnableConfigurationProperties(RedisProperties.class)
@ConditionalOnClass(Jedis.class)
public class RedisAutoConfiguration {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedisConnectionFactory createRedisConnectionFactory() {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName(redisProperties.getHost());
        connectionFactory.setPort(redisProperties.getPort());
        connectionFactory.setPassword(redisProperties.getPassword());
        connectionFactory.setTimeout(redisProperties.getConfig().getTimeout());
        connectionFactory.getPoolConfig().setMaxIdle(redisProperties.getConfig().getMaxIdle());
        connectionFactory.getPoolConfig().setMaxTotal(redisProperties.getConfig().getMaxTotal());
        connectionFactory.getPoolConfig().setMaxWaitMillis(redisProperties.getConfig().getMaxWaitMillis());
        connectionFactory.getPoolConfig().setTestOnBorrow(redisProperties.getConfig().getTestOnBorrow());
        return connectionFactory;
    }

    @Bean
    public RedisTemplate taskRedisTemplate() {
        RedisTemplate template = new StringRedisTemplate();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new JdkSerializationRedisSerializer());
        template.setConnectionFactory(createRedisConnectionFactory());
        return template;
    }
}
