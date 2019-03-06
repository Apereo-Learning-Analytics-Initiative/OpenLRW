# Using a Production Setup

> These followings instructions are our recommendations for using OpenLRW in a production setup.


## Add a specific user for running the application.
Create a user to have rights on the directories
```bash
$ useradd -c "Boot User" boot
```

## Clone the repository
```bash
$ mkdir /opt/openlrw/
$ cd /opt/openlrw/
$ git clone https://github.com/Apereo-Learning-Analytics-Initiative/OpenLRW
```

## Execute the installation script
```bash
$ sh OpenLRW/src/scripts/install.sh
```

### You will now have this following structure
```
/opt/
└── openlrw/
    ├── conf/
    ├── lib/
    ├── logs/
    ├── run/
    ├── src/
    ├── build.sh
    └── run.sh
```


## Build the application
From the `/opt/openlrw/` directory execute the `build.sh` script to create the LRW executable.

## Run the Application
From the `/opt/openlrw/` directory execute the `run.sh` script to start the application. The application listens on port 9966 (default).

> **⚠ Note** <br>
Note you will need to update the `/opt/openlrw/conf/settings.properties` file with the appropriate MongoDB path


## Automated Start
This following command can be useful for services like AWS Auto-Scale

```bash
#!/bin/bash
yum update -y
bash
cd /opt/openlrw
rm /opt/openlrw/run/*.pid
rm /opt/openlrw/*.log
rm /opt/openlrw/logs/*.log
su boot -c "sh build.sh"
su boot -c "sh run.sh start"
```


## Possible Issues

You might experience very long startup times on some cloud hosted servers.  This might be because of a shortage
of entropy as a result of no keyboard, or mouse:

http://www.issihosts.com/haveged/

To rectify, install the above software (Ubuntu instructions):

```bash
apt-get install haveged
update-rc.d haveged defaults
apt-get install rng-tools
cat /dev/random | rngtest -c 1000
```

If you are having troubles with certain caliper or xapi payloads, you can turn on http request loggging with the following:

```
logging.level.org.springframework.web.filter.CommonsRequestLoggingFilter: DEBUG
```
