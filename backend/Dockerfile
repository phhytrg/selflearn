FROM eclipse-temurin:21
#WORKDIR /app
COPY /backend/target/selflearn.jar selflearn-1.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/backend/selflearn.jar"]