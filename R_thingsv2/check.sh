num=`cat labeling_enron_subgraphs/largesubgraphs/list | wc -l`

j=0;
while read line
do	
	List[$j]=$line
	j=$(($j+1))
done < "labeling_enron_subgraphs/largesubgraphs/list"
	
max=-999999999;
min=999999999;
avrg=0;
j=0;
  for i in ${List[@]}
  do
     j=$(($j+1))
     if [[ $i -gt $max ]] 
     then 
         max=$i
     fi
     avrg=$(($avrg+$i))
  done

avrg=$(($avrg/$j))

echo "Number of clusters:" $num 
largest=`sort -n labeling_enron_subgraphs/largesubgraphs/list | uniq | tail -1`
echo "Largest cluster size:" $largest 
averag1=0
echo $num","$avrg","$largest

