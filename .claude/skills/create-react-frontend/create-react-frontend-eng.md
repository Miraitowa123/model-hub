# AGENTS.md - AI Assistant Guide for AntD Demo Frontend

This file provides comprehensive guidance for AI assistants working on the Intelligent Party Building System (aka **IPCS**) frontend. It defines the architecture, coding standards, and quality requirements.

## Project Overview

The **Intelligent Party Building System** is a modern web application built with **React 19** and **Ant Design 6**. It focuses on high-performance UI, type safety, and a streamlined developer experience using **Vite**.

## Build System

### Prerequisites

- **Node.js**: v22.x+ (Recommended)
- **Package Manager**: npm
- **Build Tool**: Vite 8.x
- **Language**: TypeScript 5.x+ (Strict Mode)

### Package Manager Configuration

- **DO NOT MODIFY**: Never change the `npm config registry` or any local `.npmrc` registry settings. These are pre-configured for internal or optimized access.  

### Common Build Commands

```bash
# Start development server
npm run dev

# Build for production (includes type checking)
npm run build

# Lint the project
npm run lint

# Preview production build
npm run preview
```

## Repository Structure

The project follows a feature-oriented component structure:

```text
frontend/
├── public/
└── src/
    ├── assets/             # Static assets (images, svgs)
    ├── components/         # Shared global components
    │   ├── common/         # Base UI wrappers
    │   └── layout/         # Layout-related components
    ├── hooks/              # Shared custom React hooks
    ├── pages/              # Page-level components (Routes)
    ├── services/           # API integration and data fetching
    ├── store/              # State management (Zustand/Context/Redux)
    ├── types/              # Global TypeScript definitions
    ├── utils/              # Helper functions and constants
    ├── App.tsx             # Root component and routing
    ├── main.tsx            # Entry point
    └── index.css           # Global styles
```

## Architecture & Technology Stack

### Core Frameworks
- **Library**: React 19 (Utilizing React Compiler)
- **UI Framework**: **Ant Design 6.x**
- **Icons**: **@ant-design/icons** (Primary choice for UI consistency)
- **Styling**: Vanilla CSS / CSS Modules (Maintain high performance and standard compliance)
- **Build Tool**: Vite 8

### UI & Component Guidelines
- **AntDesign First**: Always check for an Ant Design component before building a custom one.
- **Theme Customization**: Use Ant Design's `ConfigProvider` for global theme tokens. Avoid hardcoding hex colors.
- **Icons**: Use `@ant-design/icons` exclusively. Ensure they are imported as individual components to support tree-shaking.
- **Forms**: Use AntDesign `Form` component with built-in validation rules and `Form.useForm()` hook.

### TypeScript Standards
- **Strict Mode**: `strict: true` is mandatory in `tsconfig.json`.
- **No `any`**: Explicitly define types or interfaces for all props, state, and API responses.
- **Inference**: Leverage TS inference for local variables, but be explicit for exported members.
- **Functional Components**: Use standard function declarations with typed props.

## Code Style & Conventions

- **Component Patterns**: Prefer Functional Components with Hooks. Use `useMemo` and `useCallback` when optimizing expensive calculations or preventing unnecessary downstream renders.
- **Naming**: 
  - Components: `PascalCase` (e.g., `UserCard.tsx`)
  - Hooks: `camelCase` starting with `use` (e.g., `useAuth.ts`)
  - Files/Folders: `kebab-case` for non-component files, or matching component name.
- **Props**: Destructure props in the function signature for clarity.
- **ESLint**: Adhere to the rules defined in `eslint.config.js`. Use `typescript-eslint` for advanced type checking.

## Testing & Quality Redlines

### Mandatory Coverage
- **Shared Components**: Unit tests using **Vitest** and **React Testing Library** are highly recommended for complex logic.
- **Hooks**: Complex state transitions in custom hooks must be tested.
- **Coverage Goal**: Aim for **80%** on business logic and shared utilities.

### Running Tests (If configured)
```bash
# Run all tests
npm run test

# Run tests in watch mode
npm run test:watch
```

## Tips for AI Assistants

1. **AntDesign Synergy**: When suggesting UI changes, leverage AntDesign's design tokens and pre-built patterns (e.g., `Space`, `Flex`, `Typography`).
2. **React 19 Features**: Use the new `use` hook, improved `ref` handling, and the React Compiler for optimization.
3. **Type-Safe API**: Ensure all service calls are fully typed using interfaces from `src/types/`.
4. **Performance**: Prioritize "Surgical Edits" to minimize re-renders. Avoid wrapping the entire app in heavy providers if not necessary.
5. **Simplicity**: Favor Vanilla CSS over complex CSS-in-JS libraries unless explicitly requested, ensuring the build remains light and standard.