# Frequently Asked Questions

## Why my TimeZone is converted to UTC?
MongoDB [stores times in UTC by default](https://docs.mongodb.com/manual/reference/bson-types/#document-bson-type-date), and will convert any local time representations into this form.
Applications that must operate or report on some unmodified local time value may store the time zone alongside the UTC timestamp, and compute the original local time in their application logic.

#### For reconstructing the original local time you will have to apply the saved offset:
>  **⚠ Note** <br>
TimeZoneOffSet is saved in seconds.

```javascript
var record = db.getCollection("mongoRisk").findOne()
var localTime = new Date(record.dateTime.getTime() + record.timeZoneOffset)
```

## I don't find all the entities (eg. OneRoster models), where are these classes?

The main entities are seperated in the bellow repositories, they are imported through the Maven `pom.xml` file
- [xAPI and Caliper models (Events)](https://github.com/Apereo-Learning-Analytics-Initiative/lai-event)
- [OneRoster and other models](https://github.com/Apereo-Learning-Analytics-Initiative/lai-model)



## Why xAPI statements are converted to IMS Caliper

Since we are using the OneRoster standard for our data model, Caliper is a natural fit as the default event standard (both are designed by IMS Global). Use cases for the OpenLRW API are 100% education focused and IMS Caliper is an education specific standard (xAPI is more general purpose). Also many of the systems we integrate with (LMS such as Moodle, Canvas, etc) chose Caliper as their event standard.
