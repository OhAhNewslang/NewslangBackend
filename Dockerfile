# 첫 번째 단계: Gradle 빌드 수행
# 도커 엔진 환경위에 Gradle을 설치한다.
FROM gradle:latest AS builder

# 도커 엔진 DOS 환경의 /app Diretory를 작업 Diretory로 지정한다.
WORKDIR /app

# 프로젝트 파일의 build.gradle, settings.gradle을
# 작업 Diretory로 복사한다.
COPY build.gradle settings.gradle ./

# 프로젝트의 /src 하위의 소스코드를 /app/src 하위로 복사한다.
COPY src/ src/

# 복사해온 파일들을 기반으로 도커환경 위에서 Jar파일로 빌드한다.
RUN gradle build
# ※ 빌드에 필요한 파일들만 복사해
# ※ 도커 엔진 환경으로 가져가서 빌드하기 때문에
# ※ gradle clean 작업이 필요없다.

# 두 번째 단계: 도커 이미지 빌드
# 도커 엔진 환경 위에 JDK를 설치한다.
FROM eclipse-temurin:17 AS runtime

# Gradle 이용해 JAR파일을 생성한 /app Diretory를 작업 Diretory로 지정한다.
WORKDIR /app

# /app Diretory하위에 Jar파일의 이름을 * 와일드카드로 찾아서 복사해
# app.jar로 만들어 놓는다.
COPY --from=builder /app/build/libs/newslang.jar app.jar

# 복사 해둔 Jar파일을 도커 이미지로 빌드한다.
CMD ["java", "-jar", "app.jar"]