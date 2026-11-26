# Palette 工程协作约束

## 项目定位

Palette 是企业级应用开发平台。Palette BFF 不承载核心业务领域逻辑，也不替代公司级 API Gateway。

## 开发方式

- Backend First：先完成 Palette BFF，再开发前端通用框架。
- Step-by-Step、Incremental Development、MVP-first。
- 每次只执行当前明确指定的 Step。
- 编码前说明方案、计划文件、验收标准并等待确认。
- 完成后执行编译、测试和必要的启动验证。
- 未经确认不得进入下一 Step。
- 不虚构企业身份源、下游接口、认证配置或基础设施。

## 技术约束

- Java 21（以企业标准最终确认）。
- Spring Boot 3.x、Spring Security 6、Spring MVC。
- Gradle Kotlin DSL、Gradle Wrapper、Java Toolchains。
- 优先使用 Spring 原生能力，不做不必要的自定义封装。
- 构建统一使用 Wrapper，不使用 Maven。
- 企业仓库、凭据、秘密和环境配置不得提交到仓库。

## 架构约束

- SOLID、关注点分离、高内聚低耦合。
- API-first、配置驱动、可测试、可观测、向后兼容。
- 安全由设计开始：安全 Cookie、CSRF、最小权限、受控代理、敏感数据脱敏。
- 无客户端任意指定下游目标地址。
- Access Token 与 Refresh Token 不暴露给前端 JavaScript。
- 后端必须执行真正的权限校验。

## 验证约束

每个 Step 完成后至少执行：

```bash
./gradlew clean build
./gradlew test
./gradlew check
./gradlew bootRun
```

不得标记未实际执行的测试为通过。启动验证可能因明确的环境缺失而保留为风险项，但必须如实记录。

## 文档约束

持续维护 README、需求、架构、路线图、进度、安全、API、开发者指南、接入指南和 ADR。
