## Script input parameters:
##  1.- working directory: path to the directory where results should be stored.
##  2.- the output format of the images: jpeg, tiff or png
##  3.- the width of the images
##  4.- the height of the images
##  5.- color: TRUE for colored images and FALSE for grayscale images
##  6.- the number of clusters for the heatmap

library(ballgown)
library(genefilter)
library(pheatmap)

args <- commandArgs(TRUE)

## Parse input parameters
workingDirectory <- args[1];
if(substring(workingDirectory, nchar(workingDirectory)) != "/") {
	workingDirectory <- paste(workingDirectory, "/", sep="");
}

imagesDirectory <- file.path(workingDirectory, "user-images/")

if(!dir.exists(imagesDirectory)){
    dir.create(imagesDirectory)
}

pheno_data = read.csv(paste(workingDirectory, "phenotype-data.csv",sep=""))

setwd(workingDirectory)

## Reload the ballgown data structure
load("bg.rda")

## Filter low-abundance genes. Here we remove all transcripts with a variance across the samples of less than one
bg_filt = subset (bg,"rowVars(texpr(bg)) > 1", genomesubset=TRUE)

## Perform DE analysis now using the filtered data
results_genes = stattest(bg_filt, feature="gene", covariate="type", getFC=TRUE, meas="FPKM")
results_genes[,"logfc"] <- log2(results_genes[,"fc"])

## Create figures
image.format 	<- args[2]
image.width 	<- as.numeric(args[3])
image.height 	<- as.numeric(args[4])
image.color     <- as.logical(args[5])

clusters=as.numeric(args[6])
if(clusters<2){
	clusters=NA
}

conditions <- unique(pheno_data$type)

gene_expression <-  gexpr(bg_filt)
gene_expression <- as.data.frame(gene_expression)
gene_expression$control <- rowMeans(gene_expression[,which(pheno_data$type==conditions[1])])
gene_expression$treatment <- rowMeans(gene_expression[,which(pheno_data$type==conditions[2])])

# Identify the genes (rows) below q-value 0.05
sigpi <- which(results_genes[,"qval"]<0.05)

## If there are no significative genes, heatmap and pca plots are not generated
if(!all(is.na(sigpi))){
	## Heatmap
	
	# Extract p-significant genes in a separate object
	sigp <- results_genes[sigpi,]
	
	# Identify the statistically significant genes (rows) that are upregulated/ ##
	## downregulated by 4 fold
	sigde <- which(abs(sigp[,"logfc"]) >= 2)
	
	# Extract and store the statistically significant genes (rows) that are upregulated/ downregulated by 4 fold
	sig_tn_de <- sigp[sigde,]
	
	sig_gene_expression=gene_expression[rownames(gene_expression) %in% sig_tn_de$id,]
	#remove control and threatment/disease columns
	last <- dim(sig_gene_expression)[2]
	last_ <- last-1
	sig_gene_expression <- sig_gene_expression[,-c(last_:last)]
	 
	# for pheatmap function, column names and row names of data and pdata mush be identical# change the row names
	phenotype_table <- data.frame(id=pheno_data$id, group=pheno_data$type)
	
	rownames(phenotype_table) <- phenotype_table[,1]
	
	# remove the id column
	phenotype_table <- subset(phenotype_table, select = -c(id) )
	
	# change the colnames to match with the sample names
	colnames(sig_gene_expression) <- row.names(phenotype_table)
	
	# draw heatmap
	if(image.color){
	  pheatmap(as.matrix(sig_gene_expression), kmeans_k = clusters, scale = "row", clustering_distance_rows = "correlation", clustering_method = "complete",annotation_col = phenotype_table , main="Significant genes", filename=paste(imagesDirectory, 'heatmap_color.', image.format, sep=""), width=image.width*0.0033333333333333335, height=image.height*0.0033333333333333335, fontsize=image.width*10/3100, treeheight_row=image.width*50/3100, treeheight_col=image.width*50/3100) 
	}else{
	   group        <- c("gray20", "gray80")
	   names(group) <- c(toString(conditions[1]), toString(conditions[2]))
	   anno_colors  <- list(group = group)
	   pheatmap(as.matrix(sig_gene_expression), kmeans_k = clusters, scale = "row", clustering_distance_rows = "correlation", clustering_method = "complete",annotation_col = phenotype_table , main="Significant genes", filename=paste(imagesDirectory, 'heatmap.', image.format, sep=""), width=image.width*0.0033333333333333335, height=image.height*0.0033333333333333335, fontsize=image.width*10/3100, treeheight_row=image.width*50/3100, treeheight_col=image.width*50/3100, color=gray.colors(20, start = 0.9, end = 0.2, gamma = 2.2, alpha = NULL), annotation_colors = anno_colors)
	}
}

## Exit the R session
quit(save="no")
