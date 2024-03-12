#!/bin/bash 

docker run -d --network redisnetwork -p 6379:6379 --name redis-dev redis
