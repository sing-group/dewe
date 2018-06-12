## Writes filtered genes under a specified p-value
##
## Script input parameters:
##  1.- working directory: path to the directory that contains the ballgown data structure.
##  2.- p-value: the maximum p-value of genes.

getFpkmPlus1 <- function(ballgownData) {
	fpkm <- texpr(bg, meas="FPKM")
	fpkm <- fpkm+1

	fpkm.table <- cbind(geneNames(bg), geneIDs(bg), transcriptNames(bg), fpkm)
	colnames(fpkm.table) <- c("gene", "geneID", "transcript", colnames(fpkm))
	
	return(fpkm.table)
}

args <- commandArgs(TRUE)

## Parse input parameters
workingDirectory <- args[1];
if(substring(workingDirectory, nchar(workingDirectory)) != "/") {
	workingDirectory <- paste(workingDirectory, "/", sep="")
}
setwd(workingDirectory)

pValue <- args[2]

library(ballgown)
library(genefilter)
library(dplyr)
library(devtools)

## Create the tables directory if neccessary
tablesDirectory <- file.path(workingDirectory, "user-tables")

if(!dir.exists(tablesDirectory)){
	dir.create(tablesDirectory)
}

## Reload the ballgown data structure
load("bg.rda")

bg_filt = subset (bg,"rowVars(texpr(bg)) > 1", genomesubset=TRUE)
bg_filt_table = texpr(bg_filt, 'all')
bg_filt_gene_names = unique(bg_filt_table[, c(1,9,10)])

results_genes = stattest(bg_filt, feature="gene", covariate="type", getFC=TRUE, meas="FPKM")
results_genes = merge(results_genes,bg_filt_gene_names,by.x=c("id"),by.y=c("gene_id"))

sig_genes = subset(results_genes, results_genes$pval<pValue)

write.table(sig_genes[,-6], row.names = FALSE, paste(tablesDirectory, "/gene_results_sig_", pValue, ".tsv",sep=""), sep="\t")

fpkm.table <- getFpkmPlus1(bg)
write.table(fpkm.table[as.numeric(as.character(sig_genes[,6])),], row.names = FALSE,  paste(tablesDirectory, "/gene_results_sig_", pValue,"_sig_fpkm_plus_1.tsv",sep=""), sep="\t")

## Exit the R session
quit(save="no")