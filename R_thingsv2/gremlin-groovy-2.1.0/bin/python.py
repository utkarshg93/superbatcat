f=open("boo.txt")
c=f.readline()
a=f.readlines()
f.close()
first=[]
Second=[]
for i in a:
    first=i.split('][')
    if(len(first)>=2):
        Second.append(first[1].split('->'))
m=[]
for i in Second:
    a=''
    b=''
    for x in i[0]:
        if x.isdigit():
            a+=x
    for x in i[1]:
        if x.isdigit():
            b+=x
    l=[]
    l.append(a)
    l.append(b)
    m.append(l)

f=open("newfile.txt","w")
f.write(c)
for i in m:
    f.write(i[0]+" "+i[1]+"\n")
f.close()
