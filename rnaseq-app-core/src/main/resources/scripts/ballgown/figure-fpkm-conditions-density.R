## Script input parameters:
##  1.- working directory: path to the directory where results should be stored.
##  2.- the output format of the images: jpeg, tiff or png
##  3.- the width of the images
##  4.- the height of the images
##  5.- color: TRUE for colored images and FALSE for grayscale images

library(ballgown)
library(genefilter)

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

colors <- colorRampPalette(gray.colors(5, start = 1.0, end = 0.3, gamma = 2.2, alpha = NULL))
if(image.color){
	colors <- colorRampPalette(c("white", "blue","red","green","yellow"))
	image.file	<- paste(imagesDirectory, 'FPKM-conditions-density_color',sep="")
} else {
	image.file	<- paste(imagesDirectory, 'FPKM-conditions-density',sep="")
}

if(image.format == "jpeg") {
	jpeg(paste(image.file, '.jpeg', sep=""), width = image.width, height = image.height)
} else if(image.format == "tiff") {
	tiff(paste(image.file, '.tiff',sep=""), width = image.width, height = image.height)
} else if(image.format == "png") {
	png(paste(image.file, '.png',sep=""), width = image.width, height = image.height)
}

conditions <- unique(pheno_data$type)

# Identify the genes (rows) with adjusted p-value (i.eq-value) < 0.05 
qsig <- which(results_genes$qval<0.05)
qsigm <- results_genes[qsig,]
qgn <- unique(results_genes$id, incomparables = FALSE)

# Convert the matrix to data
gene_expression <-  gexpr(bg_filt)
gene_expression <- as.data.frame(gene_expression)
gene_expression$control <- rowMeans(gene_expression[,which(pheno_data$type==conditions[1])])
gene_expression$treatment <- rowMeans(gene_expression[,which(pheno_data$type==conditions[2])])

x <- log2(gene_expression[,conditions[1]]+1)
y <- log2(gene_expression[,conditions[2]]+1)

par(mfrow=c(1,2))
plot(x,y, xlab=paste(conditions[1],"log2(FPKM+1)",sep=" "), ylab=paste(conditions[2],"log2(FPKM+1)",sep=" "))
smoothScatter(x,y, colramp = colors, xlab=paste(conditions[1],"log2(FPKM+1)",sep=" "), ylab=paste(conditions[2],"log2(FPKM+1)",sep=" "))
title(main=paste(conditions[2], "vs", conditions[1], "FPKMs", sep=" "),outer=TRUE, line = -2)

dev.off()

## Exit the R session
quit(save="no")
