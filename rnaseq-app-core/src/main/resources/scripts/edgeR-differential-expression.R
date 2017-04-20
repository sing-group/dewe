## Script input parameters:
##  1.- working directory: path to the directory where results should be stored.
##	This directory must contain two files:
##	- ENSG_ID2Name.txt: the gene mapping.
##	- gene_read_counts_table_all.tsv: the read count matrix, including a
##	header with the sample names and a sample class labels in the last row.

args <- commandArgs(TRUE)

## Parse input parameters
working_dir = args[1];
if(substring(working_dir, nchar(working_dir)) != "/") {
	working_dir <- paste(working_dir, "/", sep="");
}

setwd(working_dir)

## Read in gene mapping
mapping=read.table(paste(working_dir, "ENSG_ID2Name.txt", sep=""), header=FALSE, stringsAsFactors=FALSE, row.names=1)

## Read in count matrix
dat=read.table(paste(working_dir, "gene_read_counts_table_all.tsv", sep=""), header=TRUE, stringsAsFactors=FALSE, row.names=1, check.names=FALSE)

## Read class labels from count matrix file
class <- factor(as.character(dat[nrow(dat),]))

## The last 5 rows are summary data and the last one contains the labels, remove
rawdata=as.data.frame(lapply(dat[1:(length(rownames(dat))-6),], as.numeric))
colnames(rawdata) <- colnames(dat)
rownames(rawdata) <- rownames(dat[1:(length(rownames(dat))-6),])

## Require at least 25% of samples to have count > 25
quant <- apply(rawdata,1,quantile,0.75)
keep <- which((quant >= 25) == 1)
rawdata <- rawdata[keep,]
dim(rawdata)

###################
## Running edgeR ##
###################

## load edgeR
library('edgeR')

## Get common gene names
genes=rownames(rawdata)
gene_names=mapping[genes,1]

## Make DGEList object
y <- DGEList(counts=rawdata, genes=genes, group=class)
nrow(y)

## TMM Normalization
y <- calcNormFactors(y)

## Estimate dispersion
y <- estimateCommonDisp(y, verbose=TRUE)
y <- estimateTagwiseDisp(y)

## Differential expression test
et <- exactTest(y)

## Print top genes
topTags(et)

## Print number of up/down significant genes at FDR = 0.05  significance level
summary(de <- decideTestsDGE(et, p=.05))
detags <- rownames(y)[as.logical(de)]

## Output DE genes
## Matrix of significantly DE genes
mat <- cbind(
 genes,gene_names,
 sprintf('%0.3f',log10(et$table$PValue)),
 sprintf('%0.3f',et$table$logFC)
)[as.logical(de),]
colnames(mat) <- c("Gene", "Gene_Name", "Log10_Pvalue", "Log_fold_change")

## Order by log fold change
o <- order(et$table$logFC[as.logical(de)],decreasing=TRUE)
mat <- mat[o,]

## Save table
write.table(mat, file="DE_genes.txt", quote=FALSE, row.names=FALSE, sep="\t")

##To exit R type the following
quit(save="no")
