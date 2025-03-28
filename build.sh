#!/usr/bin/env bash
set -eux

java -version

JAR_FILE=target/uberjar/kata.jar
lein uberjar

java -XX:AOTMode=record -XX:AOTConfiguration=target/app.aotconf -jar $JAR_FILE

java -XX:AOTMode=create -XX:AOTConfiguration=target/app.aotconf -XX:AOTCache=target/app.aot --class-path $JAR_FILE

java -XX:AOTMode=on -XX:AOTCache=target/app.aot -jar $JAR_FILE
