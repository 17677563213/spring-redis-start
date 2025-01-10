# Redis Starter 使用说明文档

## 1. 简介
Redis Starter 是一个基于 Spring Boot 的自动配置模块，提供了 Redis 连接和操作的便捷支持。它实现了自动配置，使得在 Spring Boot 项目中能够快速集成和使用 Redis。

## 2. 核心功能
- 自动配置 Redis 连接
- 提供 RedisTemplate 和 StringRedisTemplate
- 支持 Redis 连接池配置
- 提供默认的序列化方案
- 支持 YAML 配置文件

## 3. 模块结构
```
wxy-common/
├── src/main/java/org/example/common/redis/
│   ├── config/
│   │   ├── RedisAutoConfiguration.java    # Redis自动配置类
│   │   ├── RedisProperties.java           # Redis属性配置类
│   │   ├── RedisConfigurationImporter.java # Redis配置导入器
│   │   └── YamlPropertySourceFactory.java  # YAML配置文件加载工厂
│   ├── util/
│   │   └── RedisUtil.java                 # Redis工具类
│   └── EnableRedisConfig.java             # 启用Redis配置的注解
└── src/main/resources/
    ├── META-INF/
    │   └── spring.factories              # Spring Boot自动配置文件
    └── redis-default.yml                 # 默认Redis配置文件
```

## 4. 自动配置实现原理
### 4.1 配置加载流程
1. Spring Boot 启动时会扫描 `META-INF/spring.factories` 文件
2. 通过 `spring.factories` 加载 `RedisAutoConfiguration` 类
3. `RedisAutoConfiguration` 通过 `@Import` 导入 `RedisConfigurationImporter`
4. `RedisConfigurationImporter` 使用 `YamlPropertySourceFactory` 加载默认配置文件

### 4.2 核心注解说明
```java
@Configuration                           // 标识这是一个配置类
@ConditionalOnClass(RedisOperations.class) // 当类路径下存在RedisOperations时生效
@EnableConfigurationProperties(RedisProperties.class) // 启用配置属性绑定
@Import(RedisConfigurationImporter.class) // 导入配置导入器
```

## 5. 使用方法
### 5.1 引入依赖
在需要使用 Redis 的模块的 pom.xml 中添加：
```xml
<dependency>
    <groupId>org.example</groupId>
    <artifactId>wxy-common</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 5.2 启用 Redis 配置
在启动类上添加 `@EnableRedisConfig` 注解：
```java
@SpringBootApplication
@EnableRedisConfig
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### 5.3 配置 Redis
在应用的 application.yml 中导入 Redis 配置：
```yaml
spring:
  config:
    import: classpath:redis.yml
```

Redis 的具体配置在 redis.yml 中定义：
```yaml
spring:
  redis:
    host: localhost
    port: 6379
    password: 
    database: 0
    timeout: 3000
    lettuce:
      pool:
        max-active: 8
        max-wait: -1
        max-idle: 8
        min-idle: 0
```

你可以在自己的应用中创建 redis.yml 来覆盖默认配置。

### 5.4 使用 RedisUtil
注入 RedisUtil 来操作 Redis：
```java
@Autowired
private RedisUtil redisUtil;

// 设置值
redisUtil.set("key", "value");

// 获取值
Object value = redisUtil.get("key");
```

## 6. 扩展功能
### 6.1 自定义序列化
RedisTemplate 默认使用：
- key: StringRedisSerializer
- value: GenericJackson2JsonRedisSerializer
- hash key: StringRedisSerializer
- hash value: GenericJackson2JsonRedisSerializer

### 6.2 连接池配置
使用 Lettuce 连接池，支持以下配置：
- max-active: 最大连接数
- max-wait: 最大等待时间
- max-idle: 最大空闲连接
- min-idle: 最小空闲连接

## 7. 注意事项
1. 确保 Redis 服务器已启动且可访问
2. 配置文件中的密码项如果为空可以省略
3. 连接池配置根据实际需求调整
4. 在高并发场景下注意调整连接池参数

## 8. 常见问题
1. 连接超时：检查 Redis 服务是否启动，网络是否通畅
2. 序列化异常：确认存储的对象是否实现了 Serializable 接口
3. 连接池耗尽：适当调整连接池配置参数
