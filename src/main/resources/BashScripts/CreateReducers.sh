#!/bin/bash

counter=0
while [ $counter -l $1 ]
do
echo ojm123 | sudo -S docker run --name ReducerMR$counter --net mapreducenet --ip 172.18.2.$counter mapredim:final sh Run.sh ReducerMain;
((counter++))
done
