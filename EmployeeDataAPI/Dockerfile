FROM openjdk:17-alpine
EXPOSE 8089
WORKDIR emsDataApp
COPY target/emsdata-0.0.1-SNAPSHOT.jar emsDataApp/emsdata.jar
CMD ["java","-jar","emsDataApp/emsdata.jar"]
