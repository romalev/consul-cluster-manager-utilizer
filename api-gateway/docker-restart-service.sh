#!/bin/bash
# Given script re-starts the apigateway service within docker inf.
docker stop apigateway
docker rm apigateway
echo "apigateway container has been stopped and removed."
docker run -d -it --network host --name apigateway io.vertx.gateway:1.0-SNAPSHOT /bin/bash
echo "apigateway has been started!"
