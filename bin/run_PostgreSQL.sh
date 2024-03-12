#!/bin/sh

# create network
# docker network create -d bridge pgnetwork

# run
docker run -d --network pgnetwork --network-alias postgresql -it -e POSTGRES_USER=cuong.ntt -e POSTGRES_PASSWORD=cuong.ntt -p 5432:5432 -v "$(pwd)"/data:/var/lib/postgresql/data --name postgresql postgres
