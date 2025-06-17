# Dockerfile
FROM eclipse-temurin:17-jdk-alpine

# 建立工作目錄
WORKDIR /app

# 複製 jar 檔案（假設你使用 Maven 打包）
COPY target/productmgmt-0.0.1-SNAPSHOT.jar app.jar

# 執行 Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
