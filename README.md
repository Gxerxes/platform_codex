# Palette Enterprise Application Platform

Palette 是面向 HKEX PTS 部门内部业务开发团队的企业级应用开发平台，分为：

- **Palette BFF**：基于 Java 21、Spring Boot 3、Spring Security 6 与 Gradle 的 Backend for Frontend。
- **Palette UI Common Framework**：基于 React、TypeScript 与 HudUI 的通用前端框架，待 BFF 核心能力稳定后实施。

## 当前阶段

当前处于 **Step 0 — Requirement Analysis & Architecture Design**，仅进行架构设计，不实现代码。

## 文档导航

- [需求与范围](docs/requirements.md)
- [系统架构](docs/architecture.md)
- [开发路线图](docs/roadmap.md)
- [当前进度](docs/progress.md)
- [安全设计](docs/security.md)
- [API 合约](docs/api-contract.md)
- [开发者指南](docs/developer-guide.md)
- [业务接入指南](docs/onboarding-guide.md)
- [架构决策记录](docs/adr/)
- [工程协作约束](AGENTS.md)

## 构建工具约定

Palette 后端统一使用 **Gradle Kotlin DSL 与 Gradle Wrapper**。实现阶段的命令：

```bash
./gradlew clean build
./gradlew test
./gradlew check
./gradlew bootRun
./gradlew bootJar
./gradlew dependencies
./gradlew dependencyInsight --dependency <dependency-name>
```

Windows 环境使用 `gradlew.bat`。

> 当前仓库尚未初始化 Gradle 项目或 Java 源码。
