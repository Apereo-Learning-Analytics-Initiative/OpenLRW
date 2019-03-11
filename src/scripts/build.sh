#!/bin/sh
JAR_FILE=target/openlrw*.jar
DESTINATION_FILE=../../lib/openlrw.jar

cd `dirname $0`
cd src/OpenLRW
git pull
mvn -DskipTests=true clean install
cp $JAR_FILE $DESTINATION_FILE
