rm -r clusters

#copy DataSet

cp $1 "enron_undir"

javac UndirToDir.java
java UndirToDir >/dev/null

rm -r "enron_undir"

#Largest Connected Component
set -- "enron_undir_edgelist" "${@:1:2}"  "${@:4}"


cat $1 > $1_2

sed 1d $1 > $1_1

nodes=`cat $1 | head -1 | awk -F" " '{print $1}'`
cat $1_1 > $1
#echo "nodes"
echo $nodes
#echo "calling r"
sudo ./LargestConnected.r $1 $nodes >/dev/null

sed 's/ /\n/g' $1try > $1_cluster
chmod 777 $1_cluster
chmod 777 $1_2
sudo chmod 777 $1try
#echo "going in v2"
chmod 777 $1_1

#Largest Connected Component edgelist
javac JSONConvV2.java 
java -Xmx2000M JSONConvV2 $1_cluster $1 >/dev/null

rm "enron"*

cp labeling_enron_subgraphs/largesubgraphs/cluster1/subgraph1 "enron"
echo `cat labeling_enron_subgraphs/largesubgraphs/cluster1/map1 | wc -l`
echo `cat labeling_enron_subgraphs/largesubgraphs/cluster1/subgraph1 | wc -l`


#sudo bash eigenvector.sh "enron_undir_edgelist" 

#echo "here returns"
#./check.sh
rm -r "labeling_enron_subgraphs"
#run clustering on the file
./makeclusters.sh "clusters/"

#rm "enron"
