#!/bin/sh

cd `dirname $0`
APP_HOME="$PWD"
SETTINGS_FILE=$APP_HOME/settings.properties.dist
RUN_SCRIPT=$APP_HOME/run.sh
BUILD_SCRIPT=$APP_HOME/build.sh


function success {
echo -e "
╭────────────────────────────────────────────────────────────────╮
│     OpenLRW     │              \033[36mINFORMATION\033[0m               ░▒▓▓▓▓│
├────────────────────────────────────────────────────────────────│
│               The installation was successful \033[32m✓\033[0m                │
╰────────────────────────────────────────────────────────────────╯
"
}


function main {

  cd $APP_HOME
  cd ../../
  OPEN_LRW_PATH="$PWD"
  cd ..
  echo "Creating files and directories..."
  mkdir lib
  mkdir conf
  mkdir logs
  mkdir run
  mkdir src
  cp $SETTINGS_FILE conf/settings.properties
  cp $RUN_SCRIPT  run.sh
  cp $BUILD_SCRIPT build.sh
  mv $OPEN_LRW_PATH src
  
  success
  echo "You can now use run.sh and build.sh (read the documentations on Github to know how to use them)"
  echo "Do not forget to edit the config/settings.properties file before running them"
  echo # move to a new line
  exit 1

}


echo -e "
                ____                __   ___ _      __
               / __ \___  ___ ___  / /  / _ \ | /| / /
              / /_/ / _ \/ -_) _ \/ /__/ , _/ |/ |/ /
              \____/ .__/\__/_//_/____/_/|_||__/|__/
                  /_/
──────────────────── Standards-focused Learning Records Warehouse  "


if (( $EUID == 0 )); then
    echo "You should not use this command as root user, OpenLRW recommends a dedicated user for using this application"
    read -p "Do you want to continue? (y/n) " -n 1 -r
    echo    # move to a new line
    echo    # move to a new line
    if [[ $REPLY =~ ^[Yy]$ ]]
    then
        main
    else
        exit 0
    fi
fi

main

exit 0
