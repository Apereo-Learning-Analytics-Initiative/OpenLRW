# Frequently Asked Questions

## Why my TimeZone is converted to UTC?
MongoDB [stores times in UTC by default](https://docs.mongodb.com/manual/reference/bson-types/#document-bson-type-date), and will convert any local time representations into this form.
Applications that must operate or report on some unmodified local time value may store the time zone alongside the UTC timestamp, and compute the original local time in their application logic.

#### Example for reconstructing the original local time by applying the saved offset:
From the MongoDB shell

```javascript
var record = db.getCollection("riskScore").findOne()
print(new Date(record.dateTime.getTime() + (record.timeZoneOffset)) )
```
