#!/bin/bash 
./gremlin-groovy-2.1.0/bin/gremlin.sh -e getdata.groovy > boo.txt
python format.py
rm boo.txt

