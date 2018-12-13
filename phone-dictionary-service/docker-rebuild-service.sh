#!/bin/bash
# Given script re-builds the phone dictionary service within docker inf.
docker stop phonedictionary
docker rm phonedictionary
echo "phonedictionary container has been stopped and removed."
docker rmi -f io.vertx.phone.directory:1.0-SNAPSHOT
echo "phonedictionary image has been removed from local Docker repository. Building a new image ..."
./gradlew buildDocker
echo "New phonedictionary image has been created and placed within local Docker repository. You can now start the service by running the docker-restart-service.sh."
