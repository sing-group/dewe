## Script input parameters:
##  1.- working directory: path to the directory where results should be stored.
##  2.- the output format of the images: jpeg, tiff or png
##  3.- the width of the images
##  4.- the height of the images
##  5.- color: TRUE for colored images and FALSE for grayscale images
##  6.- the number of clusters for the heatmap

library(ballgown)
library(genefilter)
library(ggplot2)
library(ggrepel)

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

if(image.color){
	image.file <- paste(imagesDirectory, 'pca_color',sep="")
} else {
	image.file <- paste(imagesDirectory, 'pca',sep="")
}

if(image.format == "jpeg") {
	jpeg(paste(image.file, '.jpeg', sep=""), width = image.width, height = image.height)
} else if(image.format == "tiff") {
	tiff(paste(image.file, '.tiff',sep=""), width = image.width, height = image.height)
} else if(image.format == "png") {
	png(paste(image.file, '.png',sep=""), width = image.width, height = image.height)
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
	#remove tumor and normal columns
	sig_gene_expression <- sig_gene_expression[,-c(7:8)]
	 
	# for pheatmap function, column names and row names of data and pdata mush be identical# change the row names
	phenotype_table <- data.frame(id=pheno_data$id, group=pheno_data$type)
	
	rownames(phenotype_table) <- phenotype_table[,1]
	
	# remove the id column
	phenotype_table <- subset(phenotype_table, select = -c(id) )
	
	# change the colnames to match with the sample names
	colnames(sig_gene_expression) <- row.names(phenotype_table)
	
	## PCA plot samples
	# transpose the data and compute principal components
	pca_data <- prcomp(t(sig_gene_expression))
	
	# Calculate PCA component percentages
	pca_data_perc <- round(100*pca_data$sdev^2/sum(pca_data$sdev^2),1)
	
	# Extract 1 and 2 principle components and create a data frame with sample names, first and second principal components and group information
	df_pca_data <- data.frame(PC1 = pca_data$x[,1], PC2 = pca_data$x[,2], sample = colnames(sig_gene_expression), condition = pheno_data$type) 
	
	if(image.color){
	  ggplot(df_pca_data, aes(PC1,PC2, color = condition))+
	  geom_point(size=8)+
	  labs(x=paste0("PC1 (",pca_data_perc[1],")"), y=paste0("PC2 (",pca_data_perc[2],")"))+
	  geom_text_repel(aes(label=sample),point.padding = 0.75) 
	}else{
	  ggplot(df_pca_data, aes(PC1,PC2, color = condition))+
	  geom_point(size=8)+
	  labs(x=paste0("PC1 (",pca_data_perc[1],")"), y=paste0("PC2 (",pca_data_perc[2],")"))+
	  geom_text_repel(aes(label=sample),point.padding = 0.75) +
	  scale_colour_grey(start = 0, end = 0.5)+
	  theme_bw()
	}
}

dev.off()

## Exit the R session
quit(save="no")
