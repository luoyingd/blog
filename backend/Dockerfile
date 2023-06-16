FROM adoptopenjdk/openjdk11:latest
MAINTAINER luoyingd <luoyingd1997@gmail.com>
VOLUME /tmp
ADD api/target/api-1.0-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 9090