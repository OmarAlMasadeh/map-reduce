#!/bin/bash

counter=0
while [ $counter -l $1 ]
do
echo ojm123 | sudo -S docker run --name MapperMR$counter --net mapreducenet --ip 172.18.1.$counter mapredim:final sh Run.sh MapperMain;
((counter++))
done

