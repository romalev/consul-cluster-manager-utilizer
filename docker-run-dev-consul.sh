#!/bin/bash
# Given script runs the consul with dev mode and net host networking enabled
docker run -d  --net=host --name=dev-consul -e CONSUL_BIND_INTERFACE=eth0 consul
echo "consul has been started in dev mode!"
