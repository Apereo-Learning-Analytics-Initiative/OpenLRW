# Helpers


## Find OpenLRW API Key and Secret
The OpenLRW admin user interface is under development so you'll have to find your API key and secret by directly accessing your MongoDB instance. Follow the commands below to find your key and secret. The commands assume that you are able to access MongoDB via the command line and that you are using the default database name (if not, you would have changed this manually in openlrw).

```javascript
> mongo
> use test
> db.mongoOrg.find().pretty()

{
  "_id" : ObjectId("objid"),
  "_class" : "org.apereo.openlrw.oneroster.service.repository.MongoOrg",
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

## Count events

```javascript
> mongo
> use test
> db.mongoEvent.count()
17813
```

<br>

## Remove all events (testing only)

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

