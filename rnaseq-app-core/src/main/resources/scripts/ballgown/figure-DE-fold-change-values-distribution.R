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

## Histogram distribution of differential expression values
color <- c('grey')
if(image.color){
	color 		<- c('limegreen')
	image.file	<- paste(imagesDirectory, 'DE-values-distribution_color',sep="")
} else {
	image.file	<- paste(imagesDirectory, 'DE-values-distribution',sep="")
}

if(image.format == "jpeg") {
	jpeg(paste(image.file, '.jpeg', sep=""), width = image.width, height = image.height)
} else if(image.format == "tiff") {
	tiff(paste(image.file, '.tiff',sep=""), width = image.width, height = image.height)
} else if(image.format == "png") {
	png(paste(image.file, '.png',sep=""), width = image.width, height = image.height)
}

conditions <- unique(pheno_data$type)

abs_fcs <- cbind(
 as.integer(abs(results_genes$logfc))
) 
o <- order(abs_fcs,decreasing=TRUE)
abs_fcs_order <- abs_fcs[o,]
limit <- abs_fcs_order[1]+1

# draw histogram
hist(results_genes$logfc, breaks=50, col=color, xlab=paste("log2(fold change)",conditions[2], "vs", conditions[1], sep=" "), main="Distribution of differential expression values", xlim=c(-limit, limit))

# Add vertical cut offs
abline(v=c(-2,2), col="black", lwd=2, lty=2)

# Add legend. Remember fold changes are logged (2 base)
legend("topright", "|log2(fold change)|>2", lwd=2, lty=2)

dev.off()

## Exit the R session
quit(save="no")
