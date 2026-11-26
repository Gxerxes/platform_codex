# Palette BFF 需求与范围

## 1. 目标

为内部业务应用提供统一的登录入口、服务端会话、令牌生命周期、受控下游访问、权限校验、错误处理和可观测性基础能力。

## 2. 系统定位

Palette BFF 位于浏览器、企业身份源、API Gateway 和业务下游服务之间，承担应用接入的安全与平台能力，但不承载业务领域逻辑。

## 3. 功能需求

- 企业身份提供商及 OIDC 登录。
- Authorization Code Flow。
- 浏览器侧安全 Session Cookie。
- 服务端管理 Access Token 与 Refresh Token。
- Session 与令牌过期、刷新、注销及并发刷新。
- CSRF、CORS、安全响应头及安全入口点。
- 配置驱动的受控下游路由。
- Token Relay 与受控请求转发。
- 统一错误及 401/403 区分。
- 角色、权限和应用级权限校验。
- 请求关联、结构化日志、审计、指标、追踪。
- 健康、就绪和存活检查。

## 4. 非功能需求

- 企业身份提供商：Keycloak（OIDC）；具体 Realm、Issuer、Client 与回调地址使用占位配置。
- 兼容 Java 21 的企业批准 Spring Boot 3 与 Spring Security 6 版本在 Step 1 初始化时选定并锁定。
- Gradle 版本由 Gradle Wrapper 固定，版本选择应兼容 Java 21 与企业标准。
- Gradle Kotlin DSL 与 Wrapper 可复现构建。
- 无敏感令牌或密钥暴露给浏览器、日志或配置仓库。
- 支持水平扩展的分布式 Session 设计，Redis 为按需选项。
- 支持超时、受控重试和可选熔断。
- 兼容现有企业网络、代理、证书和身份源配置。
- 所有外部依赖通过 Mock/Stub 或配置占位符可测试。

## 5. 明确不在 MVP范 围

- 业务领域逻辑。
- 替代 API Gateway 的流量策略或通用防护。
- 任意代理目标地址。
- 前端实现。
- 未经确认的企业 IdP、网关和下游接口接入。
- 提前建立复杂多模块 Gradle 架构。

## 6. MVP 功能

MVP 聚焦：

1. 可启动的 Spring Boot 3 应用。
2. Hello API 与基础健康检查。
3. 基础配置、异常响应和关联 ID。
4. Spring Security 本地与受保护端点。
5. 本地 Mock IdP 和 OIDC 配置占位。
6. 浏览器会话与安全 Cookie。
7. 受控下游代理和 Mock 下游测试。
8. 基础认证、授权、退出、日志及指标。

## 7. 接入方要求

业务应用接入时应提供：

- 应用标识与环境名称。
- OIDC 客户端配置占位。
- 登出与回调地址。
- 下游服务注册信息。
- 角色与权限映射规则。
- 超时、重试和降级要求。
- 日志脱敏、审计和合规要求。
