# Frequently Asked Questions

## Why my TimeZone is converted to UTC?
MongoDB [stores times in UTC by default](https://docs.mongodb.com/manual/reference/bson-types/#document-bson-type-date), and will convert any local time representations into this form.
Applications that must operate or report on some unmodified local time value may store the time zone alongside the UTC timestamp, and compute the original local time in their application logic.

#### For reconstructing the original local time you will have to apply the saved offset:
>  **⚠ Note** <br>
TimeZoneOffSet is saved in seconds.

```javascript
var record = db.getCollection("riskScore").findOne()
var localTime = new Date(record.dateTime.getTime() + record.timeZoneOffset)
```
