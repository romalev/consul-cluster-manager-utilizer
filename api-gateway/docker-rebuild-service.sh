#!/bin/bash
# Given script re-builds the api gateway service within docker inf.
docker stop apigateway
docker rm apigateway
echo "apigateway container has been stopped and removed."
docker rmi -f io.vertx.gateway:1.0-SNAPSHOT
echo "apigateway image has been removed from local Docker repository. Building a new image ..."
./gradlew buildDocker
echo "New apigateway image has been created and placed within local Docker repository. You can now start the service by running the docker-restart-service.sh."
