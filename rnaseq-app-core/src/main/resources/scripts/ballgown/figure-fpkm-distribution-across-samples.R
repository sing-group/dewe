## Script input parameters:
##  1.- working directory: path to the directory where results should be stored.
##  2.- the output format of the images: jpeg, tiff or png
##  3.- the width of the images
##  4.- the height of the images
##  5.- color: TRUE for colored images and FALSE for grayscale images

library(ballgown)

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

## Create figures
image.format 	<- args[2]
image.width 	<- as.numeric(args[3])
image.height 	<- as.numeric(args[4])
image.color     <- as.logical(args[5])

if(image.color){
	palette(c('darkorange','dodgerblue','hotpink','limegreen', 'yellow'))
	image.file	<- paste(imagesDirectory, 'FPKM-distribution-across-samples_color',sep="")
}else{
	palette(gray.colors(5, start = 0.3, end = 0.9, gamma = 2.2, alpha = NULL))
	image.file	<- paste(imagesDirectory, 'FPKM-distribution-across-samples',sep="")
}

fpkm = texpr(bg, meas="FPKM")
fpkm = log2(fpkm+1)

## Distribution of FPKM values across the samples
if(image.format == "jpeg") {
	jpeg(paste(image.file, '.jpeg',sep=""), width = image.width, height = image.height)
} else if(image.format == "tiff") {
	tiff(paste(image.file, '.tiff',sep=""), width = image.width, height = image.height)
} else if(image.format == "png") {
	png(paste(image.file, '.png',sep=""), width = image.width, height = image.height)
}
defaultMar <- par()$mar
par(mar=c(defaultMar[1] + 4.9, defaultMar[2], defaultMar[3], defaultMar[4]))
boxplot(fpkm, col=as.numeric(pheno_data$type), las=2, ylab='log2(FPKM+1)', names=ballgown::sampleNames(bg))
dev.off()

## Exit the R session
quit(save="no")
