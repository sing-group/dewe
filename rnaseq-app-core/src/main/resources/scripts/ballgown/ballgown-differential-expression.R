## Script input parameters:
##  1.- working directory: path to the directory where results should be stored.
##  2.- phenotype data csv file: the name of the csv file containing the
##      phenotype data structure (it should be located in the working directory).
##  3.- the output format of the images: jpeg, tiff or png
##  4.- the width of the images
##  5.- the height of the images
##  6.- color: TRUE for colored images and FALSE for grayscale images

library(ballgown)
library(genefilter)
library(dplyr)
library(devtools)
library(calibrate)
library(pheatmap)
library(ggplot2)
library(ggrepel)

getFpkmPlus1 <- function(ballgownData) {
	fpkm <- texpr(bg, meas="FPKM")
	fpkm <- fpkm+1

	fpkm.table <- cbind(geneNames(bg), geneIDs(bg), transcriptNames(bg), fpkm)
	colnames(fpkm.table) <- c("gene", "geneID", "transcript", colnames(fpkm))
	
	return(fpkm.table)
}

args <- commandArgs(TRUE)

## Parse input parameters
workingDirectory <- args[1];
if(substring(workingDirectory, nchar(workingDirectory)) != "/") {
	workingDirectory <- paste(workingDirectory, "/", sep="");
}
phenoDataPrefix <- args[2];
if(substring(phenoDataPrefix, nchar(phenoDataPrefix)-3) == ".csv") {
	phenoDataPrefix <- tools::file_path_sans_ext(phenoDataPrefix);
}

## Load phenotype data from a file we saved in the current working directory
pheno_data = read.csv(paste(workingDirectory, phenoDataPrefix,".csv",sep=""))

## Load ballgown data structure and save it to a variable "bg"
bg = ballgown(samples=as.vector(pheno_data$path), pData=pheno_data)

## Load all attributes including gene name
bg_table = texpr(bg, 'all')
bg_gene_names = unique(bg_table[, 9:10])

## Save the ballgown object to a file for later use
save(bg, file=paste(workingDirectory, 'bg.rda',sep=""))

## Save the FPKM+1 table containing all genes and transcripts
fpkm.table <- getFpkmPlus1(bg)
write.table(fpkm.table, row.names = FALSE, paste("fpkm-plus-1", ".tsv",sep=""), sep="\t")

## Perform differential expression (DE) analysis with no filtering
results_transcripts = stattest(bg, feature="transcript", covariate="type", getFC=TRUE, meas="FPKM")
results_transcripts = data.frame(geneNames=ballgown::geneNames(bg), geneIDs=ballgown::geneIDs(bg), transcriptNames=ballgown::transcriptNames(bg), results_transcripts)
results_genes = stattest(bg, feature="gene", covariate="type", getFC=TRUE, meas="FPKM")
results_genes = merge(results_genes,bg_gene_names,by.x=c("id"),by.y=c("gene_id"))

## Save a tab delimited file for both the transcript and gene results
write.table(results_transcripts,row.names = FALSE, paste(workingDirectory, phenoDataPrefix,"_transcript_results.tsv",sep=""),sep="\t")
write.table(results_genes, row.names = FALSE, paste(workingDirectory, phenoDataPrefix,"_gene_results.tsv",sep=""), sep="\t")

## Filter low-abundance genes. Here we remove all transcripts with a variance across the samples of less than one
bg_filt = subset (bg,"rowVars(texpr(bg)) > 1", genomesubset=TRUE)

## Load all attributes including gene name
bg_filt_table = texpr(bg_filt , 'all')
bg_filt_gene_names = unique(bg_filt_table[, c(1,9,10)])

## Perform DE analysis now using the filtered data
results_transcripts = stattest(bg_filt, feature="transcript", covariate="type", getFC=TRUE, meas="FPKM")
results_transcripts = data.frame(geneNames=ballgown::geneNames(bg_filt), geneIDs=ballgown::geneIDs(bg_filt), transcriptNames=ballgown::transcriptNames(bg_filt), results_transcripts)
results_genes = stattest(bg_filt, feature="gene", covariate="type", getFC=TRUE, meas="FPKM")
results_genes = merge(results_genes,bg_filt_gene_names,by.x=c("id"),by.y=c("gene_id"))

## Output the filtered list of genes and transcripts and save to tab delimited files
write.table(results_transcripts, row.names = FALSE, paste(workingDirectory, phenoDataPrefix,"_transcript_results_filtered.tsv",sep=""), sep="\t")
write.table(results_genes[,-6], row.names = FALSE, paste(workingDirectory, phenoDataPrefix,"_gene_results_filtered.tsv",sep=""), sep="\t")

## Identify the significant genes with p-value < 0.05
sig_transcripts = subset(results_transcripts,results_transcripts$pval<0.05)
sig_genes = subset(results_genes,results_genes$pval<0.05)

## Output the significant gene results to a pair of tab delimited files
write.table(sig_transcripts, row.names = FALSE, paste(workingDirectory, phenoDataPrefix,"_transcript_results_sig.tsv",sep=""), sep="\t")
write.table(sig_genes[,-6], row.names = FALSE, paste(workingDirectory, phenoDataPrefix,"_gene_results_sig.tsv",sep=""), sep="\t")

## Output the FPKM+1 tables associated to the significant results tables
write.table(fpkm.table[as.numeric(as.character(sig_genes[,6])),], row.names = FALSE, paste(workingDirectory, phenoDataPrefix,"_gene_results_sig_fpkm_plus_1.tsv",sep=""), sep="\t")
write.table(fpkm.table[as.numeric(as.character(sig_transcripts$id)),], row.names = FALSE, paste(workingDirectory, phenoDataPrefix,"_transcripts_results_sig_fpkm_plus_1.tsv",sep=""), sep="\t")

## Create figures
image.format 	<- args[3]
image.width 	<- as.numeric(args[4])
image.height 	<- as.numeric(args[5])
image.color     <- as.logical(args[6])
palette(gray.colors(5, start = 0.3, end = 0.9, gamma = 2.2, alpha = NULL))
color = c('grey')
if(image.color){
	palette(c('darkorange','dodgerblue','hotpink','limegreen', 'yellow'))
	color = c('limegreen')
}

fpkm = texpr(bg, meas="FPKM")
fpkm = log2(fpkm+1)

## Distribution of FPKM values across all samples
if(image.format == "jpeg") {
	jpeg(paste(workingDirectory, 'FPKM-distribution-across-samples.jpeg',sep=""), width = image.width, height = image.height)
} else if(image.format == "tiff") {
	tiff(paste(workingDirectory, 'FPKM-distribution-across-samples.tiff',sep=""), width = image.width, height = image.height)
} else if(image.format == "png") {
	png(paste(workingDirectory, 'FPKM-distribution-across-samples.png',sep=""), width = image.width, height = image.height)
}
defaultMar <- par()$mar
par(mar=c(defaultMar[1] + 4.9, defaultMar[2], defaultMar[3], defaultMar[4]))
boxplot(fpkm, col=as.numeric(pheno_data$type), las=2, ylab='log2(FPKM+1)', names=ballgown::sampleNames(bg))
dev.off()

## Overall distribution of differential expression P values
if(image.format == "jpeg") {
	jpeg(paste(workingDirectory, 'transcripts-DE-pValues-distribution.jpeg',sep=""), width = image.width, height = image.height)
} else if(image.format == "tiff") {
	tiff(paste(workingDirectory, 'transcripts-DE-pValues-distribution.tiff',sep=""), width = image.width, height = image.height)
} else if(image.format == "png") {
	png(paste(workingDirectory, 'transcripts-DE-pValues-distribution.png',sep=""), width = image.width, height = image.height)
}
hist(results_transcripts[,ncol(results_transcripts)-1], breaks = 50, right=FALSE, col=color, main="Transcript P-values", xlab="P-value")
dev.off()

if(image.format == "jpeg") {
	jpeg(paste(workingDirectory, 'genes-DE-pValues-distribution.jpeg',sep=""), width = image.width, height = image.height)
} else if(image.format == "tiff") {
	tiff(paste(workingDirectory, 'genes-DE-pValues-distribution.tiff',sep=""), width = image.width, height = image.height)
} else if(image.format == "png") {
	png(paste(workingDirectory, 'genes-DE-pValues-distribution.png',sep=""), width = image.width, height = image.height)
}
hist(results_genes[,ncol(results_genes)-1], breaks = 50, right=FALSE, col=color, main="Gene P-values", xlab="P-value")
dev.off()

## conditions <- unique(pheno_data$type)
## cond1 <- subset(bg, "type == conditions[1]", genomesubset=FALSE)
## cond2 <- subset(bg, "type == conditions[2]", genomesubset=FALSE)

## cond1.fpkm <- texpr(cond1)
## cond2.fpkm <- texpr(cond2)

## cond1.fpkm.means <- rowMeans(cond1.fpkm)
## cond2.fpkm.means <- rowMeans(cond2.fpkm)

## consensuspathdb <- cbind(geneNames(bg), cond1.fpkm.means, cond2.fpkm.means)
## consensuspathdb <- consensuspathdb[consensuspathdb[,1]!=".",]

consensuspathdb <- cbind(results_genes[6], log2(results_genes[3]))
consensuspathdb <- consensuspathdb[consensuspathdb[,1]!=".",]

write.table(consensuspathdb, col.names=FALSE, row.names = FALSE, paste(workingDirectory, "consensuspathdb-enrichment-analysis.csv" ,sep=""), sep="\t")

results_genes = stattest(bg_filt, feature="gene", covariate="type", getFC=TRUE, meas="FPKM")

## Volcano plot 
if(image.format == "jpeg") {
	jpeg(paste(workingDirectory, 'volcano-plot.jpeg',sep=""), width = image.width, height = image.height)
} else if(image.format == "tiff") {
	tiff(paste(workingDirectory, 'volcano-plot.tiff',sep=""), width = image.width, height = image.height)
} else if(image.format == "png") {
	png(paste(workingDirectory, 'volcano-plot.png',sep=""), width = image.width, height = image.height)
}

colors <- c(gray.colors(4, start = 0.8, end = 0.2, gamma = 2.2, alpha = NULL))
if(image.color){
	colors <- c('black','red','orange','green')
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

conditions <- unique(pheno_data$type)

## Histogram distribution of differential expression values
if(image.format == "jpeg") {
	jpeg(paste(workingDirectory, 'DE-values-distribution.jpeg',sep=""), width = image.width, height = image.height)
} else if(image.format == "tiff") {
	tiff(paste(workingDirectory, 'DE-values-distribution.tiff',sep=""), width = image.width, height = image.height)
} else if(image.format == "png") {
	png(paste(workingDirectory, 'DE-values-distribution.png',sep=""), width = image.width, height = image.height)
}

# draw histogram
hist(results_genes$logfc, breaks=50, col=color, xlab=paste("log2(fold change)",conditions[2], "vs", conditions[1], sep=" "), main="Distribution of differential expression values", xlim=c(-limit, limit))

# Add vertical cut offs
abline(v=c(-2,2), col="black", lwd=2, lty=2)

# Add legend. Remember fold changes are logged (2 base)
legend("topright", "|log2(fold change)|>2", lwd=2, lty=2)

dev.off()

## Correlation plot between treatment/disease and control samples. 
if(image.format == "jpeg") {
	jpeg(paste(workingDirectory, 'FPKM-conditions-correlation.jpeg',sep=""), width = image.width, height = image.height)
} else if(image.format == "tiff") {
	tiff(paste(workingDirectory, 'FPKM-conditions-correlation.tiff',sep=""), width = image.width, height = image.height)
} else if(image.format == "png") {
	png(paste(workingDirectory, 'FPKM-conditions-correlation.png',sep=""), width = image.width, height = image.height)
}

colors <- c('gray65','gray40','gray10')
if(image.color){
	colors <- c('black','green','red')
}

# Identify the genes (rows) with adjusted p-value (i.eq-value) < 0.05 
qsig <- which(results_genes$qval<0.05)

# Convert the matrix to data
gene_expression <-  gexpr(bg_filt)
gene_expression <- as.data.frame(gene_expression)
gene_expression$control <- rowMeans(gene_expression[,which(pheno_data$type==conditions[1])])
gene_expression$treatment <- rowMeans(gene_expression[,which(pheno_data$type==conditions[2])])

x <- log2(gene_expression[,conditions[1]]+1)
y <- log2(gene_expression[,conditions[2]]+1)
plot(x=x, y=y, pch=1, cex=2, col=colors[1], xlab=paste(conditions[1],"log2(FPKM+1)",sep=" "), ylab=paste(conditions[2],"log2(FPKM+1)",sep=" "), main=paste(conditions[2], "vs", conditions[1], "FPKMs", sep=" "))
abline(a=0, b=1)

xqsig <- x[qsig]
yqsig <- y[qsig]
points(x=xqsig, y=yqsig, col=colors[2], pch=19, cex=2)

# Identify significant genes by fold change (genes changed by 4 fold - 2^2).
fsig <- which(abs(results_genes$logfc)>2)
xfsig <- x[fsig]
yfsig <- y[fsig]
points(x=xfsig, y=yfsig, col=colors[3], pch=1, cex=2)

legend_text <- c("Significant by Q value", "Significant by Fold change")
legend("topleft", legend_text,bty="n",pch = c(19,19), col=c(colors[2],colors[3]))

qfsig = intersect(qsig, fsig)
xqfsig <- x[qfsig]
yqfsig <- y[qfsig]

## If there are significative genes
if(!all(is.na(qfsig))){
	# label the significant genes
	textxy(xqfsig,yqfsig, cex=.6, labs=row.names(gene_expression[qfsig,]))	
}

dev.off()

## Density plot
if(image.format == "jpeg") {
	jpeg(paste(workingDirectory, 'FPKM-conditions-density-plot.jpeg',sep=""), width = image.width, height = image.height)
} else if(image.format == "tiff") {
	tiff(paste(workingDirectory, 'FPKM-conditions-density-plot.tiff',sep=""), width = image.width, height = image.height)
} else if(image.format == "png") {
	png(paste(workingDirectory, 'FPKM-conditions-density-plot.png',sep=""), width = image.width, height = image.height)
}
colors <- colorRampPalette(gray.colors(5, start = 1.0, end = 0.3, gamma = 2.2, alpha = NULL))
if(image.color){
	colors <- colorRampPalette(c("white", "blue","red","green","yellow"))
}
par(mfrow=c(1,2))
plot(x,y, xlab=paste(conditions[1],"log2(FPKM+1)",sep=" "), ylab=paste(conditions[2],"log2(FPKM+1)",sep=" "))
smoothScatter(x,y, colramp = colors, xlab=paste(conditions[1],"log2(FPKM+1)",sep=" "), ylab=paste(conditions[2],"log2(FPKM+1)",sep=" "))
title(main=paste(conditions[2], "vs", conditions[1], "FPKMs", sep=" "),outer=TRUE, line = -2)

dev.off()

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
	
	# draw heatmap
	if(image.color){
	  pheatmap(as.matrix(sig_gene_expression), kmeans_k = NA, scale = "row", clustering_distance_rows = "correlation", clustering_method = "complete",annotation_col = phenotype_table , main="Significant genes", filename=paste(workingDirectory, 'heatmap.', image.format, sep=""), width=image.width*0.0033333333333333335, height=image.height*0.0033333333333333335, fontsize=image.width*10/3100, treeheight_row=image.width*50/3100, treeheight_col=image.width*50/3100) 
	}else{
	   group        <- c("gray20", "gray80")
	   names(group) <- c(toString(conditions[1]), toString(conditions[2]))
	   anno_colors  <- list(group = group)
	   pheatmap(as.matrix(sig_gene_expression), kmeans_k = NA, scale = "row", clustering_distance_rows = "correlation", clustering_method = "complete",annotation_col = phenotype_table , main="Significant genes", filename=paste(workingDirectory, 'heatmap.', image.format, sep=""), width=image.width*0.0033333333333333335, height=image.height*0.0033333333333333335, fontsize=image.width*10/3100, treeheight_row=image.width*50/3100, treeheight_col=image.width*50/3100, color=gray.colors(20, start = 0.9, end = 0.2, gamma = 2.2, alpha = NULL), annotation_colors = anno_colors)
	}
}
	
if(image.format == "jpeg") {
	jpeg(paste(workingDirectory, 'pca.jpeg',sep=""), width = image.width, height = image.height)
} else if(image.format == "tiff") {
	tiff(paste(workingDirectory, 'pca.tiff',sep=""), width = image.width, height = image.height)
} else if(image.format == "png") {
	png(paste(workingDirectory, 'pca.png',sep=""), width = image.width, height = image.height)
}
if(!all(is.na(sigpi))){
	
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
