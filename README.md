# Product Management System(Spring Boot + JWT + Redis + MyBatis)

實作 OAuth2 登入、JWT 權限控管、Redis 快取、Swagger API 文件、ELK 日誌追蹤等功能。

## 技術棧

- Java 17, Spring Boot 3.x
- MyBatis + MySQL
- Spring Security + JWT + OAuth2
- Redis（快取 Token 與權限資訊）
- ELK Stack（可觀測性整合）
- Swagger UI（API 文件）
- Docker + docker-compose（開發部署環境）

## 系統模組

- ✅ 使用者管理（註冊、登入、權限）
- 📦 商品 CRUD（公司人員與商戶可見）
- 🔐 OAuth2 Google 登入
- 🧠 ELK 紀錄登入行為與操作紀錄
- 💾 Redis Token 快取與黑名單

## 📦 使用方式

```bash
# 啟動 Redis + MySQL + Elasticsearch
docker-compose up -d

# 啟動 Spring Boot 專案
./mvnw spring-boot:run
