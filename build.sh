#!/bin/sh
cd `dirname $0`
git pull
cd src/openlrw
mvn -DskipTests=true clean install
cp target/matthews-1.jar ../../lib/openlrw.jar