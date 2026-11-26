# Palette 开发者指南（草案）

## 前置要求

- JDK 21
- Git
- IDE（VS Code / IntelliJ IDEA）
- Docker（按需用于测试和部署）
- 企业制品仓库凭据不得提交到仓库

## Step 1 实施前的构建约定

Palette 后端使用 Gradle Kotlin DSL：

- `build.gradle.kts`
- `settings.gradle.kts`
- `gradle/libs.versions.toml`（演进为多模块或依赖集中管理时评估）
- `gradle/wrapper/`
- Java Toolchains

## 常用命令

```bash
./gradlew clean build
./gradlew test
./gradlew check
./gradlew bootRun
./gradlew bootJar
./gradlew dependencies
./gradlew dependencyInsight --dependency <dependency-name>
```

Windows：

```bat
gradlew.bat clean build
gradlew.bat bootRun
```

## 本地开发原则

- 未确认企业配置时使用 Mock IdP、Stub 和占位配置。
- 不提交真实密码、令牌、Cookie、证书或私钥。
- 外部仓库凭据通过环境变量或企业秘密管理提供。
- 测试、构建和运行使用同一 Java Toolchain 目标。

## 未初始化提示

当前仓库尚未包含 Gradle Wrapper、Spring Boot 源码或可运行应用。完成 Step 0 确认后再进入 Step 1 初始化。
