#!/usr/bin/env bash

#Possibility to set JVM options (https://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html)
export JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom" 

# Pass in some spring boot opts https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html

# These currently match the settings in docker-compose.yml
export SPRING_BOOT_OPTS="--spring.data.mongodb.host=openlrw_mongo --spring.data.mongodb.username=root --spring.data.mongodb.password=example --spring.data.mongodb.database=test"

#tail -f /dev/null
java ${JAVA_OPTS} -jar app.jar ${SPRING_BOOT_OPTS}
sleep 15
