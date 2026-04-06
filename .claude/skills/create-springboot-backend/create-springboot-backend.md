---
name: create-springboot-backend
description: 创建 Spring Boot 后端 Hello World 项目（JDK17 + Gradle8.x + MySQL5.7 + Redis6.x）
license: MIT
compatibility: 需要 JDK 17、Gradle 8.x+、MySQL 5.7.x、Redis 6.x+
metadata:
  author: model_plaza
  version: "1.0"
  generatedBy: "manual"
---

使用指定的技术栈创建 Spring Boot 后端 Hello World 项目

**输入**: 用户请求应包含项目名称（可选，默认为"hello-world-backend"）

## 技术栈要求

| 组件            | 版本  | 说明             |
| --------------- | ----- | ---------------- |
| JDK             | 17    | LTS 版本         |
| Gradle          | 8.x+  | 构建工具         |
| Spring Boot     | 3.4.3 | 核心框架         |
| MySQL           | 5.7.x | 数据库           |
| Redis           | 6.x+  | 缓存             |
| MyBatis         | 3.x   | ORM 框架         |
| MyBatis-Plus    | 3.5.x | MyBatis 增强工具 |
| Spring Security | 6.x   | 安全框架         |

**数据库驱动**: mysql-connector-java:5.1.47（兼容 MySQL 5.7.x 和 OceanBase MySQL mode）

## 步骤

### 1. 获取项目名称
- 如果用户提供了项目名称，则使用该名称
- 否则，默认使用"hello-world-backend"
- 如果不清楚，询问用户："你想将项目命名为什么？"

### 2. 创建项目目录结构
```bash
mkdir -p <project-name>/src/main/java/com/example/<project-name>/{
  config,
  controller,
  service,
  mapper,
  entity,
  dto,
  common,
  security
}
mkdir -p <project-name>/src/main/resources/mapper
mkdir -p <project-name>/src/test/java/com/example/<project-name>
```

### 3. 创建 build.gradle
- 使用 Gradle 8.x 语法
- 添加 Spring Boot 3.4.3 依赖
- 添加 MyBatis-Plus 3.5.x
- 添加 mysql-connector-java:5.1.47
- 添加 Spring Data Redis
- 添加 Spring Cache
- 添加 Spring Security
- 添加 Lombok
- 参考本 skill 文件末尾附录中的依赖清单

### 4. 创建 settings.gradle
```gradle
rootProject.name = '<project-name>'
```

### 5. 创建 gradle.properties
```properties
org.gradle.jvmargs=-Xmx2048m
org.gradle.parallel=true
```

### 6. 创建 application.yml
- 配置数据源（MySQL 5.7.x）
- 配置 Redis 连接
- 配置 MyBatis-Plus
- 配置 Spring Cache
- 配置日志级别

```yaml
server:
  port: 8080

spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/<database_name>?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: tong1023tong

  redis:
    host: localhost
    port: 6379
    password:
    database: 0
    timeout: 10000ms

  cache:
    type: redis
    redis:
      time-to-live: 3600000

mybatis-plus:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.example.<project-name>.entity
  global-config:
    db-config:
      id-type: auto
      logic-delete-field: deleted
      logic-delete-value: 1
      logic-not-delete-value: 0
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl

logging:
  level:
    com.example.<project-name>: debug
    org.springframework.security: debug
```

### 7. 创建主启动类
```java
package com.example.<project-name>;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@MapperScan("com.example.<project-name>.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### 8. 创建通用响应类 (common/Result.java)
```java
package com.example.<project-name>.common;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }
}
```

### 9. 创建基础实体类 (entity/BaseEntity.java)
```java
package com.example.<project-name>.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
```

### 10. 创建示例实体类 (entity/User.java)
```java
package com.example.<project-name>.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user")
public class User extends BaseEntity {
    private String username;
    private String password;
    private String email;
}
```

### 11. 创建 MyBatis-Plus 元数据填充处理器 (config/MyMetaObjectHandler.java)
```java
package com.example.<project-name>.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
```

### 12. 创建 Redis 配置类 (config/RedisConfig.java)
```java
package com.example.<project-name>.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}
```

### 13. 创建 Spring Security 配置类 (security/SecurityConfig.java)
```java
package com.example.<project-name>.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/hello").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }
}
```

### 14. 创建 Mapper 接口 (mapper/UserMapper.java)
```java
package com.example.<project-name>.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.<project-name>.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
```

### 15. 创建 Service 接口和实现类

**service/UserService.java**:
```java
package com.example.<project-name>.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.<project-name>.entity.User;

public interface UserService extends IService<User> {
}
```

**service/impl/UserServiceImpl.java**:
```java
package com.example.<project-name>.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.<project-name>.entity.User;
import com.example.<project-name>.mapper.UserMapper;
import com.example.<project-name>.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
```

### 16. 创建 Hello Controller (controller/HelloController.java)
```java
package com.example.<project-name>.controller;

import com.example.<project-name>.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public Result<String> hello() {
        return Result.success("Hello, Spring Boot!");
    }
}
```

### 17. 创建数据库初始化脚本 (src/main/resources/schema.sql)
```sql
-- 用户表示例，遵循 snake_case 命名规范
-- 所有 SQL 适配 OceanBase MySQL mode

CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
```

### 18. 创建 .gitignore
```
.gradle/
build/
!gradle/wrapper/gradle-wrapper.jar
!**/src/main/**/build/
!**/src/test/**/build/

### STS ###
.apt_generated
.classpath
.factorypath
.project
.settings
.springBeans
.sts4-cache

### IntelliJ IDEA ###
.idea
*.iws
*.iml
*.ipr
out/
!**/src/main/**/out/
!**/src/test/**/out/

### NetBeans ###
/nbproject/private/
/nbbuild/
/dist/
/nbdist/
/.nb-gradle/

### VS Code ###
.vscode/

### Logs ###
logs/
*.log
```

### 19. 显示最终状态
- 显示项目结构
- 显示如何构建：`./gradlew build`
- 显示如何运行：`./gradlew bootRun`
- 显示如何测试：`./gradlew test`

## 数据库规范

### 命名规范
- **表名**: snake_case（如：`user_role`、`order_item`）
- **字段名**: snake_case（如：`create_time`、`user_name`）
- **主键**: `id`（BIGINT, AUTO_INCREMENT）
- **外键**: `{table}_id`（如：`user_id`）

### 必需字段
每个表都必须包含：
```sql
`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
```

### OceanBase MySQL Mode 兼容性
- 使用 `DATETIME` 而非 `TIMESTAMP`（更广泛兼容）
- 使用 `utf8mb4` 字符集
- 使用 `InnoDB` 存储引擎
- 避免使用 MySQL 8.0+ 特有的函数和语法

## 输出

完成所有步骤后，总结：
- 项目名称和位置
- 使用的关键技术栈（来自清单）
- 如何构建：`cd <project-name> && ./gradlew build`
- 如何运行：`cd <project-name> && ./gradlew bootRun`
- 项目结构说明：
  - `config/` - 配置类（Redis、MyBatis-Plus 填充器等）
  - `controller/` - REST API 控制器
  - `service/` - 业务逻辑层
  - `mapper/` - MyBatis-Plus Mapper 接口
  - `entity/` - 实体类
  - `dto/` - 数据传输对象
  - `common/` - 通用类（Result 等）
  - `security/` - Spring Security 配置
- 数据库初始化：执行 `schema.sql` 创建表结构

## 依赖选择规则

1. **优先使用清单版本**：添加依赖时，首先检查是否存在于本 skill 文件末尾的附录依赖清单
2. **使用清单中的确切版本**：如果找到，使用指定的版本
3. **选择兼容版本**：如果依赖不在清单中，选择与 Spring Boot 3.4.3 和 JDK 17 兼容的版本
4. **核心依赖必须包含**：
   - spring-boot-starter-web（核心 Web）
   - spring-boot-starter-security（安全）
   - spring-boot-starter-data-redis（Redis）
   - spring-boot-starter-cache（缓存）
   - mybatis-plus-spring-boot3-starter（ORM）

## 约束条件

- 创建 build.gradle 前必须先参考附录中的依赖清单
- 不要使用与清单冲突的依赖版本
- 数据库驱动必须使用 mysql:mysql-connector-java:5.1.47
- 所有表名和字段名必须使用 snake_case 命名规范
- 每个表都必须包含 create_time 和 update_time 字段
- SQL 语句必须兼容 OceanBase MySQL mode
- 创建后验证 build.gradle 是有效的 Groovy 语法
- 验证所有源文件是否正确创建

---

## 附录：依赖清单

以下是后端项目推荐使用的依赖及其对应版本，按功能分类整理：

### 核心框架
```gradle
// Spring Boot 3.4.3
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.springframework.boot:spring-boot-starter-validation'
implementation 'org.springframework.boot:spring-boot-starter-actuator'
```

### 安全框架
```gradle
implementation 'org.springframework.boot:spring-boot-starter-security'
```

### 数据库
```gradle
// MySQL 驱动（兼容 MySQL 5.7.x 和 OceanBase）
implementation 'mysql:mysql-connector-java:5.1.47'

// MyBatis-Plus（Spring Boot 3.x 版本）
implementation 'com.baomidou:mybatis-plus-spring-boot3-starter:3.5.5'

// 数据库连接池
implementation 'com.zaxxer:HikariCP'
```

### 缓存
```gradle
// Spring Cache
implementation 'org.springframework.boot:spring-boot-starter-cache'

// Redis
implementation 'org.springframework.boot:spring-boot-starter-data-redis'
```

### 工具库
```gradle
// Lombok
compileOnly 'org.projectlombok:lombok'
annotationProcessor 'org.projectlombok:lombok'

// Hutool 工具库
implementation 'cn.hutool:hutool-all:5.8.25'

// MapStruct 对象映射
implementation 'org.mapstruct:mapstruct:1.5.5.Final'
annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.5.Final'

// JSON 处理
implementation 'com.fasterxml.jackson.core:jackson-databind'
implementation 'com.fasterxml.jackson.datatype:jackson-datatype-jsr310'
```

### API 文档
```gradle
// SpringDoc OpenAPI（Spring Boot 3.x）
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0'
```

### 测试
```gradle
testImplementation 'org.springframework.boot:spring-boot-starter-test'
testImplementation 'org.springframework.security:spring-security-test'
```

### 完整 build.gradle 示例

```gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.4.3'
    id 'io.spring.dependency-management' version '1.1.4'
}

group = 'com.example'
version = '0.0.1-SNAPSHOT'

java {
    sourceCompatibility = '17'
}

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // 核心框架
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.springframework.boot:spring-boot-starter-actuator'

    // 安全框架
    implementation 'org.springframework.boot:spring-boot-starter-security'

    // 数据库
    implementation 'mysql:mysql-connector-java:5.1.47'
    implementation 'com.baomidou:mybatis-plus-spring-boot3-starter:3.5.5'
    implementation 'com.zaxxer:HikariCP'

    // 缓存
    implementation 'org.springframework.boot:spring-boot-starter-cache'
    implementation 'org.springframework.boot:spring-boot-starter-data-redis'

    // 工具库
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    implementation 'cn.hutool:hutool-all:5.8.25'
    implementation 'org.mapstruct:mapstruct:1.5.5.Final'
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.5.Final'
    implementation 'com.fasterxml.jackson.core:jackson-databind'
    implementation 'com.fasterxml.jackson.datatype:jackson-datatype-jsr310'

    // API 文档
    implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0'

    // 测试
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
}

tasks.named('test') {
    useJUnitPlatform()
}
```

### Gradle Wrapper 配置 (gradle/wrapper/gradle-wrapper.properties)

```properties
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.5-bin.zip
networkTimeout=10000
validateDistributionUrl=true
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
```