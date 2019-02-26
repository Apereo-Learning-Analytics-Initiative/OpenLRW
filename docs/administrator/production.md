# Using a Production Setup

> These followings instructions are our recommendations for using OpenLRW in a production setup.

## Create the following directory structure.

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

## Add a specific user for running the application.

Create a user to run the application and make them owner of `/opt/openlrw/*` directories.
```bash
$ useradd -c "Boot User" boot
$ chown -R boot:boot /opt/openlrw
```

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
