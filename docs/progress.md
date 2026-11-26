# Palette 开发进度

## 当前阶段

**Step 4 — OAuth2 & OIDC Integration**

状态：已完成 OAuth2/OIDC 客户端配置骨架、用户与权限映射、登录成功失败处理及 Mock 测试，等待提交与下一阶段确认。

## Step 4 已完成

- 添加 `spring-boot-starter-oauth2-client`。
- 添加独立的 `oauth2` Profile 与企业 Keycloak 配置占位。
- 使用显式授权、令牌、用户信息和 JWK 端点，不依赖未确认 Issuer discovery。
- 保证真实凭据只通过环境变量或外部配置提供，不提交仓库。
- 添加 OIDC 登录成功与失败处理器。
- 添加 OIDC 用户信息映射和 Keycloak Realm Role 映射。
- 添加脱敏的 `/api/auth/session` 身份状态接口骨架。
- 添加 Mock OIDC 用户、Realm Role 与 OAuth2 Profile 上下文测试。

## Step 4 验证结果

- `./gradlew clean test`：成功。
- 默认 Profile 不加载真实身份源配置，可继续本地启动和测试。
- `oauth2` Profile 可加载显式 OAuth2/OIDC 注册占位配置。
- 未测试真实 Keycloak 登录：当前环境未提供 Realm、Client Secret 或可达身份源。
- Access Token 与 Refresh Token 未暴露给前端。

本 Step 未实现服务端 Redis Session、令牌刷新、并发刷新、OIDC Logout 或下游 Token Relay。

## 下一步

经确认后进入 **Step 5 — Session Management**。
