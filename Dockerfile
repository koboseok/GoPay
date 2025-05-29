# base image
FROM openjdk:11-slim-stretch
# 8080 port open
EXPOSE 8080
# JAR_FILE의 이름을 아규먼트로 받음
ARG JAR_FILE
# 아규먼트로 받은 JAR_FILE을 내부 컨터이너에 app.jar로 저장
COPY ${JAR_FILE} app.jar
# 아래 명령어로 이미지를 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]