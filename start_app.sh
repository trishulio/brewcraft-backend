#!/bin/bash

cd /var/app/dist
make source
source source.sh
nohup make start &