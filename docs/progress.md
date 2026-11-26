# Palette 开发进度

## 当前阶段

**Step 3 — Spring Security Foundation**

状态：已完成安全过滤链、401/403、CSRF、CORS 与安全响应头实现，等待提交与下一阶段确认。

## Step 3 已完成

- 添加 Spring Security 依赖及 Security Test 支持。
- 配置 Spring Security 6 `SecurityFilterChain`。
- 将 Hello 和 Actuator health 设为公开端点。
- 将受保护与管理员端点分别要求认证和 `ROLE_ADMIN`。
- 实现 RFC 7807 风格 `401` 与 `403` 响应入口。
- 配置 CORS 来源、方法、Header 和凭据策略。
- 启用 CSRF 防护并仅豁免只读健康探针。
- 配置 Frame、Content-Type 和安全响应头。
- 添加公开、受保护、管理员、CSRF、CORS 和安全头测试。

## Step 3 验证结果

- `./gradlew clean build test check bootJar`：成功。
- `./gradlew bootRun`：启动成功。
- `/api/hello`：公开访问，返回 `200`。
- `/actuator/health`：公开访问，返回 `UP`。
- `/api/protected/session` 未认证访问：返回 `401 application/problem+json`。
- 管理员与普通认证测试：分别验证通过和拒绝。
- 安全头和关联 ID 可在运行时观察。

当前使用 HTTP Basic 与 Spring Boot 自动 In-Memory 用户，仅用于 Step 3 验证，不是生产认证方案。

## 下一步

经确认后进入 **Step 4 — OAuth2 & OIDC Integration**，接入 Mock IdP 或配置占位的企业 Keycloak。
