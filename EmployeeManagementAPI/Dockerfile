FROM openjdk:17-alpine
EXPOSE 8080
WORKDIR emsApp
COPY target/ems-0.0.1-SNAPSHOT.jar emsApp/ems.jar
CMD ["java","-jar","emsApp/ems.jar"]
