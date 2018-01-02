#!/bin/sh
cd `dirname $0`
APP_HOME="$PWD"
PID_FILE=$APP_HOME/run/matthews.pid
JAR_PATH=$APP_HOME/lib/openlrw.jar
SETTINGS_PATH=$APP_HOME/conf/settings.properties
cd $APP_HOME
case "$1" in
"start")
  if [ -f $PID_FILE ]; then
    exit 1
  fi
  java \
    -Djava.io.tmpdir=/tmp/openlrw \
    -Dserver.port=9988 \
    -Dspring.config.location=$SETTINGS_PATH \
    -jar $JAR_PATH &
  echo $! > $PID_FILE
  ;;
"stop")
  if [ ! -f $PID_FILE ]; then
    exit 1
  fi
  kill `cat $PID_FILE`
  rm -f $PID_FILE
  ;;
*)
  echo "Usage: $0 start|stop"
  ;;
esac
exit 0