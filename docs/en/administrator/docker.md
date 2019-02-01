# Running OpenLRW with Docker

OpenLRW runs easily with [Docker](https://www.docker.com).


In the future this could be used for production but currently it's just set up for development

This is built with regular `docker-compose build` then `docker-compose up -d`. `docker-compose down` will bring this down. The redis data is stored in a local folder ./redis-data. Delete this folder to delete the data. 

An container running Mongo and this application will be started.

## Get your API

The API can be retrieved similarly to above

`docker run -it --link openlrw_mongo --net openlrw_net --rm mongo mongo --host mongo test -u root -p example --eval "db.mongoOrg.find().pretty()" | grep \"api`