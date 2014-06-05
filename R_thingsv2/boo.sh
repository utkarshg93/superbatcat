sudo rm -f "enron_undir_edgelist_connected"
sudo rm -f "enron_undir_edgelist"
sudo rm -f "enron_undir_edgelist_cluster"
sudo rm -f "enron_eigen_1.json"
sudo rm -r "labeling_enron_subgraphs"
sudo rm -f "Romil0"
sudo rm -f "Romil1"
sudo rm -f "Romil2"
sudo rm -f "Romil-1"
sudo rm -f "Romil-2"

#echo $1
#echo $2

num=`cat stuff/Datasets/boo.txt | wc -l`
javac Sampler.java
javac JSONConvV2.java 

for((j=-2;j<=-2;++j))
do
for((k=0;k<=2;++k))
do
beta=$((6+$j))
echo "Beta: "$beta" run "$k >> "Romil"$j

javac UndirToDir.java
java UndirToDir >/dev/null

java -Xmx2000m Sampler "enron_undir_edgelist" $j > "Apoorva"

java -Xmx2000m JSONConvV2 "Apoorva" "enron_undir_edgelist" >/dev/null

./check.sh >> "Romil"$j
sudo rm -r "labeling_enron_subgraphs"
echo -e "\n" >> "Romil"$j
done
done
