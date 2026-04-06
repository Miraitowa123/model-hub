## ADDED Requirements

### Requirement: 模型卡片展示
系统 SHALL 提供统一的模型卡片组件，展示模型的关键信息。

#### Scenario: 卡片展示模型图标
- **WHEN** 系统渲染模型卡片
- **THEN** 卡片 SHALL 在左上角显示模型提供商的图标

#### Scenario: 卡片展示模型名称
- **WHEN** 系统渲染模型卡片
- **THEN** 卡片 SHALL 在图标右侧显示模型名称

#### Scenario: 卡片展示模型标签
- **WHEN** 系统渲染模型卡片
- **THEN** 卡片 SHALL 在模型名称下方显示模型的分类标签（如「文本生成」「代码生成」等）

#### Scenario: 卡片展示模型简介
- **WHEN** 系统渲染模型卡片
- **THEN** 卡片 SHALL 显示模型的简要描述，介绍其主要功能和特点

#### Scenario: 卡片展示服务商信息
- **WHEN** 系统渲染模型卡片
- **THEN** 卡片 SHALL 在底部显示模型所属的服务商名称（如 OpenAI、Anthropic、Google 等）

#### Scenario: 卡片展示更新时间
- **WHEN** 系统渲染模型卡片
- **THEN** 卡片 SHALL 显示模型的最后更新时间（如「3天前」「1周前」等相对时间）

### Requirement: 模型卡片交互
系统 SHALL 支持模型卡片的基本交互功能。

#### Scenario: 用户点击模型卡片
- **WHEN** 用户点击模型卡片
- **THEN** 系统 SHALL 导航到模型详情页面（或显示详情弹窗）

#### Scenario: 鼠标悬停效果
- **WHEN** 用户鼠标悬停在模型卡片上
- **THEN** 卡片 SHALL 显示悬停效果（如阴影、边框高亮等视觉反馈）
