#!/bin/sh

docker run --network pgnetwork --network-alias pgadmin --name pgadmin -e "PGADMIN_DEFAULT_EMAIL=cuong.ntt@domain.com" -e "PGADMIN_DEFAULT_PASSWORD=cuong.ntt" -p 2345:80 -d dpage/pgadmin4

