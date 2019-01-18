## Script input parameters:
##  1.- working directory: path to the directory where results are stored.
##  2.- output directory: path to the directory where results should be stored.

library(VennDiagram)

args <- commandArgs(TRUE)

## Parse input parameters
workingDirectory <- args[1];
if(substring(workingDirectory, nchar(workingDirectory)) != "/") {
	workingDirectory <- paste(workingDirectory, "/", sep="");
}
outputDirectory <- args[2];
if(substring(outputDirectory, nchar(outputDirectory)) != "/") {
	outputDirectory <- paste(outputDirectory, "/", sep="");
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
write.table(overlap, row.names = FALSE, paste(outputDirectory,"overlap-ballgown-edger.tsv", sep=""), sep="\t")

venn.diagram(list(Ballgown = ballgown[,1], EdgeR = edger[,1]),fill = c("black", "darkgrey"),
             alpha = c(0.5, 0.5), cex = 2,cat.fontface = 4,lty =2, fontfamily =3, imagetype = "tiff",
			 filename = paste(outputDirectory,"overlap_ballgown_edger.tiff", sep=""), resolution=300);

quit(save="no")
