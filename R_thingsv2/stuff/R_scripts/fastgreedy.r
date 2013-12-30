#!/usr/bin/Rscript
args <- commandArgs(TRUE)
#R_LIBS='/home/utkarsh/R/x86_64-pc-linux-gnu-library/2.15'
#library()
library('igraph')
n=as.numeric(args[2])
#print("boom")
#print(args[1])
#print("boo")
#print(args[2])
G=read.graph(args[1], format="edgelist", n=as.numeric(args[2]), directed=FALSE)

a=fastgreedy.community(G)
print("done")
lapply(a$membership, write, paste(args[1],"try",sep = ""), append=TRUE, ncolumns=13)

