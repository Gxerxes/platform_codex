# Palette 开发路线图

## 工作流约束

- Backend First。
- Step-by-Step、Incremental、MVP-first。
- Gradle Wrapper + Kotlin DSL。
- 未经确认不进入下一 Step。
- 未执行验证不得标记为通过。

## Step 0：需求与架构设计

状态：完成设计草案，等待确认。

产物：

- 需求与范围
- 架构与系统上下文
- 安全设计
- API 合约草案
- 目录结构建议
- ADR 草案
- MVP 和关键决策清单

不编写实现代码。

## Step 1：项目初始化

待确认后执行：

- 初始化 Spring Boot 3 单模块应用。
- 配置 Gradle Wrapper 与 Kotlin DSL。
- 配置 Java Toolchains。
- 建立基础包结构与 application profiles。
- 配置日志和 Actuator。
- 实现 Hello API。
- 添加基础测试。
- 验证 clean build、test、check、bootRun 和 bootJar。

## 后续步骤

| Step | 内容 | 主要验收 |
|---|---|---|
| 2 | 配置、异常、关联 ID、日志 | 配置校验、标准错误、请求追踪 |
| 3 | Spring Security 基础 | 端点保护、CSRF/CORS、安全头、401/403 |
| 4 | OAuth2/OIDC | Mock IdP 登录、用户映射、失败处理 |
| 5 | Session 管理 | Secure Cookie、超时、固定保护、状态 API |
| 6 | 令牌生命周期 | 令牌管理、刷新、并发刷新、失败处理 |
| 7 | 登出生命周期 | 本地/OIDC 登出、清理、前端状态契约 |
| 8 | 下游集成 | 注册表、受控代理、Token Relay、超时 |
| 9 | 授权 | 角色权限、端点与方法级授权 |
| 10 | Reliability | 超时、重试、熔断、降级 |
| 11 | Observability | 日志、指标、Trace、审计、脱敏 |
| 12 | 集成与安全测试 | Mock IdP、Mock 下游、并发及失败场景 |
| 13 | Production Readiness | Docker、CI/CD、扫描、部署文档 |
| 14 | 复用与接入 | Template/Starter评估、参考应用、接入指南 |
| 15 | 最终评审 | 架构、安全、性能、契约和交付验收 |

## 版本策略

初始阶段使用语义化版本，不在项目初始化中引入复杂发布流程。企业制品仓库、分支策略和发布规范需在生产就绪阶段确认。
