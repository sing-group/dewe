## Script input parameters:
##  1.- working directory: path to the directory where results should be stored.
##	This directory must contain two files:
##	- GeneID_to_GeneName.txt: the gene id to gene name mapping.
##	- gene_read_counts_table_all.tsv: the read count matrix, including a
##	header with the sample names and a sample class labels in the last row.
##  2.- the output format of the images: jpeg, tiff or png
##  3.- the width of the images
##  4.- the height of the images
##  5.- color: TRUE for colored images and FALSE for grayscale images

args <- commandArgs(TRUE)

## Parse input parameters
working_dir = args[1];
if(substring(working_dir, nchar(working_dir)) != "/") {
	working_dir <- paste(working_dir, "/", sep="");
}

imagesDirectory <- file.path(working_dir, "user-images/")

if(!dir.exists(imagesDirectory)){
    dir.create(imagesDirectory)
}

setwd(working_dir)

## Read in gene mapping
mapping=read.table(paste(working_dir, "GeneID_to_GeneName.txt", sep=""), header=FALSE, stringsAsFactors=FALSE, row.names=1)
if(!exists("mapping")) {
  mapping <- data.frame(v1 = character(), v2 = character())
}

## Read in count matrix
dat=read.table(paste(working_dir, "gene_read_counts_table_all.tsv", sep=""), header=TRUE, stringsAsFactors=FALSE, row.names=1, check.names=FALSE)

## Read class labels from count matrix file
class <- factor(as.character(dat[nrow(dat),]))

## The last 5 rows are summary data and the last one contains the labels, remove
rawdata=as.data.frame(lapply(dat[1:(length(rownames(dat))-6),], as.numeric))
colnames(rawdata) <- colnames(dat)
rownames(rawdata) <- rownames(dat[1:(length(rownames(dat))-6),])

## Require at least 25% of samples to have count > 25
quant <- apply(rawdata,1,quantile,0.75)
keep <- which((quant >= 25) == 1)
rawdata <- rawdata[keep,]

###################
## Running edgeR ##
###################

## load edgeR
library('edgeR')
library('calibrate')

## Get common gene names
genes=rownames(rawdata)
gene_names=mapping[genes,1]

## Make DGEList object
y <- DGEList(counts=rawdata, genes=genes, group=class)
nrow(y)

## TMM Normalization
y <- calcNormFactors(y)

## Estimate dispersion
y <- estimateCommonDisp(y, verbose=TRUE)
y <- estimateTagwiseDisp(y)

## Differential expression test
et <- exactTest(y)

## Matrix of  DE genes
mat <- cbind(
 genes, gene_names,
 et$table$PValue,
 et$table$logFC
)
colnames(mat) <- c("Gene", "Gene_Name", "Pvalue", "Log_fold_change")

## Print number of up/down significant genes at FDR = 0.05  significance level
summary(de <- decideTestsDGE(et, p=0.05))
detags <- rownames(y)[as.logical(de)]

## Matrix of significantly DE genes
mat_sig <- mat[as.logical(de),]

## Order by log fold change
o <- order(as.numeric(mat[,4]),decreasing=TRUE)
mat_order <- mat[o,]

o_sig <- order(as.numeric(mat_sig[,4]),decreasing=TRUE)
mat_sig_order <- mat_sig[o_sig,]

## Create figures
image.format 	<- args[2]
image.width 	<- as.numeric(args[3])
image.height 	<- as.numeric(args[4])
image.color     <- as.logical(args[5])

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

filt_fc     <- which(abs(as.numeric(mat[,4]))>1)
mat_fc      <- mat[filt_fc,]  
filt_sig_fc <- which(abs(as.numeric(mat_sig[,4]))>1)
mat_sig_fc  <- mat_sig[filt_sig_fc,] 

abs_fcs <- cbind(
 as.integer(abs(as.numeric(mat[,4])))
) 
o <- order(as.numeric(abs_fcs),decreasing=TRUE)
abs_fcs_order <- abs_fcs[o,]
limit <- abs_fcs_order[1]+1

plot(as.numeric(mat[,4]),-log10(as.numeric(mat[,3])), col=colors[1], pch=20, xlim=c(-limit, limit), xlab="log2(fold change)", ylab="-log10(pValue)") 

# highlight the genes with color  
points(as.numeric(mat_fc[,4]),-log10(as.numeric(mat_fc[,3])), col=colors[2], pch=20) 
points(as.numeric(mat_sig[,4]),-log10(as.numeric(mat_sig[,3])), col=colors[3], pch=20) 
points(as.numeric(mat_sig_fc[,4]),-log10(as.numeric(mat_sig_fc[,3])), col=colors[4], pch=20) 

# label the significant genes
textxy(as.numeric(mat_sig_fc[,4]),-log10(as.numeric(mat_sig_fc[,3])), labs=mat_sig_fc[,1], cex=.6)

legend("bottomright", xjust=1, yjust=1, legend=c("|log2(fold change)|>1", "FDR<0.05", "both"), pch=20, col=c(colors[2],colors[3],colors[4]))

dev.off()

##To exit R type the following
quit(save="no")
