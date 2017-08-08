OpenLRW
============================
OpenLRW is the next evolution of OpenLRS. OpenLRW is a standards-focused learning records warehouse with support for event capture with xAPI and IMS Caliper.

*************************************************************************************

## Installation
### Prerequisites
* git
* MongoDB 2.6+ [(install instructions)](https://docs.mongodb.com/manual/installation/)
* Maven 3 [(download)](https://maven.apache.org/download.cgi)
* Java 8 [(jdk)](http://openjdk.java.net/)
### Running openlrw
#### Using Maven for development purposes
~~~~
mvn clean package spring-boot:run
~~~~

This will start the application on port 9966. You can check to see if the application is running by accessing the info endpoint at http://localhost:9966/info

These instructions also assume that you are running MongoDB on the same machine as the LRW application (i.e., MongoDB is accessible at localhost:27017). If you need to configure the application to connect to a different MongoDB address see the [Spring-Boot & MongoDB configuration](http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html) properties.

#### Using a more production-like setup
##### Directory Structure
Create the following directory structure.

* /opt/openlrw/
* /opt/openlrw/conf
* /opt/openlrw/lib
* /opt/openlrw/logs
* /opt/openlrw/run
* /opt/openlrw/src
* /opt/openlrw/build.sh
* /opt/openlrw/run.sh

###### Add a user to run the application
Create a user to run the application and make them owner of /opt/openlrw/* directories.
~~~~
useradd -c "Boot User" boot
chown -R boot:boot /opt/openlrw
~~~~
###### Checkout the source code
This is a one time operation. Note you'll need to update the git command below with your git username. 
~~~~
cd /opt/openlrw/src
git clone https://<!-- your bitbucket username-->@bitbucket.org/unicon/openlrw.git
~~~~
###### Build Script (build.sh)
From the /opt/openlrw directory execute the build script to create the LRW executable.
~~~~
#!/bin/sh
cd `dirname $0`
cd src/openlrw
git pull
mvn -DskipTests=true clean install
cp target/openlrw-1.jar ../../lib/openlrw.jar
~~~~
###### Run Script (run.sh)
From the /opt/openlrw directory execute the run script to start the application. Note you will need to update the script below with the appropriate MongoDB path. The application listens on port 9966.
~~~~
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
~~~~
###### Automated Start (e.g., AWS Auto-scale)
~~~~
#!/bin/bash
yum update -y
bash
cd /opt/openlrw
rm /opt/openlrw/run/*.pid
rm /opt/openlrw/*.log
rm /opt/openlrw/logs/*.log
su boot -c "sh build.sh"
su boot -c "sh run.sh start"
~~~~

### How to find your openlrw API Key and Secret
The openlrw admin user interface is under development so you'll have to find your API key and secret by directly accessing your MongoDB instance. Follow the commands below to find your key and secret. The commands assume that you are able to access MongoDB via the command line and that you are using the default database name (if not, you would have changed this manually in openlrw).

``````````````````
> mongo
> use test
> db.mongoOrg.find().pretty()

{
  "_id" : ObjectId("objid"),
  "_class" : "unicon.matthews.oneroster.service.repository.MongoOrg",
  "apiKey" : "abcdef",
  "apiSecret" : "123456",
  "tenantId" : "583ce4076f03bb1f88bee0ea",
  "org" : {
    "sourcedId" : "1f03f835-d992-4301-8e5c-5ad55e6489f5",
    "status" : "active",
    "metadata" : {
      "https://matthews/tenant" : "583ce4076f03bb1f88bee0ea"
    },
    "dateLastModified" : ISODate("2016-11-29T02:12:23.757Z"),
    "name" : "DEFAULT_ORG",
    "type" : "other"
  }
}
``````````````````

Find the values apiKey and apiSecret, those are the values you'll need to use to create a session with openlrw. In the example above the key is abcdef and the secret is 123456

Caliper
-------
OpenLRW provides two endpoints that support receipt of event data in IMS Caliper format.

### /key/caliper
This endpoint expects only to have your OpenLRW API key passed in the Authorization header. Here is an example:

`````````````````````````
POST /key/caliper HTTP/1.1
Host: localhost:9966
Content-Type: application/json
X-Requested-With: XMLHttpRequest
Authorization: YOUR-API-KEY
Cache-Control: no-cache

{ "sensor": "https://example.edu/sensor/001", "sendTime": "2015-09-15T11:05:01.000Z", "data": [ { "@context": "http://purl.imsglobal.org/ctx/caliper/v1/Context", "@type": "http://purl.imsglobal.org/caliper/v1/Event", "actor": { "@id": "https://example.edu/user/554433", "@type": "http://purl.imsglobal.org/caliper/v1/lis/Person" }, "action": "http://purl.imsglobal.org/vocab/caliper/v1/action#Viewed", "eventTime": "2015-09-15T10:15:00.000Z", "object": { "@id": "https://example.com/viewer/book/34843#epubcfi(/4/3)", "@type": "http://www.idpf.org/epub/vocab/structure/#volume" } } ] } 

`````````````````````````

### /api/caliper
This endpoint expects you to pass a valid bearer token in the Authorization header. To get a bearer token, first use the login endpoint with your api key and secret as follows:

`````````````
POST /api/auth/login HTTP/1.1
Host: localhost:9966
Content-Type: application/json
X-Requested-With: XMLHttpRequest
Cache-Control: no-cache

{ "username":"YOUR-API-KEY", "password":"YOUR-API-SECRET" }
`````````````````````````
Once you have the token, you can use it as follows:

`````````````
POST /api/caliper HTTP/1.1
Host: localhost:9966
Content-Type: application/json
X-Requested-With: XMLHttpRequest
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4NmMxMGY5Zi02MDUxLTQxNTEtYTNiYS01ODIwMmZhYWQ4ZjIiLCJzY29wZXMiOlsiUk9MRV9PUkdfQURNSU4iXSwidGVuYW50IjoiNTk2ZTM5N2RhOWY1NjQzYjFmNWFkMDA1IiwiaXNzIjoiaHR0cDovL2V4YW1wbGUuY29tIiwiaWF0IjoxNTAyMjE5MTY3LCJleHAiOjE1MDIyMzM1Njd9.6QvRpoNFe83ulOTIU3UJrAIbZLHCMx7izUwdtirrv5_-cWG5XYxVhi8b5uB5c3pYWBKld1w6y0vx7JPidECBMg
Cache-Control: no-cache

{ "sensor": "https://example.edu/sensor/001", "sendTime": "2015-09-15T11:05:01.000Z", "data": [ { "@context": "http://purl.imsglobal.org/ctx/caliper/v1/Context", "@type": "http://purl.imsglobal.org/caliper/v1/Event", "actor": { "@id": "https://example.edu/user/554433", "@type": "http://purl.imsglobal.org/caliper/v1/lis/Person" }, "action": "http://purl.imsglobal.org/vocab/caliper/v1/action#Viewed", "eventTime": "2015-09-15T10:15:00.000Z", "object": { "@id": "https://example.com/viewer/book/34843#epubcfi(/4/3)", "@type": "http://www.idpf.org/epub/vocab/structure/#volume" } } ] } 

`````````````


License
-------
ECL (a slightly less permissive Apache2)
http://opensource.org/licenses/ECL-2.0

Contact
-------
Send questions or comments to the mailing list: openlrs-user@apereo.org

