## ADDED Requirements

### Requirement: 多维度筛选引擎
系统 SHALL 实现一个多维度筛选引擎，支持按多个条件组合筛选 AI 模型。

#### Scenario: 按服务商筛选
- **WHEN** 用户通过 `providers` 参数传递服务商列表（如 `providers=OpenAI,Anthropic`）
- **THEN** 系统 SHALL 只返回指定服务商的模型

#### Scenario: 按分类筛选
- **WHEN** 用户通过 `categories` 参数传递分类列表（如 `categories=文本生成,代码生成`）
- **THEN** 系统 SHALL 只返回指定分类的模型

#### Scenario: 按能力筛选
- **WHEN** 用户通过 `capabilities` 参数传递能力列表（如 `capabilities=函数调用,视觉能力`）
- **THEN** 系统 SHALL 只返回具备指定能力的模型

#### Scenario: 按上下文长度筛选
- **WHEN** 用户通过 `contextLengths` 参数传递上下文长度范围（如 `contextLengths=32K-128K`）
- **THEN** 系统 SHALL 只返回符合指定上下文长度范围的模型

### Requirement: 组合筛选逻辑
系统 SHALL 支持多个筛选条件的组合使用，使用 AND 逻辑。

#### Scenario: 多条件组合筛选
- **WHEN** 用户同时传递多个筛选条件（如 `providers=OpenAI&categories=文本生成&capabilities=函数调用`）
- **THEN** 系统 SHALL 返回同时满足所有条件的模型（OpenAI 的文本生成模型，且支持函数调用）

### Requirement: 搜索功能
系统 SHALL 提供关键词搜索功能，支持在模型名称和描述中搜索。

#### Scenario: 关键词搜索
- **WHEN** 用户通过 `search` 参数传递搜索关键词（如 `search=GPT`）
- **THEN** 系统 SHALL 返回名称或描述中包含关键词的模型

#### Scenario: 搜索与筛选结合
- **WHEN** 用户同时传递搜索关键词和筛选条件
- **THEN** 系统 SHALL 先执行搜索，再在搜索结果中应用筛选条件

### Requirement: 排序功能
系统 SHALL 提供灵活的排序功能，支持多种排序方式。

#### Scenario: 按发布时间排序
- **WHEN** 用户通过 `sort=publishedAt` 和 `order=desc` 参数请求排序
- **THEN** 系统 SHALL 按发布时间降序排列（最新的在前）

#### Scenario: 按价格排序
- **WHEN** 用户通过 `sort=inputPrice` 和 `order=asc` 参数请求排序
- **THEN** 系统 SHALL 按输入价格升序排列（最便宜的在前）

#### Scenario: 默认排序
- **WHEN** 用户未指定排序参数
- **THEN** 系统 SHALL 按更新时间降序排列（默认排序）

### Requirement: 分页功能
系统 SHALL 提供分页功能，支持大数据集的分页展示。

#### Scenario: 基本分页
- **WHEN** 用户通过 `page=1` 和 `size=20` 参数请求分页数据
- **THEN** 系统 SHALL 返回第 1 页的数据，每页 20 条

#### Scenario: 分页信息
- **WHEN** 系统返回分页数据
- **THEN** 响应 SHALL 包含当前页码、每页大小、总页数、总记录数等信息

#### Scenario: 默认分页
- **WHEN** 用户未指定分页参数
- **THEN** 系统 SHALL 使用默认分页（第 1 页，每页 20 条）
