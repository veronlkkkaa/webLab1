FROM maven:3.9.6-eclipse-temurin-17 AS build
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre
EXPOSE 1337
RUN mkdir /app
WORKDIR /app
COPY --from=build /usr/src/app/target/*.jar /app/app.jar
ENTRYPOINT ["java", "-DFCGI_PORT=1337", "-jar", "app.jar"]