#!/bin/bash

counter=1
while [ $counter -le $1 ]
do
echo ojm123 | sudo -S docker run --name ReducerMR$counter --net mapreducenet --ip 172.18.2.$counter mapredim:final sh Run.sh ReducerMain;
((counter++))
done
