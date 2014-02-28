count=0
with open("lengths3.json") as f:
    for line in f:
    	#print line
    	a=line.split()
    	
    	if(a[3]=='1'):
    		continue
    	else:
    		if(int(a[3])-int(a[6])>=3 or int(a[6])-int(a[3])>=3):
    			count+=1
print count