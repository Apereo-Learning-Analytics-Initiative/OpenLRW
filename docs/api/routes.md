Apereo OpenLRW API - Documentation
==================================
The Open-source standards-focused Learning Records Warehouse.

### /api/academicsessions
---
##### ***POST***
**Summary:** postAcademicSession

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| academicSession | body | academicSession | Yes | [AcademicSession](#academicsession) |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/academicsessions/{academicSessionId}
---
##### ***GET***
**Summary:** getAcademicSession

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| academicSessionId | path | academicSessionId | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [AcademicSession](#academicsession) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/adminuser/create
---
##### ***POST***
**Summary:** Create a new admin user for tenant and organization

**Description:** Create a new admin user for tenant and organization

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| user | body | user | Yes | [UserDTO](#userdto) |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [AdminUser](#adminuser) |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/auth/token
---
##### ***GET***
**Summary:** refreshToken

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [JwtToken](#jwttoken) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/caliper
---
##### ***POST***
**Summary:** post

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| envelope | body | envelope | Yes | [Envelope](#envelope) |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/classes
---
##### ***GET***
**Summary:** getClass

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Collection«MongoClass»](#collection«mongoclass») |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

##### ***POST***
**Summary:** postClass

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| klass | body | klass | Yes | [Class](#class) |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/classes/mapping
---
##### ***POST***
**Summary:** postClassMapping

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| cm | body | cm | Yes | [ClassMapping](#classmapping) |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/classes/mapping/{externalClassId}
---
##### ***GET***
**Summary:** getClassMapping

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| externalClassId | path | externalClassId | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [ClassMapping](#classmapping) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/classes/{classId}
---
##### ***GET***
**Summary:** getClass

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| classId | path | classId | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Class](#class) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/classes/{classId}/enrollments
---
##### ***GET***
**Summary:** getEnrollmentsForClass

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| classId | path | classId | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Collection«Enrollment»](#collection«enrollment») |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

##### ***POST***
**Summary:** postEnrollment

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| classId | path | classId | Yes | string |
| enrollment | body | enrollment | Yes | [Enrollment](#enrollment) |
| check | query | check | No | boolean |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/classes/{classId}/events/stats
---
##### ***GET***
**Summary:** getEventStatisticsForClass

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| classId | path | classId | Yes | string |
| studentsOnly | query | studentsOnly | No | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [ClassEventStatistics](#classeventstatistics) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/classes/{classId}/events/user/{userId}
---
##### ***GET***
**Summary:** getEventForClassAndUser

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| classId | path | classId | Yes | string |
| userId | path | userId | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Collection«Event»](#collection«event») |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/classes/{classId}/lineitems
---
##### ***GET***
**Summary:** getLineItemsForClass

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| classId | path | classId | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Collection«LineItem»](#collection«lineitem») |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

##### ***POST***
**Summary:** postLineItem

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| lineItem | body | lineItem | Yes | [LineItem](#lineitem) |
| check | query | check | No | boolean |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/classes/{classId}/lineitems/{lineitemId}/results
---
##### ***GET***
**Summary:** getLineItemsResults

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| lineitemId | path | lineitemId | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Result](#result) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/classes/{classId}/results
---
##### ***GET***
**Summary:** getResultsForClass

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| classId | path | classId | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Collection«Result»](#collection«result») |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

##### ***POST***
**Summary:** postResult

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| classId | path | classId | Yes | string |
| result | body | result | Yes | [Result](#result) |
| check | query | check | No | boolean |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/classes/{classId}/results/user/{userId}
---
##### ***GET***
**Summary:** getResultsForClassAndUser

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| classId | path | classId | Yes | string |
| userId | path | userId | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Collection«Result»](#collection«result») |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/courses
---
##### ***POST***
**Summary:** postCourse

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| course | body | course | Yes | [Course](#course) |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/courses/{courseId}
---
##### ***GET***
**Summary:** getClass

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| courseId | path | courseId | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Course](#course) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/lineitems
---
##### ***GET***
**Summary:** getUsers

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Collection«MongoLineItem»](#collection«mongolineitem») |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

##### ***POST***
**Summary:** postLineItem

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| lineItem | body | lineItem | Yes | [LineItem](#lineitem) |
| check | query | check | No | boolean |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/orgs
---
##### ***POST***
**Summary:** post

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| org | body | org | Yes | [Org](#org) |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/orgs/{orgId}
---
##### ***GET***
**Summary:** getOne

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| orgId | path | orgId | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Org](#org) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/orgs/{orgId}/datasyncs
---
##### ***POST***
**Summary:** postDataSync

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| orgId | path | orgId | Yes | string |
| dataSync | body | dataSync | Yes | [DataSync](#datasync) |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/orgs/{orgId}/datasyncs/{syncType}
---
##### ***GET***
**Summary:** getLatestDataSync

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| orgId | path | orgId | Yes | string |
| syncType | path | syncType | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [DataSync](#datasync) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/risk
---
##### ***POST***
**Summary:** postLineItem

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| riskScore | body | riskScore | Yes | [RiskScore](#riskscore) |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/risk/{classSourcedId}/latest
---
##### ***GET***
**Summary:** getLatestRiskScoresForClass

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| classSourcedId | path | classSourcedId | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [ [RiskScore](#riskscore) ] |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/sync
---
##### ***POST***
**Summary:** postLineItem

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| dataSync | body | dataSync | Yes | [DataSync](#datasync) |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/sync/{syncType}/latest
---
##### ***GET***
**Summary:** getEventForClassAndUser

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| syncType | path | syncType | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [DataSync](#datasync) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/tenants
---
##### ***GET***
**Summary:** get

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Collection«Tenant»](#collection«tenant») |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

##### ***POST***
**Summary:** post

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| tenant | body | tenant | Yes | [Tenant](#tenant) |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/tenants/{tenantId}
---
##### ***GET***
**Summary:** getOne

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| tenantId | path | tenantId | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Tenant](#tenant) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/users
---
##### ***GET***
**Summary:** getUsers

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Collection«MongoUser»](#collection«mongouser») |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

##### ***POST***
**Summary:** post

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| user | body | user | Yes | [User](#user) |
| check | query | check | No | boolean |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/users/mapping
---
##### ***POST***
**Summary:** postUserMapping

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| um | body | um | Yes | [UserMapping](#usermapping) |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/users/mapping/{externalUserId}
---
##### ***GET***
**Summary:** getUserMapping

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| externalUserId | path | externalUserId | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [UserMapping](#usermapping) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/users/{userId}
---
##### ***GET***
**Summary:** getUser

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| userId | path | userId | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [User](#user) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

##### ***DELETE***
**Summary:** deleteUser

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| userId | path | userId | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [ResponseEntity](#responseentity) |
| 204 | No Content |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |

##### ***PATCH***
**Summary:** updateUser

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| userId | path | userId | Yes | string |
| data | body | data | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [ResponseEntity](#responseentity) |
| 204 | No Content |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |

### /api/users/{userId}/enrollments
---
##### ***GET***
**Summary:** getEnrollmentsForUser

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| userId | path | userId | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Collection«Enrollment»](#collection«enrollment») |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/users/{userId}/events
---
##### ***GET***
**Summary:** getEventsForUser

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| userId | path | userId | Yes | string |
| from | query | from | No | string |
| to | query | to | No | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Collection«Event»](#collection«event») |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /api/users/{userId}/results
---
##### ***GET***
**Summary:** getResultsForUser

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| details | query |  | No | object |
| authenticated | query |  | No | boolean |
| userId | path | userId | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [Result](#result) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /error
---
##### ***GET***
**Summary:** error

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

##### ***POST***
**Summary:** error

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

##### ***PUT***
**Summary:** error

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

##### ***DELETE***
**Summary:** error

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 204 | No Content |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |

##### ***OPTIONS***
**Summary:** error

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 204 | No Content |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |

##### ***PATCH***
**Summary:** error

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 204 | No Content |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |

### /info
---
##### ***GET***
**Summary:** invoke

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /info.json
---
##### ***GET***
**Summary:** invoke

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /key/caliper
---
##### ***POST***
**Summary:** post

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| Authorization | header | Authorization | Yes | string |
| envelope | body | envelope | Yes | [Envelope](#envelope) |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | object |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /xAPI/about
---
##### ***GET***
**Summary:** about

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [About](#about) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /xAPI/statements
---
##### ***GET***
**Summary:** getStatements

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| Authorization | header | Authorization | Yes | string |
| statementId | query | statementId | No | string |
| page | query | page | No | string |
| limit | query | limit | No | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [StatementResult](#statementresult) |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

##### ***POST***
**Summary:** postStatement

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| json | body | json | Yes | string |
| Authorization | header | Authorization | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [ string ] |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### /xAPI/statements/
---
##### ***POST***
**Summary:** postStatement

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| json | body | json | Yes | string |
| Authorization | header | Authorization | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | OK | [ string ] |
| 201 | Created |  |
| 401 | Unauthorized |  |
| 403 | Forbidden |  |
| 404 | Not Found |  |

### Models
---

### Entity  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| @context | string |  | No |
| @id | string |  | No |
| @type | string |  | No |
| actor | string |  | No |
| alignedLearningObjective | [ [LearningObject](#learningobject) ] |  | No |
| assignable | string |  | No |
| comment | string |  | No |
| currentTime | string |  | No |
| curveFactor | string |  | No |
| curvedTotalScore | double |  | No |
| dateCreated | dateTime |  | No |
| dateModified | dateTime |  | No |
| datePublished | dateTime |  | No |
| description | string |  | No |
| duration | string |  | No |
| extensions | object |  | No |
| extraCreditScore | double |  | No |
| isPartOf | [Entity](#entity) |  | No |
| keywords | [ string ] |  | No |
| name | string |  | No |
| normalScore | double |  | No |
| objectType | [ string ] |  | No |
| penaltyScore | double |  | No |
| scoredBy | [Agent](#agent) |  | No |
| totalScore | double |  | No |
| version | string |  | No |

### ClassMapping  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| classExternalId | string |  | No |
| classSourcedId | string |  | No |
| dateLastModified | dateTime |  | No |
| organizationId | string |  | No |
| tenantId | string |  | No |

### LearningObject  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| @context | string |  | No |
| @id | string |  | No |
| @type | string |  | No |
| dateCreated | dateTime |  | No |
| dateModified | dateTime |  | No |
| description | string |  | No |
| extensions | object |  | No |
| name | string |  | No |

### UserDTO  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | string | User Id | No |
| tenantId | string | Tenant Id | No |
| orgId | string | Organization Id | No |
| username | string | User Name | No |
| password | string | Password | No |
| emailAddress | string | Email Address | No |

### Collection«MongoLineItem»  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| Collection«MongoLineItem» | object |  |  |

### MongoLineItem  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| classSourcedId | string |  | No |
| id | string |  | No |
| lineItem | [LineItem](#lineitem) |  | No |
| orgId | string |  | No |
| tenantId | string |  | No |

### Collection«MongoUser»  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| Collection«MongoUser» | object |  |  |

### XApiVerb  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| display | object |  | No |
| id | string |  | No |

### Result  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| comment | string |  | No |
| date | dateTime |  | No |
| dateLastModified | dateTime |  | No |
| lineitem | [Link](#link) |  | No |
| metadata | object |  | No |
| resultstatus | string |  | No |
| score | double |  | No |
| sourcedId | string |  | No |
| status | string |  | No |
| student | [Link](#link) |  | No |

### Collection«Enrollment»  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| Collection«Enrollment» | object |  |  |

### MongoUser  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | string |  | No |
| orgId | string |  | No |
| tenantId | string |  | No |
| user | [User](#user) |  | No |

### Membership  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| @context | string |  | No |
| @id | string |  | No |
| @type | string |  | No |
| dateCreated | dateTime |  | No |
| dateModified | dateTime |  | No |
| description | string |  | No |
| extensions | object |  | No |
| member | string |  | No |
| name | string |  | No |
| organization | string |  | No |
| roles | [ string ] |  | No |
| status | string |  | No |

### Enrollment  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| class | [Class](#class) |  | No |
| metadata | object |  | No |
| primary | boolean |  | No |
| role | string |  | No |
| sourcedId | string |  | No |
| status | string |  | No |
| user | [User](#user) |  | No |

### Course  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| courseCode | string |  | No |
| grade | string |  | No |
| metadata | object |  | No |
| schoolYear | string |  | No |
| sourcedId | string |  | No |
| status | string |  | No |
| subjects | [ string ] |  | No |
| title | string |  | No |

### XApiResult  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| completion | boolean |  | No |
| duration | string |  | No |
| extensions | object |  | No |
| response | string |  | No |
| score | [XApiScore](#xapiscore) |  | No |
| success | boolean |  | No |

### Agent  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| @context | string |  | No |
| @id | string |  | No |
| @type | string |  | No |
| dateCreated | dateTime |  | No |
| dateModified | dateTime |  | No |
| description | string |  | No |
| extensions | object |  | No |
| name | string |  | No |

### About  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| extensions | object |  | No |
| version | string |  | No |

### XApiActor  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| account | [XApiAccount](#xapiaccount) |  | No |
| mbox | string |  | No |
| mbox_sha1sum | string |  | No |
| member | [ [XApiActor](#xapiactor) ] |  | No |
| name | string |  | No |
| objectType | string |  | No |
| openid | string |  | No |

### XApiObjectDefinition  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| choices | [ [XApiInteractionComponent](#xapiinteractioncomponent) ] |  | No |
| correctResponsesPattern | [ string ] |  | No |
| description | object |  | No |
| extensions | object |  | No |
| interactionType | string |  | No |
| moreInfo | string |  | No |
| name | object |  | No |
| scale | [ [XApiInteractionComponent](#xapiinteractioncomponent) ] |  | No |
| source | [ [XApiInteractionComponent](#xapiinteractioncomponent) ] |  | No |
| steps | [ [XApiInteractionComponent](#xapiinteractioncomponent) ] |  | No |
| target | [ [XApiInteractionComponent](#xapiinteractioncomponent) ] |  | No |
| type | string |  | No |

### Map«string,long»  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| Map«string,long» | object |  |  |

### XApiAccount  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| homePage | string |  | No |
| name | string |  | No |

### Collection«MongoClass»  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| Collection«MongoClass» | object |  |  |

### UserMapping  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| dateLastModified | dateTime |  | No |
| organizationId | string |  | No |
| tenantId | string |  | No |
| userExternalId | string |  | No |
| userSourcedId | string |  | No |

### Class  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| course | [Course](#course) |  | No |
| metadata | object |  | No |
| sourcedId | string |  | No |
| status | string |  | No |
| title | string |  | No |

### Link  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| href | string |  | No |
| sourcedId | string |  | No |
| type | string |  | No |

### Group  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| @context | string |  | No |
| @id | string |  | No |
| @type | string |  | No |
| courseNumber | string |  | No |
| dateCreated | dateTime |  | No |
| dateModified | dateTime |  | No |
| description | string |  | No |
| extensions | object |  | No |
| name | string |  | No |
| subOrganizationOf | [SubOrganizationOf](#suborganizationof) |  | No |

### StatementResult  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| statements | [ [Statement](#statement) ] |  | No |

### User  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| email | string |  | No |
| familyName | string |  | No |
| givenName | string |  | No |
| identifier | string |  | No |
| metadata | object |  | No |
| phone | string |  | No |
| role | string |  | No |
| sms | string |  | No |
| sourcedId | string |  | No |
| status | string |  | No |
| userId | string |  | No |
| username | string |  | No |

### Org  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| dateLastModified | dateTime |  | No |
| identifier | string |  | No |
| metadata | object |  | No |
| name | string |  | No |
| sourcedId | string |  | No |
| status | string |  | No |
| type | string |  | No |

### ModelAndView  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| empty | boolean |  | No |
| model | object |  | No |
| modelMap | object |  | No |
| reference | boolean |  | No |
| status | string |  | No |
| view | [View](#view) |  | No |
| viewName | string |  | No |

### XApiScore  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| max | double |  | No |
| min | double |  | No |
| raw | double |  | No |
| scaled | double |  | No |

### Envelope  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| data | [ [Event](#event) ] |  | No |
| sendTime | dateTime |  | No |
| sensor | string |  | No |

### XApiStatementRef  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | string |  | No |
| objectType | string |  | No |

### Tenant  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| description | string |  | No |
| id | string |  | No |
| metadata | object |  | No |
| name | string |  | No |

### XApiContext  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| contextActivities | [XApiContextActivities](#xapicontextactivities) |  | No |
| extensions | object |  | No |
| instructor | [XApiActor](#xapiactor) |  | No |
| language | string |  | No |
| platform | string |  | No |
| registration | string |  | No |
| revision | string |  | No |
| statement | [XApiStatementRef](#xapistatementref) |  | No |
| team | [XApiActor](#xapiactor) |  | No |

### Collection«Tenant»  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| Collection«Tenant» | object |  |  |

### AdminUser  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| createdAt | dateTime |  | No |
| emailAddress | string |  | No |
| id | string |  | No |
| lastModified | dateTime |  | No |
| metadata | object |  | No |
| orgId | string |  | No |
| password | string |  | No |
| superAdmin | boolean |  | No |
| tenantId | string |  | No |
| username | string |  | No |

### Collection«LineItem»  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| Collection«LineItem» | object |  |  |

### AcademicSession  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| academicSessionType | string |  | No |
| dateLastModified | dateTime |  | No |
| endDate | date |  | No |
| metadata | object |  | No |
| sourcedId | string |  | No |
| startDate | date |  | No |
| status | string |  | No |
| title | string |  | No |

### XApiInteractionComponent  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| description | object |  | No |
| id | string |  | No |

### Collection«Result»  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| Collection«Result» | object |  |  |

### Statement  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| actor | [XApiActor](#xapiactor) |  | No |
| authority | [XApiActor](#xapiactor) |  | No |
| context | [XApiContext](#xapicontext) |  | No |
| id | string |  | No |
| object | [XApiObject](#xapiobject) |  | No |
| result | [XApiResult](#xapiresult) |  | No |
| stored | string |  | No |
| timestamp | string |  | No |
| verb | [XApiVerb](#xapiverb) |  | No |
| version | string |  | No |

### ClassEventStatistics  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| classSourcedId | string |  | No |
| eventCountGroupedByDate | object |  | No |
| eventCountGroupedByDateAndStudent | object |  | No |
| metadata | object |  | No |
| totalEvents | integer |  | No |
| totalStudentEnrollments | integer |  | No |

### LineItemCategory  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| metadata | object |  | No |
| sourcedId | string |  | No |
| status | string |  | No |
| title | string |  | No |

### LineItem  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| assignDate | dateTime |  | No |
| category | [LineItemCategory](#lineitemcategory) |  | No |
| class | [Class](#class) |  | No |
| description | string |  | No |
| dueDate | dateTime |  | No |
| metadata | object |  | No |
| sourcedId | string |  | No |
| status | string |  | No |
| title | string |  | No |

### RiskScore  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| active | boolean |  | No |
| classSourcedId | string |  | No |
| dateTime | dateTime |  | No |
| id | string |  | No |
| modelType | string |  | No |
| orgId | string |  | No |
| score | string |  | No |
| tenantId | string |  | No |
| userSourcedId | string |  | No |

### SubOrganizationOf  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| @context | string |  | No |
| @id | string |  | No |
| @type | string |  | No |
| academicSession | string |  | No |
| category | string |  | No |
| courseNumber | string |  | No |
| dateCreated | dateTime |  | No |
| dateModified | dateTime |  | No |
| description | string |  | No |
| extensions | object |  | No |
| name | string |  | No |
| subOrganizationOf | [SubOrganizationOf](#suborganizationof) |  | No |

### View  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| contentType | string |  | No |

### Collection«Event»  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| Collection«Event» | object |  |  |

### JwtToken  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| token | string |  | No |

### ResponseEntity  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| body | object |  | No |
| statusCode | string |  | No |
| statusCodeValue | integer |  | No |

### MongoClass  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| classSourcedId | string |  | No |
| id | string |  | No |
| klass | [Class](#class) |  | No |
| orgId | string |  | No |
| tenantId | string |  | No |

### XApiContextActivities  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| category | [ [XApiObject](#xapiobject) ] |  | No |
| grouping | [ [XApiObject](#xapiobject) ] |  | No |
| other | [ [XApiObject](#xapiobject) ] |  | No |
| parent | [ [XApiObject](#xapiobject) ] |  | No |

### XApiObject  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| actor | [XApiActor](#xapiactor) |  | No |
| definition | [XApiObjectDefinition](#xapiobjectdefinition) |  | No |
| id | string |  | No |
| object | [XApiObject](#xapiobject) |  | No |
| objectType | string |  | No |
| verb | [XApiVerb](#xapiverb) |  | No |

### Event  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| @context | string |  | No |
| @type | string |  | No |
| action | string |  | No |
| actor | [Agent](#agent) |  | No |
| edApp | [Agent](#agent) |  | No |
| eventTime | dateTime |  | No |
| federatedSession | string |  | No |
| generated | [Entity](#entity) |  | No |
| group | [Group](#group) |  | No |
| id | string |  | No |
| membership | [Membership](#membership) |  | No |
| object | [Entity](#entity) |  | No |
| target | [Entity](#entity) |  | No |

### DataSync  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | string |  | No |
| orgId | string |  | No |
| syncDateTime | dateTime |  | No |
| syncStatus | string |  | No |
| syncType | string |  | No |
| tenantId | string |  | No |