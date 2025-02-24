FROM openjdk:23-jdk

ENV JAVA_OPTS=""

WORKDIR /app

COPY . .

RUN chmod +x ./mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/aki-backend-0.0.1-SNAPSHOT.jar"]
