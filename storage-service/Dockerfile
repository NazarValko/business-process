FROM maven:3.8.7-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY common common/
COPY gateway-service gateway-service/
COPY personal-info-service personal-info-service/
COPY address-service address-service/
COPY contacts-service contacts-service/
COPY storage-service storage-service/
RUN mvn -f pom.xml clean install -DskipTests
RUN mvn -f storage-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
RUN mkdir -p /data
COPY --from=builder /app/storage-service/target/storage-service-0.0.1-SNAPSHOT.jar app.jar
VOLUME /data
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]