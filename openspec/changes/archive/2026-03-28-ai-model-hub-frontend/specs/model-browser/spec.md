## ADDED Requirements

### Requirement: 模型搜索功能
系统 SHALL 提供搜索功能，允许用户通过关键词搜索模型。

#### Scenario: 用户输入关键词搜索
- **WHEN** 用户在搜索框输入关键词
- **THEN** 系统 SHALL 实时过滤并显示匹配的模型列表

#### Scenario: 用户清除搜索
- **WHEN** 用户清除搜索框内容
- **THEN** 系统 SHALL 显示所有模型列表

### Requirement: 模型列表展示
系统 SHALL 以卡片形式展示模型列表，每个卡片包含模型的关键信息。

#### Scenario: 用户浏览模型列表
- **WHEN** 用户进入模型列表页面
- **THEN** 系统 SHALL 展示模型卡片网格，每个卡片包含：模型图标、名称、标签、简介、服务商、更新时间

#### Scenario: 用户滚动加载更多
- **WHEN** 用户滚动到页面底部
- **THEN** 系统 SHALL 加载更多模型（如支持分页）

### Requirement: 模型排序功能
系统 SHALL 提供排序选项，允许用户按不同维度排序模型列表。

#### Scenario: 用户按发布时间排序
- **WHEN** 用户选择按发布时间排序
- **THEN** 系统 SHALL 按模型发布时间重新排序列表

#### Scenario: 用户按价格排序
- **WHEN** 用户选择按输入价格或输出价格排序
- **THEN** 系统 SHALL 按所选价格维度重新排序列表

### Requirement: 模型统计展示
系统 SHALL 在列表顶部显示符合条件的模型数量。

#### Scenario: 用户查看筛选结果统计
- **WHEN** 用户应用筛选条件或搜索
- **THEN** 系统 SHALL 在列表顶部显示符合条件的模型总数（如「326个模型符合条件」）
