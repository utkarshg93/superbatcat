sudo rm -f "enron_undir_edgelist_connected"
sudo rm -f "enron_undir_edgelist"
sudo rm -f "enron_undir_edgelist_cluster"
sudo rm -f "enron_eigen_1.json"
sudo rm -r "labeling_enron_subgraphs"
#echo $1
#echo $2

num=`cat stuff/Datasets/boo.txt | wc -l`

for((i=1;i<=num;++i))
do
name=`cat stuff/Datasets/boo.txt | head -$i | tail -1| awk -F" " '{print $1}'`
echo "data setname "$name >> "Romil"
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
javac Sampler.java
java -Xmx2000m Sampler "enron_undir_edgelist" > "Apoorva"

#echo "here returns"
#./check.sh
javac JSONConvV2.java #HAVE CHANGED LINE 65 AND 66
java -Xmx2000M JSONConvV2 "Apoorva" "enron_undir_edgelist" >/dev/null 
./check.sh >> "Romil"
#sudo rm -r "labeling_enron_subgraphs"
done
