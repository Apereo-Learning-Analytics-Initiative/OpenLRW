# Frequently Asked Questions

## Why my TimeZone is converted to UTC?
MongoDB [stores times in UTC by default](https://docs.mongodb.com/manual/reference/bson-types/#document-bson-type-date), and will convert any local time representations into this form.
Applications that must operate or report on some unmodified local time value may store the time zone alongside the UTC timestamp, and compute the original local time in their application logic.

#### Example
In the MongoDB shell, you can store both the current date and the current client’s offset from UTC.

```javascript
var now = new Date();
db.data.save( { date: now, offset: now.getTimezoneOffset() } );
```
You can reconstruct the original local time by applying the saved offset:
```javascript
var record = db.data.findOne();
var localNow = new Date( record.date.getTime() -  ( record.offset * 60000 ) );
```
