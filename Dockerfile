# from 컨테이너 이름:버전
FROM openjdk:17-alpine

# cd와 유사
WORKDIR /app

# 모든 파일을 현재 디렉토리에 복사
COPY . ./

# gradlew 빌드
RUN ./gradlew build -x test

# 도커 컨테이너 외부에서 8080 포트에 접근할 수 있도록 열어둠
EXPOSE 8080

# 빌드된 jar 파일을 자바로 실행
CMD ["java", "-jar", "/app/build/libs/gojisik-0.0.1-SNAPSHOT.jar"]
