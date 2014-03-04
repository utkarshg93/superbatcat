#!/usr/bin/Rscript
args <- commandArgs(TRUE)
#R_LIBS='/home/utkarsh/R/x86_64-pc-linux-gnu-library/2.15'
#library()
library('igraph')
n=as.numeric(args[2])
#print("boom")
#print("In R")
#print(args[1])
#print("boo")
#print(args[2])
G=read.graph(args[1], format="edgelist", n=as.numeric(args[2]), directed=FALSE)
b=.libPaths()
#print(b)
a=leading.eigenvector.community(G, options=list(maxiter=1000000))
print("done")
lapply(a[2], write, paste(args[1],"try",sep = ""), append=FALSE, ncolumns=13)

