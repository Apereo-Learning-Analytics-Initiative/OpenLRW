# OpenLRW

> OpenLRW is the next evolution of OpenLRS. OpenLRW is a standards-focused learning records warehouse implementing OneRoster with support for event capture with xAPI and IMS Caliper.

# Table of Contents
* [I. Requirements](#i-requirements)
* [II. Installation](#ii-installation)
  * [A. Get OpenLRW](#a-clone-the-project)
  * [B. Configuration](#b-configure-the-application-settings)
  * [C. Create a user account](#c-create-a-user-account)
  * [D. Run the application](#d-run-the-application)
* [III. Tips](#iii-tips)
  * [A. Find OpenLRW API Key and Secret](#a-find-openlrw-api-key-and-secret)
  * [B. Log in](#b-log-in)
  * [C. Count events](#c-count-events)
  * [D. Remove all events (testing only)](#d-remove-all-events-testing-only)
  * [E. Caliper routes](#e-caliper-routes)
* [IV. Possible Issues](#iv-possible-issues)
* [V. Resources](#v-resources)
* [VI. License](#vi-license)
* [VII. Contact](#vii-contact)

## I. Requirements
- [Git](https://git-scm.com/)
- [Java Development Kit 8](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html)
- [Maven 3](https://maven.apache.org/download.cgi)
- [MongoDB 2.6+](https://docs.mongodb.com/manual/installation/)


## II. Installation
### A. Clone the project
> OpenLRW should be placed in the /opt/ directory.

` $ git clone https://github.com/Apereo-Learning-Analytics-Initiative/OpenLRW.git `

### B. Configure the application settings
Create `settings.properties` from its template then fill its fields.

```bash
$ cp conf/settings.properties.dist conf/settings.properties 
```

### C. Create a user account
Create a user to run the application and make them owner of /opt/OpenLRW/* directories.

```bash
$ useradd -c "Boot User" boot
$ chown -R boot:boot /opt/OpenLRW
```

### D. Run the application
#### 1. Development setup
    
```bash
$ cd src/openlrw/ 
$ mvn clean package spring-boot:run
```

This will start the application on port 9966. You can check to see if the application is running by accessing the info endpoint at http://localhost:9966/info

These instructions also assume that you are running MongoDB on the same machine as the LRW application (i.e., MongoDB is accessible at localhost:27017). If you need to configure the application to connect to a different MongoDB address see the [Spring-Boot & MongoDB configuration](http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html) properties.

#### 2. Production setup
`build.sh` will create the executable while `run.sh` will start the application by using the `conf/settings.properties` configuration file.
```bash
$ sh build.sh
$ sh run.sh
```
#### 3. Automated Start (e.g. AWS Auto-scale)

```bash
#!/bin/bash
yum update -y
bash
cd /opt/openlrw
rm /opt/openlrw/run/*.pid
rm /opt/openlrw/*.log
rm /opt/openlrw/logs/*.log
su boot -c "sh build.sh"
su boot -c "sh run.sh start"
```

## III. Tips
### A. Find OpenLRW API Key and Secret
The OpenLRW admin user interface is under development so you'll have to find your API key and secret by directly accessing your MongoDB instance. Follow the commands below to find your key and secret. The commands assume that you are able to access MongoDB via the command line and that you are using the default database name (if not, you would have changed this manually in openlrw).

```javascript
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
```

Find the values apiKey and apiSecret, those are the values you'll need to use to create a session with openlrw. In the example above the key is abcdef and the secret is 123456

### B. Log in

> Curl
```bash
curl -X POST -H "X-Requested-With: XMLHttpRequest" -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d '{  
    "username": USERNAME",
    "password": PORT
}' "http://localhost:9966/api/auth/login"
```

> Python 
```python 
import requests
import json

response = requests.post("http://localhost:9966/api/auth/login",
                         headers={'X-Requested-With': 'XMLHttpRequest'},
                         json={"username": USERNAME, "password": PASSWORD})
response = response.json()
token = response['token']
``` 

### C. Count events

```javascript
> mongo
> use test
> db.mongoEvent.count()
17813
```
### D. Remove all events (testing only)

```javascript
> mongo
> use test
> db.mongoEvent.count()
17813
> db.mongoEvent.remove({})
WriteResult({ "nRemoved" : 17813 })
> db.mongoEvent.count()
0
```


### E. Caliper routes
> OpenLRW provides two endpoints that support receipt of event data in IMS Caliper format.

#### `/key/caliper`
This endpoint expects only to have your OpenLRW API key passed in the Authorization header. Here is an example:

```javascript
POST /key/caliper HTTP/1.1
Host: localhost:9966
Content-Type: application/json
X-Requested-With: XMLHttpRequest
Authorization: YOUR-API-KEY
Cache-Control: no-cache

{ "sensor": "https://example.edu/sensor/001", "sendTime": "2015-09-15T11:05:01.000Z", "data": [ { "@context": "http://purl.imsglobal.org/ctx/caliper/v1/Context", "@type": "http://purl.imsglobal.org/caliper/v1/Event", "actor": { "@id": "https://example.edu/user/554433", "@type": "http://purl.imsglobal.org/caliper/v1/lis/Person" }, "action": "http://purl.imsglobal.org/vocab/caliper/v1/action#Viewed", "eventTime": "2015-09-15T10:15:00.000Z", "object": { "@id": "https://example.com/viewer/book/34843#epubcfi(/4/3)", "@type": "http://www.idpf.org/epub/vocab/structure/#volume" } } ] } 
```

#### `/api/caliper` 
This endpoint expects you to pass a valid bearer token in the Authorization header. To get a bearer token, first use the login endpoint with your api key and secret as follows:

```javascript
POST /api/auth/login HTTP/1.1
Host: localhost:9966
Content-Type: application/json
X-Requested-With: XMLHttpRequest
Cache-Control: no-cache

{ "username":"YOUR-API-KEY", "password":"YOUR-API-SECRET" }
```

Once you have the token, you can use it as follows:

```javascript
POST /api/caliper HTTP/1.1
Host: localhost:9966
Content-Type: application/json
X-Requested-With: XMLHttpRequest
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4NmMxMGY5Zi02MDUxLTQxNTEtYTNiYS01ODIwMmZhYWQ4ZjIiLCJzY29wZXMiOlsiUk9MRV9PUkdfQURNSU4iXSwidGVuYW50IjoiNTk2ZTM5N2RhOWY1NjQzYjFmNWFkMDA1IiwiaXNzIjoiaHR0cDovL2V4YW1wbGUuY29tIiwiaWF0IjoxNTAyMjE5MTY3LCJleHAiOjE1MDIyMzM1Njd9.6QvRpoNFe83ulOTIU3UJrAIbZLHCMx7izUwdtirrv5_-cWG5XYxVhi8b5uB5c3pYWBKld1w6y0vx7JPidECBMg
Cache-Control: no-cache

{ "sensor": "https://example.edu/sensor/001", "sendTime": "2015-09-15T11:05:01.000Z", "data": [ { "@context": "http://purl.imsglobal.org/ctx/caliper/v1/Context", "@type": "http://purl.imsglobal.org/caliper/v1/Event", "actor": { "@id": "https://example.edu/user/554433", "@type": "http://purl.imsglobal.org/caliper/v1/lis/Person" }, "action": "http://purl.imsglobal.org/vocab/caliper/v1/action#Viewed", "eventTime": "2015-09-15T10:15:00.000Z", "object": { "@id": "https://example.com/viewer/book/34843#epubcfi(/4/3)", "@type": "http://www.idpf.org/epub/vocab/structure/#volume" } } ] } 

```

## IV. Possible Issues
You might experience very long startup times on some cloud hosted servers.  This might be because of a shortage
of entropy as a result of no keyboard, or mouse:

http://www.issihosts.com/haveged/

To rectify, install the above software (Ubuntu instructions):

```bash
apt-get install haveged
update-rc.d haveged defaults
apt-get install rng-tools
cat /dev/random | rngtest -c 1000
```

## V. Resources
- [Caliper Analytics](https://www.imsglobal.org/activity/caliper)
- [OneRoster](https://www.imsglobal.org/activity/onerosterlis)
- [xAPI](https://experienceapi.com/)
- [Swagger](http://imshackathonlrw.cloudlrs.com/swagger-ui.html#/)

## VI. License
OpenLRW is made available under the terms of the [Educational Community License, Version 2.0 (ECL-2.0)](https://opensource.org/licenses/ECL-2.0).

## VII. Contact 
Send questions or comments to the mailing list: openlrs-user@apereo.org

