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

## Load all attributes including gene name
bg_filt_table = texpr(bg_filt , 'all')
bg_filt_gene_names = unique(bg_filt_table[, 9:10])

## Perform DE analysis now using the filtered data
results_genes = stattest(bg_filt, feature="gene", covariate="type", getFC=TRUE, meas="FPKM")
results_genes = merge(results_genes,bg_filt_gene_names,by.x=c("id"),by.y=c("gene_id"))

## Create figures
image.format 	<- args[2]
image.width 	<- as.numeric(args[3])
image.height 	<- as.numeric(args[4])
image.color     <- as.logical(args[5])
color = c('grey')
if(image.color){
	color = c('limegreen')
}

if(image.format == "jpeg") {
	jpeg(paste(imagesDirectory, 'genes-DE-pValues-distribution.jpeg',sep=""), width = image.width, height = image.height)
} else if(image.format == "tiff") {
	tiff(paste(imagesDirectory, 'genes-DE-pValues-distribution.jpeg',sep=""), width = image.width, height = image.height)
} else if(image.format == "png") {
	png(paste(imagesDirectory, 'genes-DE-pValues-distribution.jpeg',sep=""), width = image.width, height = image.height)
}
hist(results_genes[,ncol(results_genes)-1], breaks = 50, right=FALSE, col=color, main="Gene P-values", xlab="P-value")
dev.off()

## Exit the R session
quit(save="no")
