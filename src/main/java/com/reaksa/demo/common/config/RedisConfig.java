package com.reaksa.demo.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisConfig {
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // build JSON serializer for Redis
        GenericJackson2JsonRedisSerializer jsonSerializer = this.buildRedisJsonSerializer();

        // default configuration
        RedisCacheConfiguration defaultConfig =  this.getRedisCacheConfig(30, jsonSerializer);

        // pagination configuration
        RedisCacheConfiguration paginatedConfig = this.getRedisCacheConfig(5, jsonSerializer);

        // pagination cache with 5mins TTL
        Map<String, RedisCacheConfiguration> paginationCacheConfigs = new HashMap<>();

        paginationCacheConfigs.put("products-paginated", paginatedConfig);

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(paginationCacheConfigs)
                .build();
    }

    private GenericJackson2JsonRedisSerializer buildRedisJsonSerializer() {
        PolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType(Object.class)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(typeValidator, ObjectMapper.DefaultTyping.NON_FINAL);

        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    private RedisCacheConfiguration getRedisCacheConfig(Integer duration, GenericJackson2JsonRedisSerializer jsonSerializer) {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(duration))
                .computePrefixWith(cacheName -> cacheName + ":")
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
                )
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer)
                )
                .disableCachingNullValues();
    }
}
