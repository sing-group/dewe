## Creates the figure of the FPKM distribution in conditions for the specified transcript id
##
## Script input parameters:
##  1.- working directory: path to the directory that contains the ballgown data structure.
##  2.- transcript id: the numeric id of the transcript.
##  3.- image format: the output format of the image which can be jpeg, tiff or png
##  4.- the width of the image
##  4.- the height of the image

args <- commandArgs(TRUE)

## Parse input parameters
workingDirectory <- args[1];
if(substring(workingDirectory, nchar(workingDirectory)) != "/") {
	workingDirectory <- paste(workingDirectory, "/", sep="")
}
setwd(workingDirectory)

library(ballgown)

## Reload the ballgown data structure
load("bg.rda")

## Retrieve image data
pheno_data 	<- ballgown::pData(bg)
transcriptId 	<- args[2]
geneName 	<- ballgown::geneNames(bg)[transcriptId]
transcriptName 	<- ballgown::transcriptNames(bg)[transcriptId]

## Compute the FPKM values
fpkm = texpr(bg, meas="FPKM")
fpkm = log2(fpkm+1)

## Configure the output image
image.format 	<- args[3]
image.width 	<- as.numeric(args[4])
image.height 	<- as.numeric(args[5])
image.file 	<- paste(workingDirectory, 'FPKM-distribution-gene_', geneName, '-transcript_', transcriptName, '.', image.format, sep="")

## Plot the FPKM distribution
if(image.format == "jpeg") {
	jpeg(image.file, width = image.width, height = image.height)
} else if(image.format == "tiff") {
	tiff(image.file, width = image.width, height = image.height)
} else if(image.format == "png") {
	png(image.file, width = image.width, height = image.height)
}

plot(
	fpkm[transcriptId,] ~ pheno_data$type, 
	border=c(1,2), 
	main=paste(geneName, ' : ', transcriptName), 
	pch=19, 
	xlab="Type", 
	ylab='log2(FPKM+1)'
)
points(
	fpkm[transcriptId,] ~ jitter(as.numeric(pheno_data$type)), 
	col=as.numeric(pheno_data$type)
)
dev.off()

## Exit the R session
quit(save="no")
