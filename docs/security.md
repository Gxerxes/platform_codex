# Palette BFF 安全设计

## 1. 安全目标

- 浏览器不接触 OAuth2 Access Token 或 Refresh Token。
- 认证、会话和令牌生命周期由服务端控制。
- 所有下游请求经过受控注册表，不允许客户端任意指定目标。
- 后端执行认证和业务授权。
- 外部输入、日志、审计和错误响应不泄露敏感数据。

## 2. 推荐认证模型

使用 OAuth 2.0 Authorization Code Flow + OIDC。Spring Security OAuth2 Client 优先承载流程和用户映射；企业 IdP 配置不写入仓库中的真实秘密。

## 3. Cookie 与 Session

建议：

- HttpOnly
- Secure
- SameSite=Lax，若跨站回调存在则经过 IdP 具体约束评估
- 合理的域名和 Path
- 服务端 Session
- 固定为配置化的有效期和空闲超时
- 登录前重新生成 Session ID，防止 Session Fixation
- OIDC 与本地注销均清理服务端状态

分布式部署优先评估 Spring Session Redis；若部署平台已提供更合适的会话实现，需通过 ADR确认。

## 4. CSRF 与 CORS

- 浏览器状态变更请求启用 CSRF 防护。
- CORS 限制明确来源，不使用通配符加凭据。
- 公共只读端点按白名单开放。
- 401 表示未认证或凭据失效；403 表示已认证但无权限。

## 5. Open Redirect 与下游代理

- 回调和成功重定向地址必须在受控白名单中。
- 禁止无条件返回开放重定向地址。
- 下游目标由配置注册表确定。
- 禁止客户端传入目标地址、协议或凭据覆盖参数。
- 下游地址只允许企业可信网络目标，并校验协议、主机和端口。

## 6. SSRF 防护

- 默认拒绝私网、环回、链路本地或特殊目的地址，除非企业白名单明确允许。
- 禁止重定向到不受控目标或重新解析域名绕过校验。
- HTTP 连接和重定向策略应受限并可观测。

## 7. 令牌安全

- Access Token 与 Refresh Token 默认服务端持有，不暴露给浏览器。
- 使用 Spring Security 及 Authorized Client Repository 管理令牌。
- 刷新应串行化或使用可靠的并发控制。
- 刷新失败触发明确的重新登录流程。
- 禁止在日志、指标标签、异常响应或审计中记录完整令牌。

## 8. 日志与审计

结构化日志至少包括：

- Correlation ID
- 环境与应用标识
- 请求类型与结果
- 用户或租户标识（如合规允许）
- 认证及授权事件
- 敏感字段脱敏状态

禁止记录密码、Session/CSRF 秘密、完整令牌、Authorization Header、Cookie 和高风险个人信息。

## 9. 威胁与验证

初始威胁模型包括：会话劫持、令牌泄露、CSRF、Open Redirect、SSRF、越权访问、重放、依赖漏洞、下游错误和密钥泄露。

后续各 Step 需对应安全测试、失败场景和并发场景。
