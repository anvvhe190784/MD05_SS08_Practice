# Thực hành: Pipeline CI/CD Multi-stage Build với Spring Boot & PostgreSQL

## Cấu trúc triển khai
- **`Dockerfile` (Multi-stage Build):**
  - Stage 1 (`builder`): `gradle:8.5-jdk17-alpine` biên dịch mã nguồn ra file `.jar`.
  - Stage 2 (`production`): `eclipse-temurin:17-jre-alpine` siêu nhẹ chỉ chứa JRE 17 và `app.jar`, mở cổng `8080`.
- **`.gitlab-ci.yml`:** Pipeline 3 stages tuần tự (`test` $\rightarrow$ `build_image` $\rightarrow$ `deploy`):
  - `test`: Khởi chạy `services: postgres:15-alpine` (`alias: postgres`), truyền `SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/testdb_ci` và chạy `gradle test`.
  - `build_image`: Đăng nhập Registry bằng `$CI_REGISTRY_USER` / `$CI_REGISTRY_PASSWORD`, build và push 2 tag (`$CI_COMMIT_SHORT_SHA` và `latest`).
  - `deploy`: Pull image vừa build và triển khai.
- **`.github/workflows/ci.yml`:** Pipeline GitHub Actions 3 stages tương đương (`Stage 1 - Test` $\rightarrow$ `Stage 2 - Build & Push Multi-stage Image` $\rightarrow$ `Stage 3 - Deploy & Verify`) sử dụng `secrets.GITHUB_TOKEN` đẩy lên GHCR.
