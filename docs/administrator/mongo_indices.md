# MongoDB: Indexing Strategies

<p align="center"> 
  <img src="https://upload.wikimedia.org/wikipedia/fr/thumb/4/45/MongoDB-Logo.svg/1280px-MongoDB-Logo.svg.png" alt="mongodb logo" height="75px">

> For the fastest processing, ensure that your indexes fit entirely in RAM so that the system can avoid reading the index from disk.


## MongoEnrollment
| Index  | Keys     |
|:------:|:--------:|
| user   | userSourcedId, orgId, tenantId  |
| class  | classSourcedId, orgId, tenantId |


## MongoEvent
|    Index    |                      Keys                         |
|:-----------:|:-------------------------------------------------:|
|  user_date  | userId, event.eventTime, organizationId, tenantId |
|  user_class | userId, classId, organizationId, tenantId         |
|  user       | userId, organizationId, tenantId                  |

## MongoLineItem
| Index | Keys |
|:------:|:--------:|
| class  | classSourcedId, orgId, tenantId |


## MongoResult
|    Index    |               Keys                 |
|:-----------:|:----------------------------------:|
|     user    | userSourcedId, orgId, tenantId     |
|  line_item  | lineitemSourcedId, orgId, tenantId |
| primary_key | result.sourcedId, orgId, tenantId  |
