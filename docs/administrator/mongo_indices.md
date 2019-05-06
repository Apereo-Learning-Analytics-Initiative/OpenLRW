# MongoDB: Indexing Strategies

<p align="center"> 
  <img src="https://upload.wikimedia.org/wikipedia/fr/thumb/4/45/MongoDB-Logo.svg/1280px-MongoDB-Logo.svg.png" alt="mongodb logo" height="75px">

> 



| :warning:   | For the fastest processing, ensure that your indexes fit entirely in RAM so that the system can avoid reading the index from disk. |
|:----:|:----|

## Why indexing my collections?

The concept of an index in MongoDB is the same as in relational databases. An index is generally a small structure that provides a faster way to access to documents. Without an index, the only way that MongoDB has to retrieve the documents is to do a collection scan: reading sequentially all the documents in the collection. This is exactly the same as the full scan table in Oracle MySQL. Another similarity to MySQL is that the indices in MongoDB have a structure based on the well known B-Tree. 

## Our recommendation

### MongoEnrollment
| Index  | Keys     |
|:------:|:--------:|
| user   | userSourcedId, orgId, tenantId  |
| class  | classSourcedId, orgId, tenantId |

<br>

### MongoEvent
|    Index    |                      Keys                         |
|:-----------:|:-------------------------------------------------:|
|  user_date  | userId, event.eventTime, organizationId, tenantId |
|  user_class | userId, classId, organizationId, tenantId         |
|  user       | userId, organizationId, tenantId                  |

<br>

### MongoLineItem
| Index | Keys |
|:------:|:--------:|
| class  | classSourcedId, orgId, tenantId |

<br>

### MongoResult
|    Index    |               Keys                 |
|:-----------:|:----------------------------------:|
|     user    | userSourcedId, orgId, tenantId     |
|  line_item  | lineitemSourcedId, orgId, tenantId |
| primary_key | result.sourcedId, orgId, tenantId  |
