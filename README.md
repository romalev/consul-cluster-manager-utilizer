# Consul cluster manager utilizer #

**Introduction**
-
This is a simple project intended to show how to use [consul - based cluster manager](https://github.com/romalev/vertx-consul-cluster-manage) in the vert.x ecosystem. Given project contains two services: 

- **api-gateway**: exposes unsecured rest api (to be documented here) and talks to **phone-dictionary-service** by using event-bus.
- **phone-dictionary-service**: utilizes event-bus to accept incoming messages from **api-gateway** and handle them apppropriately.

**How to use**

-to be done.

