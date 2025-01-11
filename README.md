# Spring Boot 多模块项目示例

这是一个基于Spring Boot的多模块项目示例，展示了如何构建一个具有良好架构设计的企业级应用。

## 项目结构

```
spring-start/
├── wxy-business/        # 业务模块
├── wxy-common/         # 公共模块
└── pom.xml            # 父项目POM文件
```

## 模块说明

### wxy-common（公共模块）

公共模块包含了可以被其他模块复用的组件和工具类：

- Redis配置和工具类
  - 自动配置支持
  - Redis操作工具类
  - 分布式锁实现
  - 缓存管理

### wxy-business（业务模块）

业务模块包含了具体的业务实现：

- 用户管理
- 示例API接口
- 业务逻辑实现

## 技术栈

- Spring Boot
- Spring Data JPA
- Redis (Lettuce)
- MySQL
- Lombok
- Maven

## 特性

- [x] 模块化架构
- [x] 自动配置支持
- [x] Redis集成
- [x] 统一异常处理
- [x] RESTful API
- [x] 构造器注入模式

## 快速开始

1. **环境要求**
   - JDK 11+
   - Maven 3.6+
   - Redis 6+
   - MySQL 8+

2. **配置数据库**
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/your_database
       username: your_username
       password: your_password
   ```

3. **配置Redis**
   ```yaml
   spring:
     redis:
       host: localhost
       port: 6379
       password: your_password
   ```

4. **构建项目**
   ```bash
   mvn clean install
   ```

5. **运行应用**
   ```bash
   cd wxy-business
   mvn spring-boot:run
   ```

## 最佳实践

### 1. 依赖注入

推荐使用构造器注入方式，配合Lombok的@RequiredArgsConstructor注解：

```java
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RedisUtil redisUtil;
}
```

### 2. Redis使用

Redis工具类提供了常用的操作方法：

```java
// 设置缓存
redisUtil.set("key", value);

// 获取缓存
Object value = redisUtil.get("key");

// 设置带过期时间的缓存
redisUtil.set("key", value, 60); // 60秒后过期
```

### 3. 自动配置

项目使用Spring Boot的自动配置机制，通过META-INF/spring.factories文件配置：

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.example.common.redis.config.RedisAutoConfiguration
```

## 开发规范

1. **代码风格**
   - 使用Lombok简化代码
   - 遵循阿里巴巴Java开发规范
   - 使用构造器注入

2. **命名规范**
   - 类名：驼峰式，首字母大写
   - 方法名：驼峰式，首字母小写
   - 常量：全大写，下划线分隔

3. **注释规范**
   - 类添加Javadoc注释
   - 关键方法添加注释
   - 复杂逻辑添加行注释

## 常见问题

1. **Redis连接问题**
   ```
   问题：无法连接Redis服务器
   解决：检查Redis配置和网络连接
   ```

2. **自动配置不生效**
   ```
   问题：自定义的自动配置没有生效
   解决：检查spring.factories文件配置
   ```

## 贡献指南

1. Fork 项目
2. 创建特性分支
3. 提交改动
4. 推送到分支
5. 创建Pull Request

## 版本历史

- v1.0.0
  - 初始版本
  - 基础功能实现

## 维护者

- @wxy

## 许可证

[MIT](LICENSE) © wxy
