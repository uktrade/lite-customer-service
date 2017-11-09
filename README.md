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
No caching is currently implemented.

## JWT Authentication

Some of this services endpoints require authentication with a JWT token, information on the token can be found here [lite-dropwizard-common/jwt](https://github.com/uktrade/lite-dropwizard-common/tree/master/jwt).
