#!/bin/sh

# Clean, Build, Exec
./gradlew modules:jdbc-demos:clean :modules:jdbc-demos:build :modules:jdbc-demos:runWithExec

## Build
#./gradlew modules:jdbc-demos:clean :src:jdbc-demos:build
#
## Set CLASSPATH
## Note: Place *.jar dependencies in ./libs directory
#CLASSPATH="./src/jdbc-demos/build/classes/java/main/"
#for f in "./libs/"*.jar; do
#    CLASSPATH="${CLASSPATH}:$f";
#done
#
#export CLASSPATH
## echo $CLASSPATH
#
## Run
#java jdbc.dev.demos.DatabaseConnector
