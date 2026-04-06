## ADDED Requirements

### Requirement: 服务商筛选
系统 SHALL 提供按服务商筛选模型的功能。

#### Scenario: 用户选择服务商筛选
- **WHEN** 用户点击服务商筛选标签（如 OpenAI、Anthropic、Google、阿里云等）
- **THEN** 系统 SHALL 只显示所选服务商的模型

#### Scenario: 用户清除服务商筛选
- **WHEN** 用户再次点击已选中的服务商标签，或点击「全部」标签
- **THEN** 系统 SHALL 显示所有服务商的模型

### Requirement: 分类筛选
系统 SHALL 提供按模型分类筛选的功能。

#### Scenario: 用户选择分类筛选
- **WHEN** 用户点击分类筛选标签（如 文本生成、代码生成、图像生成、向量/嵌入等）
- **THEN** 系统 SHALL 只显示所选分类的模型

#### Scenario: 用户清除分类筛选
- **WHEN** 用户再次点击已选中的分类标签，或点击「全部」标签
- **THEN** 系统 SHALL 显示所有分类的模型

### Requirement: 能力筛选
系统 SHALL 提供按模型能力筛选的功能。

#### Scenario: 用户选择能力筛选
- **WHEN** 用户点击能力筛选标签（如 函数调用、视觉能力、JSON模式等）
- **THEN** 系统 SHALL 只显示具备所选能力的模型

#### Scenario: 用户清除能力筛选
- **WHEN** 用户再次点击已选中的能力标签，或点击「全部」标签
- **THEN** 系统 SHALL 显示所有能力的模型

### Requirement: 上下文长度筛选
系统 SHALL 提供按上下文长度筛选的功能。

#### Scenario: 用户选择上下文长度筛选
- **WHEN** 用户点击上下文长度筛选标签（如 8K、32K、128K、200K 等）
- **THEN** 系统 SHALL 只显示支持所选上下文长度的模型

#### Scenario: 用户清除上下文长度筛选
- **WHEN** 用户再次点击已选中的上下文长度标签，或点击「全部」标签
- **THEN** 系统 SHALL 显示所有上下文长度的模型

### Requirement: 筛选组合
系统 SHALL 支持多个筛选条件的组合使用。

#### Scenario: 用户使用多维度组合筛选
- **WHEN** 用户同时选择了多个筛选维度（如服务商=OpenAI + 分类=文本生成）
- **THEN** 系统 SHALL 只显示同时满足所有筛选条件的模型

#### Scenario: 用户清除所有筛选
- **WHEN** 用户点击「清除全部」按钮或类似操作
- **THEN** 系统 SHALL 清除所有筛选条件，显示所有模型
