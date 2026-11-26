# Palette 开发进度

## 当前阶段

**Step 0 — Requirement Analysis & Architecture Design**

状态：已完成设计文档草案，等待确认。

## 已完成

- 检查 Workspace：仓库为干净的 `main` 分支。
- 确认仓库尚无现有 Java、Gradle 或应用源码。
- 明确 Palette BFF 定位、职责边界和 MVP 范围。
- 建立系统上下文、认证交互时序和部署候选方案。
- 建立初始 Gradle 技术路线和项目目录建议。
- 创建初始需求、架构、安全、路线图、API、接入文档和 ADR。

## 已确认

- Java 21 已确认为项目运行基线。
- 企业身份源已确认为 Keycloak。
- 构建工具已确认使用 Gradle Kotlin DSL 与 Gradle Wrapper。

## 验证结果

- 未执行编译、测试或启动验证：Step 0 不编写代码。
- 未发现已有未提交项目修改。
- 未虚构企业身份源、下游服务或基础设施。

## 待确认问题

- Spring Boot 3、Spring Security 6 与 Gradle 的具体企业批准版本。
- Keycloak Realm、Issuer、Client ID、回调地址及企业配置端点。
- API Gateway 与 BFF 是否共同部署，及其职责边界。
- 部署平台和会话存储方式。
- 目标下游服务发现机制。
- 前端技术版本与 HudUI 交付方式。
- 企业 Artifact Repository 与密钥管理方式。
- 生产与非生产环境域名策略。

## 下一步

经确认后进入 **Step 1 — Project Initialization**。

仅初始化 Spring Boot 3 + Gradle Kotlin DSL 基础骨架，不实现认证、授权、Session 管理或下游集成。
