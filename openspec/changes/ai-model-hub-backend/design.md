## Context

基于前端 AI 模型聚合平台的需求，构建配套的 Spring Boot 后端服务。前端使用 React + TypeScript 实现，需要后端提供 RESTful API 支持模型的列表展示、搜索、筛选、排序等功能。

## Goals / Non-Goals

**Goals:**
- 构建完整的 Spring Boot 3.x 后端服务
- 提供 RESTful API 支持前端所有功能
- 实现多维度筛选、搜索、排序逻辑
- 支持开发环境快速启动（模拟数据）

**Non-Goals:**
- 生产环境数据库配置（当前仅支持 H2 内存数据库）
- 用户认证与授权（JWT/Session）
- 文件上传/下载功能
- 缓存机制（Redis）
- 消息队列集成

## Decisions

### 技术栈选择
- **Spring Boot 3.4.x**: 最新稳定版本，支持 Java 17+
- **Java 17**: LTS 版本，新特性支持良好
- **Spring Web**: RESTful API 支持
- **H2 Database**: 开发环境内存数据库，零配置启动
- **Lombok**: 减少样板代码
- **MapStruct**: 对象映射（Entity ↔ DTO）

### 架构设计
- **分层架构**: Controller → Service → Repository → Entity
- **DTO 模式**: 使用 DTO 进行数据传输，隐藏实体细节
- **统一响应**: 使用统一的 API 响应格式

### 数据初始化
- 使用 `CommandLineRunner` 在应用启动时初始化模拟数据
- 支持 12+ 个 AI 模型数据，覆盖多个服务商和分类

## Risks / Trade-offs

- [使用 H2 内存数据库] → 仅适用于开发环境，生产需替换为 MySQL/PostgreSQL
- [无用户认证] → 当前所有 API 公开可访问，生产环境需添加 JWT/OAuth2
- [模拟数据] → 数据在重启后重置，实际应用需持久化存储

## Migration Plan

1. 创建 Spring Boot 项目结构
2. 配置 `application.yml` 和依赖
3. 创建实体类 (Model, Provider, Category 等)
4. 创建 DTO 类和 Mapper
5. 实现 Repository 层
6. 实现 Service 层（包含筛选、搜索、排序逻辑）
7. 实现 Controller 层（RESTful API）
8. 创建数据初始化器
9. 测试并验证 API

## Open Questions

- 是否需要添加 API 文档（Swagger/OpenAPI）？
- 生产环境数据库选型（MySQL vs PostgreSQL）？
- 是否需要添加缓存层（Redis）提升性能？
