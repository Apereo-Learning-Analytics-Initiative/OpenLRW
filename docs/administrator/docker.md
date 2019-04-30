# Running OpenLRW with Docker

OpenLRW runs easily with [Docker](https://www.docker.com).

Two options are available, they both use the same Dockerfile.

## Bundle package (MongoDB + OpenLRW API)
> Data are not mapped on a volume, so do not use it for production!
### 1. Go to the bundle directory
```bash
$ cd docker/bundle/
```
### 2. Fill the settings file
```bash
$ cp .env.dist .env ; vi .env
```
### 3. Run Docker
```bash
$ docker-compose up -d
```


## Standalone API
### 1. Go to the standalone directory
```bash
$ cd docker/standalone/
```

### 2. Fill the settings file
```bash
$ cp .env.dist .env ; vi .env
```

### 3. Run Docker
```bash
$ docker-compose up -d
```
