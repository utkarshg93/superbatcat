sudo rm -f "enron_undir_edgelist_connected"
sudo rm -f "enron_undir_edgelist"
sudo rm -f "enron_undir_edgelist_cluster"
sudo rm -f "enron_eigen_1.json"
#echo $1
#echo $2

num=`cat stuff/Datasets/boo.txt | wc -l`

for((i=1;i<=num;++i))
do
name=`cat stuff/Datasets/boo.txt | head -$i | tail -1| awk -F" " '{print $1}'`
echo "data setname "$name
if [[ $name == "neo4j" ]]
then
sudo bash load3.sh
else 
cp "stuff/Datasets/"$name "enron_undir"
fi
javac UndirToDir.java
java UndirToDir >/dev/null
cp "stuff/R_scripts/"$2 "eigenvector.r"
chmod 777 "eigenvector.r"
echo "sh file"
sudo bash eigenvector.sh "enron_undir_edgelist" 

#echo "here returns"
./check.sh
rm -r "labeling_enron_subgraphs"
done
