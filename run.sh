#!/bin/bash

export MODE=develop
export CMD=${1:-~jetty:start}

docker run -it -e MODE -e CMD -p 8080:8080 -v `pwd`:/root/project/ws-chat ws-chat:1.0
