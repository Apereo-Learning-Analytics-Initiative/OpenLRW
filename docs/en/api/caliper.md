# About Caliper Endpoints

OpenLRW provides two endpoints that support receipt of event data in IMS Caliper format.

### `/key/caliper`
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

### `/api/caliper` 
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