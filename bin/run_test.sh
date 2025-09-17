#!/bin/sh

./gradlew -x test modules:libs-test:runWithExec

./gradlew modules:libs-test:test

#./gradlew modules:libs-test:test --tests libs.test.TestGuava