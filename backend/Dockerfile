FROM eclipse-temurin:21
#WORKDIR /backend
COPY /backend/target/selflearn-1.0.jar selflearn-1.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/selflearn-1.0.jar"]
RUN apt-get update && apt-get install -y iputils-ping