# Consul cluster manager utilizer #

**Introduction**
-
This is a simple project intended to show how to use [consul - based cluster manager](https://github.com/reactiverse/consul-cluster-manager) in the vert.x ecosystem. Given project contains two services: 

- **api-gateway**: exposes unsecured rest api (to be documented here) and talks to **phone-dictionary-service** by using event-bus.
- **phone-dictionary-service**: utilizes event-bus to accept incoming messages from **api-gateway** and handle them apppropriately.

**How to use**
- 
**api-gateway** and **phone-dictionary-service** contain two shell scripts: 
- ```./docker-rebuild-service.sh``` - to (re) build the docker image of the service.
- ```./docker-restart-service.sh``` - to (re) start the docker image of the service.

Execute ```./docker-run-dev-consul.sh``` to boot **consul agent** (in dev mode). 

