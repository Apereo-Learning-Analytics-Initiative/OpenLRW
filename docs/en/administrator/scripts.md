# Scripts

Useful scripts for a production setup.

## Build Script (build.sh)
From the `/opt/openlrw/` directory execute the build script to create the LRW executable.

```bash
#!/bin/sh
cd `dirname $0`
cd src/OpenLRW
git pull
mvn -DskipTests=true clean install
cp target/openlrw-1.0.1.ALPHA.jar ../../lib/openlrw.jar
```

## Run Script (run.sh)
From the `/opt/openlrw/` directory execute the run script to start the application. Note you will need to update the script below with the appropriate MongoDB path. The application listens on port 9966.

```bash
#!/bin/sh
cd `dirname $0`
APP_HOME="$PWD"
PID_FILE=$APP_HOME/run/openlrw.pid
JAR_PATH=$APP_HOME/lib/openlrw.jar

cd $APP_HOME

case "$1" in
"start")
  if [ -f $PID_FILE ]; then
    exit 1
  fi
  java \
    -Dlogging.path=/opt/openlrw/logs/ \
    -Dspring.data.mongodb.uri=<!-- mongodb uri --> \
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
```

