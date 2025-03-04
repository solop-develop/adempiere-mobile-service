# ADempiere Mobile Service

This project was created for add support to Mobile Service with ADempiere as server.

## Requirements

The ADempiere template is a service to expose ADempiere as gRPC service with a little functionality of ADempiere:
 
 - Run Login
 
Since the ADempiere dependency is vital for this project is high recommended that the you are sure that of project [adempiere-jwt-token](https://github.com/adempiere/adempiere-jwt-token) is installed and the setup is runned in ADempiere Database.

## Run it from Gradle

```Shell
gradle run --args="resources/env.yaml"
```


## Some Notes

For Token validation is used [JWT](https://www.viralpatel.net/java-create-validate-jwt-token/)

## Run with Docker

```Shell
docker pull openls/adempiere-mobile-service:latest
```

### Minimal Docker Requirements
To use this Docker image you must have your Docker engine version greater than or equal to 3.0.

### Environment variables
 * `DB_TYPE`: Database Type (Supported `Oracle` and `PostgreSQL`). Default `PostgreSQL`.
 * `DB_HOST`: Hostname for data base server. Default: `localhost`.
 * `DB_PORT`: Port used by data base server. Default: `5432`.
 * `DB_NAME`: Database name that Adempiere-Backend will use to connect with the database. Default: `adempiere`.
 * `DB_USER`: Database user that Adempiere-Backend will use to connect with the database. Default: `adempiere`.
 * `DB_PASSWORD`: Database password that Adempiere-Backend will use to connect with the database. Default: `adempiere`.
 * `DB_PASSWORD_FILE`: Database password that Adempiere-Backend will use to connect with the database. This overrides `DB_PASSWORD` but if not defined no secrets will be implemented in the password but instead the value of the environment variable will be used.
 * `IDLE_TIMEOUT`: It sets the maximum time a connection can sit around without being used before it gets closed to free up resources. Default: `300`.
 * `MINIMUM_IDLE`: It sets the minimum number of connections that should be kept open and ready to use, even if they're not currently being used. This helps improve performance by reducing the time it takes to get a connection. Default: `1`.
 * `MAXIMUM_POOL_SIZE`: It sets the maximum number of connections that can be open at the same time. This helps prevent the pool from getting too big and using up too much memory. Default: `10`.
 * `CONNECTION_TIMEOUT`: it sets the maximum time HikariCP will wait to get a connection from the pool before giving up and throwing an error. Default: `5000`.
 * `MAXIMUM_LIFETIME`: It sets the maximum amount of time a connection can stay open in the pool before it's automatically closed. This helps keep the pool clean and prevents problems. Default: `6000`.
 * `KEEPALIVE_TIME`: It sets a test query that HikariCP will run on connections to make sure they're still working properly. Default: `360000`.
 * `CONNECTION_TEST_QUERY`: It sets how often HikariCP will check if a connection is still working properly. This helps prevent problems with connections that might become inactive. Default: `SELECT 1`
 * `SERVER_PORT`: Port to access Adempiere-Backend from outside of the container. Default: `50059`.
 * `SERVER_LOG_LEVEL`: Log Level. Default: `WARNING`.
 * `TZ`: (Time Zone) Indicates the time zone to set in the nginx-based container, the default value is `America/Caracas` (UTC -4:00).
 * `SYSTEM_LOGO_URL`: Logo of the main image of the system, shown in the login screen.

You can download the last image from docker hub, just run the follow command:

```Shell
docker run -d -p 50062:50062 --name adempiere-mobile-service -e DB_HOST="localhost" -e DB_PORT=5432 -e DB_NAME="adempiere" -e DB_USER="adempiere" -e DB_PASSWORD="adempiere" openls/adempiere-mobile-service:latest
```

See all images [here](https://hub.docker.com/r/openls/adempiere-mobile-service)

## Run with Docker Compose

You can also run it with `docker compose` for develop enviroment. Note that this is a easy way for start the service with PostgreSQL and template.

### Requirements

- [Docker Compose v2.16.0 or later](https://docs.docker.com/compose/install/linux/)

```Shell
docker compose version
Docker Compose version v2.16.0
```

## Run it

Just go to `docker-compose` folder and run it

```Shell
cd docker-compose
```

```Shell
docker compose up
```

### Some Variables

You can change variables editing the `.env` file. Note that this file have a minimal example.


### Where is the service?

- ZK service by default is launched at [[here](http://localhost:8080)](http://localhost:8080/webui/timeout.zul)
- The Mobile service is launched at 50059 port

**Note:** For Postman collection you can import the file located at `resources/Mobile_Postman_Collection.json`

## ADempiere Core Changes

This project depends of [adempiere-mobile-changes](https://github.com/adempiere/adempiere-mobile-changes), please refer to project for core changes.


## Supported Endpoints

- `/login`
- `/check-token`
- `/logout`
- `/adempiere/organizations`
- `/app/base-settings`
- `/app/home-screen`
- `/appoinment/get-list`
- `/dashboard/statistics`
- `/upcoming-events/get-list`
- `/user/attendance/get-checkin-checkout-status`


## What else?
- You can login with the follow data:
  - User: `app`
  - Password: `app`
- The seed database have all related to Mobile Application:
  - User
  - Role
  - Test Roles
  - Dictionary Changes

**Some utils**

```Shell
pg_dump -U adempiere -Fc -d "adempiere" > /tmp/seed.backup --verbose
docker cp adempiere-mobile-service.adempiere.database:/tmp/seed.backup postgresql/
```
