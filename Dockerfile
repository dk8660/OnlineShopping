# 기본 JDK 설정
FROM openjdk:23

# JAR 파일 복사
COPY build/libs/*.jar app.jar

# Thymeleaf 템플릿 및 정적 리소스 포함
COPY src/main/resources/templates /app/resources/templates
COPY src/main/resources/static /app/resources/static

# 실행 명령어
ENTRYPOINT ["java","-jar","/app.jar"]
LABEL authors="dk8660"
