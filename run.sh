#!/bin/sh

##################
#      GUI       #
################## 

function splash {
  printf "\033c"
  echo "
                ____                __   ___ _      __
               / __ \___  ___ ___  / /  / _ \ | /| / /
              / /_/ / _ \/ -_) _ \/ /__/ , _/ |/ |/ / 
              \____/ .__/\__/_//_/____/_/|_||__/|__/  
                  /_/                                 
──────────────────── Standards-focused Learning Records Warehouse  "
}
function alert_usage {
echo "
╭────────────────────────────────────────────────────────────────╮ 
│     OpenLRW     │             \033[31mERROR MESSAGE\033[0m              ░▒▓▓▓▓│ 
├────────────────────────────────────────────────────────────────│ 
│      Wrong argument try these instead: start, stop, deploy     │ 
╰────────────────────────────────────────────────────────────────╯"
}

function alert_error_start {
echo "
╭────────────────────────────────────────────────────────────────╮ 
│     OpenLRW     │             \033[31mERROR MESSAGE\033[0m              ░▒▓▓▓▓│ 
├────────────────────────────────────────────────────────────────│ 
│  Can't start, an instance is already running! stop it first \033[31m✘\033[0m  │ 
╰────────────────────────────────────────────────────────────────╯"
}

function alert_start {
echo "
╭────────────────────────────────────────────────────────────────╮ 
│     OpenLRW     │              \033[36mINFORMATION\033[0m               ░▒▓▓▓▓│ 
├────────────────────────────────────────────────────────────────│ 
│                       Starting OpenLRW \033[32m✓\033[0m                       │ 
╰────────────────────────────────────────────────────────────────╯
"
}

function alert_error_stop {
echo "
╭────────────────────────────────────────────────────────────────╮ 
│     OpenLRW     │             \033[31mERROR MESSAGE\033[0m              ░▒▓▓▓▓│ 
├────────────────────────────────────────────────────────────────│ 
│              Cannot stop, OpenLRW was not running! \033[31m✘\033[0m           │ 
╰────────────────────────────────────────────────────────────────╯"
}

function alert_stop {
echo "
╭────────────────────────────────────────────────────────────────╮ 
│     OpenLRW     │              \033[36mINFORMATION\033[0m               ░▒▓▓▓▓│ 
├────────────────────────────────────────────────────────────────│ 
│                    Shutting down OpenLRW \033[32m✓\033[0m                     │ 
╰────────────────────────────────────────────────────────────────╯
"
}

function alert_deploy {
echo "
╭────────────────────────────────────────────────────────────────╮ 
│     OpenLRW     │              \033[36mINFORMATION\033[0m               ░▒▓▓▓▓│ 
├────────────────────────────────────────────────────────────────│ 
│                Deploying OpenLRW (Build + Run) \033[32m✓\033[0m               │ 
╰────────────────────────────────────────────────────────────────╯
"
}

######## SCRIPT ##########
function start {
  if [ -f $PID_FILE ]; then
      alert_error_start
      exit 1
  fi
  alert_start
  java \
      -Djava.io.tmpdir=/tmp/openlrw \
      -Dserver.port=9966 \
      -Dspring.config.location=$SETTINGS_PATH \
      -jar $JAR_PATH &
    echo $! > $PID_FILE
}

splash 

cd `dirname $0`
APP_HOME="$PWD"
PID_FILE=$APP_HOME/run/matthews.pid
JAR_PATH=$APP_HOME/lib/openlrw.jar
SETTINGS_PATH=$APP_HOME/conf/settings.properties
cd $APP_HOME

case "$1" in
  "start")
  
    start
    ;;
  "deploy")
    alert_deploy
    sh build.sh
    start
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
