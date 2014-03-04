num=`cat labeling_enron_subgraphs/largesubgraphs/list | wc -l`

for((i=1;i<=num;++i))
do
name=`cat labeling_enron_subgraphs/largesubgraphs/list | head -$i | tail -1| awk -F" " '{print $1}'`
echo $name
#cat labeling_enron_subgraphs/largesubgraphs/list | head -$i | tail -1 | awk -F" " '{print $1,$2}'
./numberconnected.r `cat labeling_enron_subgraphs/largesubgraphs/list | head -$i | tail -1 | awk -F" " '{print $1,$2}' ` 

done


