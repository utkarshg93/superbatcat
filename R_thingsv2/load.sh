echo $1
echo $2
cp "stuff/Datasets/"$1 "enron_undir_edgelist"
cp "stuff/R_scripts/"$2 "eigenvector.r"
chmod 777 "eigenvector.r"
sudo bash eigenvector.sh "enron_undir_edgelist" > /dev/null
