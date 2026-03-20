# 多阶段构建
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY target/smart-park-app-*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

# 运行阶段
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# 安装必要的工具
RUN apk add --no-cache curl

# 创建非 root 用户
RUN addgroup -S smartpark && adduser -S smartpark -G smartpark

# 分层复制，提高构建缓存利用率
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./

# 更改文件所有者
RUN chown -R smartpark:smartpark /app

# 切换到非 root 用户
USER smartpark

# 健康检查
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

EXPOSE 8080

# JVM 参数优化
ENV JAVA_OPTS="-XX:+UseG1GC -XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=50.0"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS org.springframework.boot.loader.launch.JarLauncher"]
