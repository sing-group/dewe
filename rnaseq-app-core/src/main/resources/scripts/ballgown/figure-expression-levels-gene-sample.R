## Creates a figure with the structure and expression levels in a sample of all transcripts that share the same gene locus.
##
## Script input parameters:
##  1.- working directory: path to the directory that contains the ballgown data structure.
##  2.- transcript id: the numeric id of the transcript
##  3.- the sample name
##  4.- the output format of the image: jpeg, tiff or png
##  5.- the width of the image
##  6.- the height of the image

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
transcriptId 	<- args[2]
geneName 	<- geneNames(bg)[transcriptId]
geneId		<- geneIDs(bg)[transcriptId]
sampleName	<- args[3]

## Configure the output image
image.format 	<- args[4]
image.width 	<- as.numeric(args[5])
image.height 	<- as.numeric(args[6])
image.file 	<- paste(workingDirectory, 'transcripts-gene_', geneName, '-sample_', sampleName, '.', image.format, sep="")
image.title 	<- paste('Gene ', geneName, ' in sample ', sampleName, sep="")

## Plot the FPKM distribution
if(image.format == "jpeg") {
	jpeg(image.file, width = image.width, height = image.height)
} else if(image.format == "tiff") {
	tiff(image.file, width = image.width, height = image.height)
} else if(image.format == "png") {
	png(image.file, width = image.width, height = image.height)
}

plotTranscripts(geneId, bg, main = image.title, sample = sampleName)
dev.off()

## Exit the R session
quit(save="no")
