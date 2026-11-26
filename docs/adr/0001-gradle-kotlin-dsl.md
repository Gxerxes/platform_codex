# ADR-0001：Palette BFF 使用 Gradle Kotlin DSL

## 状态

已采纳（Step 0 架构约束），等待企业标准版本最终确认。

## 背景

Palette BFF 是新建企业应用，构建系统需支持 Java 21、Spring Boot 3、可复现 Wrapper、企业仓库、依赖锁定与 CI/CD 集成。

## 决策

- 使用 Gradle，而非 Maven。
- 使用 Gradle Kotlin DSL。
- 提交 Gradle Wrapper 文件。
- 使用 Spring Boot Dependency Management/BOM。
- 使用 Java Toolchains。
- 初始保持单模块。
- 只在演进为多模块或共享约定较大时评估 Version Catalog 和 Convention Plugins。

## 可选方案

### Maven

优点：企业成熟度高、配置直观。

缺点：本项目明确要求 Gradle，因此不采纳。

### Gradle Groovy DSL

优点：文档和社区示例多。

缺点：相较 Kotlin DSL，类型提示和 IDE 重构支持较弱；本项目明确要求 Kotlin DSL。

## 版本原则

具体 Java、Spring Boot、Spring Security 和 Gradle 版本须以企业批准标准确认，不在 Step 0 随意写入固定版本。

## 后果

- 项目需要完整提交 Wrapper 和 Gradle 配置。
- CI/CD 配置必须以 `./gradlew` 为准。
- 文档和脚本不能保留 Maven 命令。
- 技术栈升级需要单独 ADR 或可验证变更。
