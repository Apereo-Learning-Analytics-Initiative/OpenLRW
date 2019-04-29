# syntax=docker/dockerfile:experimental

FROM maven:3-jdk-8-alpine as build
MAINTAINER apereo.org

# Set Timezone to make sure SLF4J logs the correct timestamp
RUN apk --no-cache add \
 tzdata \
 && echo "${TIME_ZONE}" > /etc/timezone \
&& ln -sf /usr/share/zoneinfo/${TIME_ZONE} /etc/localtime

# Create a main directory and copy the maven pom file
RUN mkdir -p /build
COPY pom.xml /build/pom.xml

WORKDIR /build

# Download Maven dependencies
RUN mvn -B dependency:resolve dependency:resolve-plugins

COPY src /build/src

EXPOSE $SPRING_PORT

# Build application
RUN mvn clean package

ENV APP_HOME /app

# Create base app folder
RUN mkdir $APP_HOME

WORKDIR $APP_HOME

RUN mkdir config log

VOLUME $APP_HOME/log
VOLUME $APP_HOME/config

# Expose volumes where application.log and application.yml will be generated/replaced
VOLUME ["/tmp/openlrw", "/app/log"]

WORKDIR /build
RUN cp /build/target/*.jar ${APP_HOME}/openlrw.jar

ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://${MONGO_USERNAME}:${MONGO_PASSWORD}@${MONGO_HOST}/${MONGO_DB}","-Djava.security.egd=file:/dev/./urandom","-Dmatthews.security.jwt.tokenIssuer=http://localhost", "-Dmatthews.security.jwt.tokenSigningKey=${TOKEN_SIGNING_KEY}","-Djava.io.tmpdir=/tmp/openlrw","-Dmatthews.security.jwt.tokenExpirationTime=360","-Dmatthews.security.jwt.refreshTokenExpTime=240","-jar","/app/openlrw.jar"]