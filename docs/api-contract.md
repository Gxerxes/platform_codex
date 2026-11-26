# Palette BFF API 合约（Step 0 草案）

> 以下为 MVP 候选接口，企业真实接口与安全策略确认前不得视为最终契约。

## Hello API

### GET /api/hello

用途：验证应用启动和基础请求链路。

成功响应：`200 OK`

```json
{
  "message": "Hello, Palette"
}
```

## 健康端点

由 Spring Boot Actuator 提供：

- `/actuator/health`
- `/actuator/health/liveness`
- `/actuator/health/readiness`

生产暴露范围需在安全配置中确认。

## 标准错误响应（Step 2 已实现）

服务端使用 `application/problem+json`，并携带关联 ID：

```json
{
  "type": "about:blank",
  "title": "Not Found",
  "status": 404,
  "detail": "The requested resource was not found.",
  "instance": "/api/not-found",
  "correlationId": "请求提供或系统生成的关联 ID"
}
```

每个响应也会包含 `X-Correlation-ID` 响应头。客户端提供的安全关联 ID 会原样保留，否则服务端生成 UUID。

## 候选认证接口

- `GET /oauth2/authorization/{registration-id}`：发起登录，由 Spring Security 原生实现。
- `GET /login/oauth2/code/{registration-id}`：OIDC 回调，由 Spring Security 原生处理。
- `POST /api/auth/logout`：本地及 OIDC 退出候选接口。
- `GET /api/auth/session`：返回脱敏的认证状态，不暴露令牌。

## 候选受控代理接口

形式待确认，原则上禁止任意目标参数：

```text
GET /api/downstream/{registered-service}/{path}
```

实现前必须确认：路径、方法、Header、限流、超时、重试和错误映射。

## 状态码约定

- `401 Unauthorized`：未登录、Session 失效或令牌失效。
- `403 Forbidden`：已认证但角色或权限不足。
- `502 Bad Gateway`：受控下游不可用且无可接受降级。
- `504 Gateway Timeout`：受控下游超时。
