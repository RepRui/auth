package com.li.auth.config;


import com.li.auth.utils.RedisUtil;
import com.li.auth.utils.ToolsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean("stringRedisTemplate")
    StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {

        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        template.setDefaultSerializer(StringRedisSerializer.UTF_8);
       /*
        template.setConnectionFactory(redisConnectionFactory);
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        ObjectMapper om = new ObjectMapper();
        // 持久化改动.设置可见性,
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 持久化改动.非final类型的对象，把对象类型也序列化进去，以便反序列化推测正确的类型
        om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        // 持久化改动.null字段不显示
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 持久化改动.POJO无public属性或方法时不报错
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 持久化改动.setObjectMapper方法移除.使用构造方法传入ObjectMapper
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(om);
        template.setKeySerializer(redisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(redisSerializer);*/

        template.afterPropertiesSet();

        return template;
    }
    @Bean
    RedisUtil redisUtil(StringRedisTemplate stringRedisTemplate) {
        return new RedisUtil(stringRedisTemplate);
    }
}
