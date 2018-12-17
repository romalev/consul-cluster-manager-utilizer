#!/bin/bash
# Given script re-starts the phonedirectory service within docker inf.
docker stop phonedirectory
docker rm phonedirectory
echo "phonedirectory container has been stopped and removed."
docker run -d -it --network host --name phonedirectory io.vertx.phone.directory:1.0-SNAPSHOT /bin/bash
echo "phonedirectory has been started!"
