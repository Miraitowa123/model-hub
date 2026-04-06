## Context

基于用户提供的截图和需求，构建一个 AI 模型聚合平台的前端界面。该平台允许用户浏览、搜索和筛选全球优质 AI 模型。

## Goals / Non-Goals

**Goals:**
- 创建响应式的 React 前端工程
- 实现完整的页面结构和组件
- 使用 Tailwind CSS 实现现代化 UI
- 支持模型搜索、筛选和排序功能

**Non-Goals:**
- 后端 API 集成（使用模拟数据）
- 用户认证系统
- 模型详情页面
- 竞技场功能实现

## Decisions

### 技术栈选择
- **React + TypeScript**: 类型安全，开发体验好
- **Tailwind CSS**: 原子化 CSS，快速开发响应式界面
- **Lucide React**: 现代化图标库，与 Tailwind 配合良好
- **Vite**: 快速构建工具，支持热更新

### 组件架构
- 功能组件划分：
  - `Navbar`: 顶部导航栏
  - `HeroSection`: 主标题区
  - `FilterBar`: 筛选栏
  - `ModelCard`: 模型卡片
  - `ModelList`: 模型列表

### 状态管理
- 使用 React useState 管理本地状态
- 筛选条件、搜索关键词、排序方式等状态提升

## Risks / Trade-offs

- [使用模拟数据] → 后期需要替换为真实 API
- [单页面应用] → SEO 需要额外处理
- [无后端认证] → 登录/注册按钮为展示用途

## Migration Plan

1. 初始化 Vite + React + TypeScript 项目
2. 安装 Tailwind CSS 和 Lucide React
3. 按顺序开发各组件
4. 添加模拟数据
5. 测试和优化
