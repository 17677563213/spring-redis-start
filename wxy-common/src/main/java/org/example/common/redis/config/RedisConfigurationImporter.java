package org.example.common.redis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:redis-default.yml", factory = YamlPropertySourceFactory.class)
public class RedisConfigurationImporter {
}
