Matthews
============================
Matthews is the next evolution of OpenLRS. Matthews is a standards-focused learning records warehouse with support for event capture with xAPI and IMS Caliper.

*************************************************************************************

## Installation
### Prerequisites
* git
* MongoDB 2.6+ [(install instructions)](https://docs.mongodb.com/manual/installation/)
* Maven 3 [(download)](https://maven.apache.org/download.cgi)
* Java 8 [(jdk)](http://openjdk.java.net/)
### Running Matthews
#### Using Maven for development purposes
~~~~
mvn clean package spring-boot:run
~~~~

This will start the application on port 9966. You can check to see if the application is running by accessing the info endpoint at http://localhost:9966/info

These instructions also assume that you are running MongoDB on the same machine as the LRW application (i.e., MongoDB is accessible at localhost:27017). If you need to configure the application to connect to a different MongoDB address see the [Spring-Boot & MongoDB configuration](http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html) properties.

#### Using a more production-like setup
##### Directory Structure
Create the following directory structure.

* /opt/matthews/
* /opt/matthews/conf
* /opt/matthews/lib
* /opt/matthews/logs
* /opt/matthews/run
* /opt/matthews/src
* /opt/matthews/build.sh
* /opt/matthews/run.sh

###### Add a user to run the application
Create a user to run the application and make them owner of /opt/matthews/* directories.
~~~~
useradd -c "Boot User" boot
chown -R boot:boot /opt/matthews
~~~~
###### Checkout the source code
This is a one time operation. Note you'll need to update the git command below with your git username. 
~~~~
cd /opt/matthews/src
git clone https://<!-- your bitbucket username-->@bitbucket.org/unicon/matthews.git
~~~~
###### Build Script (build.sh)
From the /opt/matthews directory execute the build script to create the LRW executable.
~~~~
#!/bin/sh
#!/bin/sh
cd `dirname $0`
cd src/matthews
git pull
mvn -DskipTests=true clean install
cp target/matthews-1.jar ../../lib/matthews.jar
~~~~
###### Run Script (run.sh)
From the /opt/matthews directory execute the run script to start the application. Note you will need to update the script below with the appropriate MongoDB path. The application listens on port 9966.
~~~~
#!/bin/sh
cd `dirname $0`
APP_HOME="$PWD"
PID_FILE=$APP_HOME/run/matthews.pid
JAR_PATH=$APP_HOME/lib/matthews.jar

cd $APP_HOME

case "$1" in
"start")
  if [ -f $PID_FILE ]; then
    exit 1
  fi
  java \
    -Dlogging.path=/opt/matthews/logs/ \
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
~~~~
###### Automated Start (e.g., AWS Auto-scale)
~~~~
#!/bin/bash
yum update -y
bash
cd /opt/matthews
rm /opt/matthews/run/*.pid
rm /opt/matthews/*.log
rm /opt/matthews/logs/*.log
su boot -c "sh build.sh"
su boot -c "sh run.sh start"
~~~~

License
-------
ECL (a slightly less permissive Apache2)
http://opensource.org/licenses/ECL-2.0

Contact
-------
Send questions or comments to the mailing list: openlrs-user@apereo.org

