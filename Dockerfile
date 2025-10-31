# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package

# Run stage
FROM eclipse-temurin:17-jre
ENV PORT=8080
ENV APP_DATA_DIR=/data
WORKDIR /app
COPY --from=build /workspace/target/mintike-issues-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
VOLUME ["/data"]
ENTRYPOINT ["java","-jar","/app/app.jar"]


