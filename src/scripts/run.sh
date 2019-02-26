#!/bin/sh

cd `dirname $0`
APP_HOME="$PWD"
SETTINGS_PATH=$APP_HOME/conf/settings.properties
PORT_NUMBER=9966
PID_FILE=$APP_HOME/run/openlrw.pid
JAR_PATH=$APP_HOME/lib/openlrw.jar
cd $APP_HOME

##################
#      GUI       #
##################

function splash {
  printf "\033c"
  echo -e "
                ____                __   ___ _      __
               / __ \___  ___ ___  / /  / _ \ | /| / /
              / /_/ / _ \/ -_) _ \/ /__/ , _/ |/ |/ /
              \____/ .__/\__/_//_/____/_/|_||__/|__/
                  /_/
──────────────────── Standards-focused Learning Records Warehouse  "
}
function alert_usage {
echo -e "
╭────────────────────────────────────────────────────────────────╮
│     OpenLRW     │             \033[31mERROR MESSAGE\033[0m              ░▒▓▓▓▓│
├────────────────────────────────────────────────────────────────│
│          Wrong argument try these instead: start, stop \033[31m✘\033[0m       │
╰────────────────────────────────────────────────────────────────╯"
}

function alert_error_start {
echo -e "
╭────────────────────────────────────────────────────────────────╮
│     OpenLRW     │             \033[31mERROR MESSAGE\033[0m              ░▒▓▓▓▓│
├────────────────────────────────────────────────────────────────│
│  Can't start, an instance is already running! stop it first \033[31m✘\033[0m  │
╰────────────────────────────────────────────────────────────────╯"
}

function alert_start {
echo -e "
╭────────────────────────────────────────────────────────────────╮
│     OpenLRW     │              \033[36mINFORMATION\033[0m               ░▒▓▓▓▓│
├────────────────────────────────────────────────────────────────│
│                       Starting OpenLRW \033[32m✓\033[0m                       │
╰────────────────────────────────────────────────────────────────╯
"
}

function alert_error_stop {
echo -e "
╭────────────────────────────────────────────────────────────────╮
│     OpenLRW     │             \033[31mERROR MESSAGE\033[0m              ░▒▓▓▓▓│
├────────────────────────────────────────────────────────────────│
│              Cannot stop, OpenLRW was not running! \033[31m✘\033[0m           │
╰────────────────────────────────────────────────────────────────╯"
}

function alert_stop {
echo -e "
╭────────────────────────────────────────────────────────────────╮
│     OpenLRW     │              \033[36mINFORMATION\033[0m               ░▒▓▓▓▓│
├────────────────────────────────────────────────────────────────│
│                    Shutting down OpenLRW \033[32m✓\033[0m                     │
╰────────────────────────────────────────────────────────────────╯
"
}

function root_stop {
echo -e "
╭────────────────────────────────────────────────────────────────╮
│     OpenLRW     │             \033[31mERROR MESSAGE\033[0m              ░▒▓▓▓▓│
├────────────────────────────────────────────────────────────────│
│             Please don't run OpenLRW as root user \033[31m✘\033[0m            │
╰────────────────────────────────────────────────────────────────╯
"
}


######## SCRIPT ##########

splash

if (( $EUID == 0 )); then
    root_stop
    exit
fi

case "$1" in
  "start")

    if [ -f $PID_FILE ]; then
        PID_NUM=`cat $PID_FILE`
        # If PID_FILE exists then we check if that PID actually exists
        if ! kill -0 $PID_NUM; then
            rm -f $PID_FILE
        else
            alert_error_start
            exit 1
        fi
    fi

    alert_start

    java \
        -Djava.io.tmpdir=/tmp/openlrw \
        -Dserver.port=$PORT_NUMBER \
        -Dspring.config.location=$SETTINGS_PATH \
        -jar $JAR_PATH &
    echo $! > $PID_FILE
    ;;

  "stop")
    if [ ! -f $PID_FILE ]; then
      alert_error_stop
      exit 1
    fi
    alert_stop
    kill `cat $PID_FILE`
    rm -f $PID_FILE
    ;;
*)
  alert_usage
  ;;
esac
exit 0