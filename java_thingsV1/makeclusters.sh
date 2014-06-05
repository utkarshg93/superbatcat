#!/bin/bash


mkdir $1

echo '0' > $1"/number"


javac Step2.java
java Step2 $1 "enron" 0 $1"number"


#cat $1"/list"


recursetake2()
{
 #echo "Recursing"
 #cat $1"/list"

 while read line           
 do           
    #echo $line |  awk -F" " '{print $1}'
    #echo $line |  awk -F" " '{print $2}'
    boo=`echo $line |  awk -F" " '{print $1}'`
    clustno=`echo $line |  awk -F" " '{print $2}'`
    number=`cat $1"/number" | head -1 | tail -1` 
   

#    echo $boo"/" $boo"/subgraph"$clustno $number "/home/utkarsh/superbatcat/java_thingsV1/number"
    
    java Step2 $boo"/" $boo"/subgraph"$clustno $number $1"/number"
 #   echo "number of large clusters: "`cat $boo"/list" | wc -l`
    
    if((`cat $boo"/list" | wc -l`==1))
    then
  #   echo "Should not process"
     continue
    fi

    if((`cat $boo"/list" | wc -l`==0))
    then
   #  echo "Should not process"
     continue
    fi
  
    #echo "list file: " $boo"/list"
    
    cat $boo"/list" >> $1"/list"
   
 done < $1"/list"
}

recursetake2 $1

javac JSONConvV41.java
java JSONConvV41 $1

