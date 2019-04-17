#!/usr/bin/env bash

# JVM options (https://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html)
# https://www.baeldung.com/jvm-parameters

export JAR_PATH="/app/openlrw.jar"
export TMP_DIR="/tmp/openlrw"
export TOKEN_EXP_TIME=240
export TOKEN_REFRESH_EXP_TIME=360

java \
    -Djava.io.tmpdir=${TMP_DIR} \
    -Dspring.data.mongodb.uri=${MONGO_SETTINGS} \
    -Dmatthews.security.jwt.tokenSigningKey=${TOKEN_SIGNING_KEY} \
    -Dmatthews.security.jwt.tokenIssuer=${TOKEN_ISSUER} \
    -Dmatthews.security.jwt.tokenExpirationTime=${TOKEN_EXP_TIME} \
    -Dmatthews.security.jwt.refreshTokenExpTime=${TOKEN_REFRESH_EXP_TIME} -jar ${JAR_PATH}