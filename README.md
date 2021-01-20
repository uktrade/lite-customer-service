# Customer service

Proxy service for SPIRE customer data. A "customer" is typically an organisation like a limited company, but may represent
an individual or a sole trader. Each customer has one or more "sites" (addresses) which they export goods from.

Several endpoints require a SPIRE web user account ID. This is used by the SPIRE permissions model to determine which
customers and sites a user has access to. Consumers must take care about what information they reveal to a user, as it
may be possible to leak confidential or sensitive information about a company if this privilege checking is not correctly
implemented elsewhere.

## Running locally

* `git clone git@github.com:uktrade/lite-customer-service.git`
* `cd lite-customer-service`
* `cp src/main/resources/sample-config.yaml src/main/resources/config.yaml`
* `./gradlew run`

You will need to update `config.yaml` with passwords for the mocking service from Vault.

You will also need to run a local Redis, e.g. `docker run -p 6379:6379 --name my-redis -d redis:latest`. If your Redis
is not running with default settings, you will need to update connection details in `config.yaml`.

## Endpoint summary

* `/create-customer` (`CustomerCreateResource`)

Creates a new customer record.

* `/customers` (`CustomerResource`)

Retrieves customer information based on a customer ID.  Requires JWT.

* `/user-customers` (`CustomerResource`)

Gets customer information for all customers a user has access to. Requires JWT (if `userId` is provided it must match the JWT `sub`).

* `/search-customers` (`CustomerResource`)

Searches for customers by postcode, EORI number or Companies House registered number. Requires JWT.

* `/customer-sites` (`SiteCreateResource`)

Creates a new site record for a given customer.

* `/sites/{siteId}` (`SiteResource`)

Retrieves site information based on a customer ID. Requires JWT.

* `/user-sites` (`SiteResource`)

Gets site information for all sites a user has access to within a given customer. Requires JWT (if `userId` is provided it must match the JWT `sub`).

* `/customer-admins` (`UserResource`)

Gets a list of users who are administrators for the given customer. Requires JWT.

* `/user-roles` (`UserResource`)

Allows a users privilege level ("role") to be updated for a given site. Requires JWT.

## SPIRE integration

All endpoints are proxies for SPIRE SOAP endpoints. `SpireClient` implementations are used to send the SOAP messages.
Some endpoint results are cached using [lite-dropwizard-common/redis-cache](https://github.com/uktrade/lite-dropwizard-common/tree/master/redis-cache).

## JWT Authentication

Some of this services endpoints require authentication with a JWT token, information on the token can be found here [lite-dropwizard-common/jwt](https://github.com/uktrade/lite-dropwizard-common/tree/master/jwt).

### GDS PaaS Deployment

This repo contains a pre-packed deployment file, lite-customer-service-xxxx.jar.  This can be used to deploy this service manually from the CF cli.  Using the following command:

* cf push [app_name] -p lite-customer-service-xxxx.jar

For this application to work the following dependencies need to be met:

* Bound PG DB (all services share the same backend db)
* Bound REDIS
* Env VARs will need to be set

### Archive state

This repo is now archived. If you need to deploy this application, you can find a copy of the DB and VARs in the DIT AWS account.
