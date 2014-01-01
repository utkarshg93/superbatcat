#!/bin/bash

cat $1 > $1_2
sed 1d $1 > $1_1

nodes=`cat $1 | head -1 | awk -F" " '{print $1}'`
cat $1_1 > $1
echo $nodes
sudo ./eigenvector.r $1 $nodes >/dev/null


sudo chmod 777 $1try
sed 's/ /\n/g' $1try > $1_cluster
sudo chmod 777 $1_cluster
javac ConnectedChecker.java
java ConnectedChecker
sudo chmod 777 $1_connected
javac JSONConv.java
java JSONConv
sudo chmod 777 "enron_eigen_1.json"
sudo rm $1_1
sudo rm $1_2
sudo rm $1try
sudo mv "enron_eigen_1.json" "ClusteredGraph1d.json"
sudo cp 'ClusteredGraph1d.json' '/var/www'

