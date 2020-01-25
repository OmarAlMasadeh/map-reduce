#!/bin/bash

echo ojm123 | sudo -S docker run --name ManagerMR --net mapreducenet --ip 172.18.0.2 mapredim:final sh Run.sh ManagerMain

