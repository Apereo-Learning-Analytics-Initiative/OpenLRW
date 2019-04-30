# Running OpenLRW with Docker

<p align="center">
    <img src='https://www.docker.com/sites/default/files/social/docker_facebook_share.png' alt='docker logo' height='200px'>
</p>


> OpenLRW runs easily with [Docker](https://www.docker.com) - Two options are available, they both use the same Dockerfile.

<br>

## - Bundle package (MongoDB + OpenLRW API)
> Data are not mapped on a volume, so do not use it for production!
#### 1. Go to the bundle directory
```bash
$ cd docker/bundle/
```
#### 2. Fill the settings file
```bash
$ cp .env.dist .env ; vi .env
```
#### 3. Run Docker
```bash
$ docker-compose up -d
```

<br>

## - Standalone API
#### 1. Go to the standalone directory
```bash
$ cd docker/standalone/
```

#### 2. Fill the settings file
```bash
$ cp .env.dist .env ; vi .env
```

#### 3. Run Docker
```bash
$ docker-compose up -d
```
