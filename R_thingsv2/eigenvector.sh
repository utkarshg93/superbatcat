#!/bin/bash

cat $1 > $1_2

sed 1d $1 > $1_1

nodes=`cat $1 | head -1 | awk -F" " '{print $1}'`
cat $1_1 > $1
#echo "nodes"
echo $nodes
#echo "calling r"
sudo ./eigenvector.r $1 $nodes >/dev/null

chmod 777 $1try


sed 's/ /\n/g' $1try > $1_cluster
chmod 777 $1_cluster
chmod 777 $1_2

#echo "going in v2"
chmod 777 $1_1
javac JSONConvV2.java 
java -Xmx2000M JSONConvV2 $1_cluster $1 >/dev/null

#exit
#
#echo "done with v2"
chmod -R 777 'labeling_enron_subgraphs/'
chmod 777 $1_cluster_l3
mv $1_cluster_l3 'labeling_enron_subgraphs/'
exit
./eigenvector.r `cat labeling_enron_subgraphs/largesubgraphs/list | head -1 | awk -F" " '{print $1,$2}' `>/dev/null
num=`cat labeling_enron_subgraphs/largesubgraphs/list | wc -l`
echo $num

if false

for((i=1;i<=num;++i))
do
echo $i
name=`cat labeling_enron_subgraphs/largesubgraphs/list | head -$i | tail -1| awk -F" " '{print $1}'`
cat labeling_enron_subgraphs/largesubgraphs/list | head -$i | tail -1 | awk -F" " '{print $1,$2}'
echo `cat labeling_enron_subgraphs/largesubgraphs/list | head -$i | tail -1 | awk -F" " '{print $1,$2}' `
./eigenvector.r `cat labeling_enron_subgraphs/largesubgraphs/list | head -$i | tail -1 | awk -F" " '{print $1,$2}' ` >/dev/null
sed 's/ /\n/g' $name"try" > $name"_cluster"  
rm `cat labeling_enron_subgraphs/largesubgraphs/list | head -$i | tail -1| awk -F" " '{print $1}'`try
done
./eigenvector.r "labeling_enron_subgraphs/"$1_cluster_l3 `cat 'labeling_enron_subgraphs/'$1_cluster_l3 | wc -l` >/dev/null
sed 's/ /\n/g' "labeling_enron_subgraphs/"$1_cluster_l3"try" > "labeling_enron_subgraphs/"$1_cluster_l3"_cluster"  
cp $1 'labeling_enron_subgraphs'
mv $1_cluster 'labeling_enron_subgraphs'
rm "labeling_enron_subgraphs/"$1_cluster_l3"try"
#echo "labeling_enron_subgraphs/$1_cluster_l3_cluster"	
#echo "labeling_enron_subgraphs/$1_cluster_l3"
javac topl.java
java topl $1_cluster_l3 $1_cluster_l3_cluster
chmod -R 777 'labeling_enron_subgraphs/'


javac JSONCOnvV3.java
java JSONCOnvV3 >/dev/null

sudo chmod 777 'enron.igraph.json'

sudo cp 'enron.igraph.json' '/var/www'
fi

sudo chmod 777 $1try

rm $1try
cat $1_2 > $1
rm $1_2
rm $1_1
rm $1
rm "eigenvector.r"
