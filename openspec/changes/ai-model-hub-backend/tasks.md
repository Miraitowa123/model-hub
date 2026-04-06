# Tasks: AI Model Hub Backend

## Task 1: 创建 Spring Boot 项目结构和配置
- [ ] 1.1 创建 `ai-model-hub-backend` 目录结构
- [ ] 1.2 创建 `pom.xml` 添加 Spring Boot 3.4.x、H2、Lombok 等依赖
- [ ] 1.3 创建 `application.yml` 配置 H2 数据库和 JPA
- [ ] 1.4 创建主应用程序类 `ModelHubApplication`

## Task 2: 创建实体类
- [ ] 2.1 创建 `Provider` 实体（服务商）
- [ ] 2.2 创建 `Category` 实体（分类）
- [ ] 2.3 创建 `Capability` 实体（能力）
- [ ] 2.4 创建 `Model` 实体（AI模型）
- [ ] 2.5 创建 `ModelPricing` 嵌入类（定价信息）

## Task 3: 创建 DTO 类和 Mapper
- [ ] 3.1 创建 `ModelDto` - 模型列表DTO
- [ ] 3.2 创建 `ModelDetailDto` - 模型详情DTO
- [ ] 3.3 创建 `ProviderDto` - 服务商DTO
- [ ] 3.4 创建 `CategoryDto` - 分类DTO
- [ ] 3.5 创建 `ModelMapper` - ModelStruct 映射器

## Task 4: 创建 Repository 层
- [ ] 4.1 创建 `ModelRepository` - 带分页和筛选查询
- [ ] 4.2 创建 `ProviderRepository`
- [ ] 4.3 创建 `CategoryRepository`

## Task 5: 实现 Service 层和筛选引擎
- [ ] 5.1 创建 `ModelFilterCriteria` - 筛选条件封装
- [ ] 5.2 创建 `ModelSpecifications` - JPA 规格查询
- [ ] 5.3 创建 `ModelService` 接口
- [ ] 5.4 实现 `ModelServiceImpl` - 包含筛选、搜索、排序逻辑

## Task 6: 实现 REST Controller 层
- [ ] 6.1 创建 `ModelController` - `/api/v1/models` 端点
- [ ] 6.2 创建 `ProviderController` - `/api/v1/providers` 端点
- [ ] 6.3 创建 `CategoryController` - `/api/v1/categories` 端点
- [ ] 6.4 创建 `GlobalExceptionHandler` - 全局异常处理
- [ ] 6.5 创建统一 API 响应封装类

## Task 7: 数据初始化和测试
- [ ] 7.1 创建 `DataSeeder` - 模拟数据初始化
- [ ] 7.2 添加 12+ 个 AI 模型数据（OpenAI、Anthropic等）
- [ ] 7.3 测试 API 端点是否正常工作

---

**进度跟踪:**
- 已完成: 0/7 任务
- 待开始: 7/7 任务
