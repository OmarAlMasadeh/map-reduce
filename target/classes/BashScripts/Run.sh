#!/bin/bash

cd /home
mkdir MapReduce
cd MapReduce
git init
git pull "https://OmarAlMasadeh:omarj1st1@github.com/OmarAlMasadeh/map-reduce" master
mvn clean package
cd target
java -cp MapReduce-1.0-SNAPSHOT.jar $1
