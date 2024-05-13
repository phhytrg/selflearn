FROM eclipse-temurin:21
#WORKDIR /app
COPY /backend/target/selflearn.jar selflearn.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/backend/selflearn.jar"]