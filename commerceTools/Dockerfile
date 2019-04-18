FROM java:8
VOLUME /tmp
WORKDIR /usr/app
ADD target/stockHandlingTask-0.4.0.jar /usr/app/app.jar
ENTRYPOINT ["java", "-jar", "/usr/app/app.jar", " -web -webAllowOthers -tcp -tcpAllowOthers -browser"]