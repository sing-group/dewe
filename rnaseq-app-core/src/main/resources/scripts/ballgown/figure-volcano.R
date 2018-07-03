## Script input parameters:
##  1.- working directory: path to the directory where results should be stored.
##  2.- the output format of the images: jpeg, tiff or png
##  3.- the width of the images
##  4.- the height of the images
##  5.- color: TRUE for colored images and FALSE for grayscale images

library(ballgown)
library(genefilter)
library(devtools)
library(calibrate)

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

## Create figures
image.format 	<- args[2]
image.width 	<- as.numeric(args[3])
image.height 	<- as.numeric(args[4])
image.color     <- as.logical(args[5])

## Volcano plot
colors <- c(gray.colors(4, start = 0.8, end = 0.2, gamma = 2.2, alpha = NULL))
if(image.color){
	colors <- c('black','red','orange','green')
	image.file	<- paste(imagesDirectory, 'volcano-plot_color',sep="")
} else {
	image.file	<- paste(imagesDirectory, 'volcano-plot',sep="")
}

if(image.format == "jpeg") {
	jpeg(paste(image.file, '.jpeg', sep=""), width = image.width, height = image.height)
} else if(image.format == "tiff") {
	tiff(paste(image.file, '.tiff',sep=""), width = image.width, height = image.height)
} else if(image.format == "png") {
	png(paste(image.file, '.png',sep=""), width = image.width, height = image.height)
}

results_genes[,"logfc"] <- log2(results_genes[,"fc"])

fc_sig_results_genes <- which(abs(results_genes$logfc)>1)
fc_sig_results_genes_plot <- results_genes[fc_sig_results_genes,] 

qval_sig_results_genes <- which(results_genes$qval<0.05)
qval_sig_results_genes_plot <- results_genes[qval_sig_results_genes,] 

qval_fc_sig_results_genes <- which(fc_sig_results_genes_plot$qval<0.05)
qval_fc_sig_results_genes_plot <-fc_sig_results_genes_plot[qval_fc_sig_results_genes,] 

abs_fcs <- cbind(
 as.integer(abs(results_genes$logfc))
) 
o <- order(abs_fcs,decreasing=TRUE)
abs_fcs_order <- abs_fcs[o,]
limit <- abs_fcs_order[1]+1

plot(results_genes$logfc,-log10(results_genes$pval), col=colors[1], pch=20, xlim=c(-limit, limit), xlab="log2(fold change)", ylab="-log10(pValue)")  

# highlight the genes with color  
points(fc_sig_results_genes_plot$logfc,-log10(fc_sig_results_genes_plot$pval), col=colors[2], pch=20) 
points(qval_sig_results_genes_plot$logfc,-log10(qval_sig_results_genes_plot$pval), col=colors[3], pch=20) 
points(qval_fc_sig_results_genes_plot$logfc,-log10(qval_fc_sig_results_genes_plot$pval), col=colors[4], pch=20) 

# label the significant genes
textxy(qval_fc_sig_results_genes_plot$logfc,-log10(qval_fc_sig_results_genes_plot$pval), labs=qval_fc_sig_results_genes_plot$id, cex=.6)

legend("bottomright", xjust=1, yjust=1, legend=c("|log2(fold change)|>1", "FDR<0.05", "both"), pch=20, col=c(colors[2],colors[3],colors[4]))

dev.off()

## Exit the R session
quit(save="no")
