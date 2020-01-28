#!/bin/bash

counter=0
while [ $counter -lt $1 ]
do
echo ojm123 | sudo -S docker run --name MapperMR$counter --net mapreducenet --ip 172.18.1.$counter mapredim:final sh Run.sh MapperMain;
((counter++))
done

counter=0
while [ $counter -lt $2 ]
do
echo ojm123 | sudo -S docker run --name ReducerMR$counter --net mapreducenet --ip 172.18.2.$counter mapredim:final sh Run.sh ReducerMain;
((counter++))
done

