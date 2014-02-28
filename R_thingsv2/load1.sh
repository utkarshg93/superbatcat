sudo rm -f "enron_undir_edgelist_connected"
sudo rm -f "enron_undir_edgelist"
sudo rm -f "enron_undir_edgelist_cluster"
sudo rm -f "enron_eigen_1.json"
echo $1
if [[ $1 == "neo4j" ]]
then
sudo bash load3.sh
else 
cp "stuff/Datasets/"$1 "enron_undir"
fi
cp "stuff/R_scripts/"$2 "eigenvector.r"
javac UndirToDir.java
java UndirToDir
chmod 777 "enron_undir_edgelist"
chmod 777 "eigenvector.r"
sudo bash eigenvector1.sh "enron_undir_edgelist" > /dev/null
sudo rm "eigenvector.r"

