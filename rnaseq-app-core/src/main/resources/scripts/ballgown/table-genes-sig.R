## Writes filtered genes under a specified p-value
##
## Script input parameters:
##  1.- working directory: path to the directory that contains the ballgown data structure.
##  2.- p-value: the maximum p-value of genes.
##  3.- the output format of the images: jpeg, tiff or png
##  4.- the width of the images
##  5.- the height of the images
##  6.- color: TRUE for colored images and FALSE for grayscale images

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

## Load phenotype data from a file we saved in the current working directory
pheno_data = read.csv(paste(workingDirectory, "phenotype-data.csv", sep=""))

setwd(workingDirectory)

pValue <- args[2]

library(ballgown)
library(genefilter)
library(dplyr)
library(devtools)
library(pheatmap)

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

results_genes[,"logfc"] <- log2(results_genes[,"fc"])
# Identify the genes (rows) below p-value
sigpvi <- which(results_genes[,"pval"]<pValue)
# Extract p-significant genes in a separate object
sigpv <- results_genes[sigpvi,]

# Identify the genes (rows) below q-value 0.05
sigpi <- which(sigpv[,"qval"]<0.05)
# Extract q-significant genes in a separate object
sigp <- sigpv[sigpi,]

## If there are no significative genes, heatmap plot is not generated
if(!all(is.na(sigpi))){
	imagesDirectory <- file.path(workingDirectory, "user-images/")
	
	if(!dir.exists(imagesDirectory)){
	    dir.create(imagesDirectory)
	}
	
	## Create figures
	image.format 	<- args[3]
	image.width 	<- as.numeric(args[4])
	image.height 	<- as.numeric(args[5])
	image.color     <- as.logical(args[6])

	## Heatmap
	conditions <- unique(pheno_data$type)
	gene_expression <- gexpr(bg)
	gene_expression <- as.data.frame(gene_expression)
	gene_expression$control <- rowMeans(gene_expression[,c(1:3)])
	gene_expression$treatment <- rowMeans(gene_expression[,c(4:6)])	
	
	# Identify the statistically significant genes (rows) that are upregulated/downregulated by 4 fold
	sigde <- which(abs(sigp[,"logfc"]) >= 2)
	
	# Extract and store the statistically significant genes (rows) that are upregulated/ downregulated by 4 fold
	sig_tn_de <- sigp[sigde,]
	
	sig_gene_expression=gene_expression[rownames(gene_expression) %in% sig_tn_de$id,]
	#remove tumor and normal columns
	sig_gene_expression <- sig_gene_expression[,-c(7:8)]
	
	# for pheatmap function, column names and row names of data and pdata mush be identical# change the row names
	phenotype_table <- data.frame(id=pheno_data$id, group=pheno_data$type)
	
	rownames(phenotype_table) <- phenotype_table[,1]
	
	# remove the id column
	phenotype_table <- subset(phenotype_table, select = -c(id) )
	
	# change the colnames to match with the sample names
	colnames(sig_gene_expression) <- row.names(phenotype_table)
	
	# draw heatmap
	if(image.color){
	  pheatmap(as.matrix(sig_gene_expression), kmeans_k = NA, scale = "row", clustering_distance_rows = "correlation", clustering_method = "complete",annotation_col = phenotype_table , main="Significant genes", filename=paste(imagesDirectory, 'heatmap-pvalue-less-than-', pValue, ".", image.format, sep=""), width=image.width*0.0033333333333333335, height=image.height*0.0033333333333333335, fontsize=image.width*10/3100, treeheight_row=image.width*50/3100, treeheight_col=image.width*50/3100) 
	}else{
	   group        <- c("gray20", "gray80")
	   names(group) <- c(toString(conditions[1]), toString(conditions[2]))
	   anno_colors <- list(group = group)
	   pheatmap(as.matrix(sig_gene_expression), kmeans_k = NA, scale = "row", clustering_distance_rows = "correlation", clustering_method = "complete",annotation_col = phenotype_table , main="Significant genes", filename=paste(imagesDirectory, 'heatmap-pvalue-less-than-', pValue, ".", image.format, sep=""), width=image.width*0.0033333333333333335, height=image.height*0.0033333333333333335, fontsize=image.width*10/3100, treeheight_row=image.width*50/3100, treeheight_col=image.width*50/3100, color=gray.colors(20, start = 0.9, end = 0.2, gamma = 2.2, alpha = NULL), annotation_colors = anno_colors)
	}	
}

## Exit the R session
quit(save="no")