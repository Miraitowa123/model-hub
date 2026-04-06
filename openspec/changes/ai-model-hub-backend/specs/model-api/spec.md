## ADDED Requirements

### Requirement: AI 模型列表查询
系统 SHALL 提供 API 接口支持查询 AI 模型列表，支持分页、筛选和排序。

#### Scenario: 用户查询模型列表
- **WHEN** 用户发送 GET 请求到 `/api/v1/models`
- **THEN** 系统 SHALL 返回模型列表，包含分页信息

#### Scenario: 用户筛选模型列表
- **WHEN** 用户通过查询参数传递筛选条件（如 `provider=OpenAI`）
- **THEN** 系统 SHALL 只返回符合条件的模型

#### Scenario: 用户搜索模型
- **WHEN** 用户通过 `search` 参数传递搜索关键词
- **THEN** 系统 SHALL 返回名称或描述包含关键词的模型

#### Scenario: 用户排序模型列表
- **WHEN** 用户通过 `sort` 参数指定排序方式（如 `publishedAt`）
- **THEN** 系统 SHALL 按指定方式排序返回结果

### Requirement: AI 模型详情查询
系统 SHALL 提供 API 接口支持查询单个 AI 模型的详细信息。

#### Scenario: 用户查询模型详情
- **WHEN** 用户发送 GET 请求到 `/api/v1/models/{id}`
- **THEN** 系统 SHALL 返回该模型的完整信息

#### Scenario: 用户查询不存在的模型
- **WHEN** 用户发送 GET 请求到 `/api/v1/models/{id}`，但 ID 不存在
- **THEN** 系统 SHALL 返回 404 状态码和错误信息

### Requirement: AI 模型统计信息
系统 SHALL 提供 API 接口支持获取模型统计信息。

#### Scenario: 用户获取统计数据
- **WHEN** 用户发送 GET 请求到 `/api/v1/models/stats`
- **THEN** 系统 SHALL 返回模型总数、服务商数量、分类数量等统计数据

### Requirement: 服务商列表
系统 SHALL 提供 API 接口支持获取服务商列表。

#### Scenario: 用户获取服务商列表
- **WHEN** 用户发送 GET 请求到 `/api/v1/providers`
- **THEN** 系统 SHALL 返回所有服务商列表，包含服务商名称和模型数量

### Requirement: 分类列表
系统 SHALL 提供 API 接口支持获取分类列表。

#### Scenario: 用户获取分类列表
- **WHEN** 用户发送 GET 请求到 `/api/v1/categories`
- **THEN** 系统 SHALL 返回所有分类列表，包含分类名称和模型数量
