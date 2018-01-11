#!/bin/sh
echo "
╭────────────────────────────────────────────────────────────────╮ 
│     OpenLRW     │              \033[36mINFORMATION\033[0m               ░▒▓▓▓▓│ 
├────────────────────────────────────────────────────────────────│ 
│     Checking version then building executable Java program \033[32m✓\033[0m   │ 
╰────────────────────────────────────────────────────────────────╯
"
cd `dirname $0`
git pull
cd src/openlrw
mvn -DskipTests=true clean install
cp target/openlrw-1.jar ../../lib/openlrw.jar