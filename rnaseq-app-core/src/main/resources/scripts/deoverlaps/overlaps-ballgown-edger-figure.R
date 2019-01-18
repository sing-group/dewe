## Script input parameters:
##  1.- working directory: path to the directory where results are stored.
##  2.- the output format of the images: jpeg, tiff or png
##  3.- the width of the images
##  4.- the height of the images
##  5.- color: TRUE for colored images and FALSE for grayscale images

library(VennDiagram)

args <- commandArgs(TRUE)

## Parse input parameters
workingDirectory <- args[1];
if(substring(workingDirectory, nchar(workingDirectory)) != "/") {
	workingDirectory <- paste(workingDirectory, "/", sep="");
}

ballgown <- read.table(file = paste(workingDirectory,"ballgown/phenotype-data_gene_results_sig.tsv", sep=""), sep = "\t", header = TRUE)
ballgown <- ballgown[ballgown[ ,6]!='.', ]
ballgown <- ballgown[!duplicated(ballgown), ]
ballgown <- ballgown[, -c(1,2,5)]
ballgown <- ballgown[, c(3,1,2)]
ballgown[, 2] <- log(ballgown[2], 2)
colnames(ballgown) <- c('gene','ballgown.logfc','ballgown.pvalue')
edger <- read.table(file = paste(workingDirectory,"edger/DE_significant_genes.tsv", sep=""), sep = "\t", header = TRUE)
edger <- edger[, -1]
edger <- edger[, c(1,3,2)]
colnames(edger) <- c('gene','edger.logfc','edger.pvalue')
overlap <- merge(ballgown, edger, by = 'gene')

## Create figures
image.format 	<- args[2]
image.width 	<- as.numeric(args[3])
image.height 	<- as.numeric(args[4])
image.color     <- as.logical(args[5])

colors <- c(gray.colors(4, start = 0.8, end = 0.2, gamma = 2.2, alpha = NULL))
if(image.color){
	venn.diagram(list(Ballgown = ballgown[,1], EdgeR = edger[,1]),fill = c("blue", "green"), imagetype = image.format,
             alpha = c(0.5, 0.5), cex = 2,cat.fontface = 4,lty =2, fontfamily =3, height=image.height, width = image.width,
			 filename = paste(workingDirectory, "overlaps/","overlap_ballgown_edger_color.", image.format, sep=""), resolution=300);
} else {
	venn.diagram(list(Ballgown = ballgown[,1], EdgeR = edger[,1]),fill = c("black", "darkgrey"),
             alpha = c(0.5, 0.5), cex = 2,cat.fontface = 4,lty =2, fontfamily =3, height=image.height, width = image.width, imagetype = image.format,
			 filename = paste(workingDirectory, "overlaps/","overlap_ballgown_edger.", image.format, sep=""), resolution=300);
}

quit(save="no")
