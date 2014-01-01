javac FinalShortestPath.java
echo $1
echo $2
echo $3
echo $4
java FinalShortestPath $1 $2 $3 $4
chmod 777 'shortest.json'
cp 'ShortestPath.json' '/var/www'
