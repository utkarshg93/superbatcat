sudo rm -f "enron_undir_edgelist_connected"
sudo rm -f "enron_undir_edgelist"
sudo rm -f "enron_undir_edgelist_cluster"
sudo rm -f "enron_eigen_1.json"
cp "stuff/Datasets/"$1 "enron_undir_edgelist"
cp "stuff/R_scripts/"$2 "eigenvector.r"
chmod 777 "enron_undir_edgelist"
chmod 777 "eigenvector.r"
sudo bash eigenvector1.sh "enron_undir_edgelist" > /dev/null
sudo rm "eigenvector.r"

