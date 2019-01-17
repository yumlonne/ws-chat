#!/bin/bash

export MODE=develop
export CMD=~jetty:start

docker run -it -e MODE -e CMD -p 80:8080 -v `pwd`:/root/project/ws-proxy ws-chat:1.0
