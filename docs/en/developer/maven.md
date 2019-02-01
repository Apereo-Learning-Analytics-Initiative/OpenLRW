# Using Maven for development purposes

This following command will start the application on the port 9966 of your local machine.
``` 
$ mvn clean package spring-boot:run 
```

You can check to see if the application is running by accessing the info endpoint at http://localhost:9966/info

>  **⚠ Note** <br>
These instructions also assume that you are running MongoDB on the same machine as the LRW application (i.e., MongoDB is accessible at localhost:27017). If you need to configure the application to connect to a different MongoDB address see the [Spring-Boot & MongoDB configuration](http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html) properties.