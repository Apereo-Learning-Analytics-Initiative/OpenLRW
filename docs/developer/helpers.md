# Helpers


## Custom Health indicator

#### Set an indicator status for the API

Send an HTTP POST request to `/api/indicator/custom` with a Bearer Authorization as an header.

###### Example
```json
{
	"status": "MAINTENANCE" 
}
```

The status can have the value `UP`, `DOWN` and `MAINTENANCE` (check the `Indicator.java` file for more details).

It will return a `201 Created` code if the operation was successful.


### Retrieve this health indicator

Send an HTTP GET request to `/actuator/health`.

###### Response
```json
{  
   "status": "UP",
   "details": {  
      "indicator": {  
         "status": "MAINTENANCE"
      },
      "diskSpace":{  
         "status": "UP",
         "details": {  
            "total": 254538137600,
            "free": 104024383488,
            "threshold": 10485760
         }
      },
      "mongo": {  
         "status": "UP",
         "details": {  
            "version": "3.6.4"
         }
      }
   }
}
```
