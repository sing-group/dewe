# Script input parameters:
#  1.- working directory: path to the directory where results should be stored.
#  2.- phenotype data csv file: the name of the csv file containing the 
#      phenotype data structure (it should be located in the working directory).

library(ballgown)
library(genefilter)
library(dplyr)
library(devtools)

args <- commandArgs(TRUE)

# Parse input parameters
workingDirectory <- args[1];
if(substring(workingDirectory, nchar(workingDirectory)) != "/") {
	workingDirectory <- paste(workingDirectory, "/", sep="");
}
phenoDataPrefix <- args[2];
if(substring(phenoDataPrefix, nchar(phenoDataPrefix)-3) == ".csv") {
	phenoDataPrefix <- tools::file_path_sans_ext(phenoDataPrefix);
}

# Load phenotype data from a file we saved in the current working directory
pheno_data = read.csv(paste(workingDirectory, phenoDataPrefix,".csv",sep=""))

# Load ballgown data structure and save it to a variable "bg"
bg = ballgown(samples=as.vector(pheno_data$path), pData=pheno_data)

# Load all attributes including gene name
bg_table = texpr(bg, 'all')
bg_gene_names = unique(bg_table[, 9:10])

# Save the ballgown object to a file for later use
save(bg, file=paste(workingDirectory, 'bg.rda',sep=""))

# Perform differential expression (DE) analysis with no filtering
results_transcripts = stattest(bg, feature="transcript", covariate="type", getFC=TRUE, meas="FPKM")
results_transcripts = data.frame(geneNames=ballgown::geneNames(bg), geneIDs=ballgown::geneIDs(bg), transcriptNames=ballgown::transcriptNames(bg), results_transcripts)
results_genes = stattest(bg, feature="gene", covariate="type", getFC=TRUE, meas="FPKM")
results_genes = merge(results_genes,bg_gene_names,by.x=c("id"),by.y=c("gene_id"))

# Save a tab delimited file for both the transcript and gene results
write.table(results_transcripts,row.names = FALSE, paste(workingDirectory, phenoDataPrefix,"_transcript_results.tsv",sep=""),sep="\t")
write.table(results_genes, row.names = FALSE, paste(workingDirectory, phenoDataPrefix,"_gene_results.tsv",sep=""), sep="\t")

# Filter low-abundance genes. Here we remove all transcripts with a variance across the samples of less than one
bg_filt = subset (bg,"rowVars(texpr(bg)) > 1", genomesubset=TRUE)

# Load all attributes including gene name
bg_filt_table = texpr(bg_filt , 'all')
bg_filt_gene_names = unique(bg_filt_table[, 9:10])

# Perform DE analysis now using the filtered data
results_transcripts = stattest(bg_filt, feature="transcript", covariate="type", getFC=TRUE, meas="FPKM")
results_transcripts = data.frame(geneNames=ballgown::geneNames(bg_filt), geneIDs=ballgown::geneIDs(bg_filt), transcriptNames=ballgown::transcriptNames(bg_filt), results_transcripts)
results_genes = stattest(bg_filt, feature="gene", covariate="type", getFC=TRUE, meas="FPKM")
results_genes = merge(results_genes,bg_filt_gene_names,by.x=c("id"),by.y=c("gene_id"))

# Output the filtered list of genes and transcripts and save to tab delimited files
write.table(results_transcripts, row.names = FALSE, paste(workingDirectory, phenoDataPrefix,"_transcript_results_filtered.tsv",sep=""), sep="\t")
write.table(results_genes, row.names = FALSE, paste(workingDirectory, phenoDataPrefix,"_gene_results_filtered.tsv",sep=""), sep="\t")

# Identify the significant genes with p-value < 0.05
sig_transcripts = subset(results_transcripts,results_transcripts$pval<0.05)
sig_genes = subset(results_genes,results_genes$pval<0.05)

# Output the signifant gene results to a pair of tab delimited files
write.table(sig_transcripts, row.names = FALSE, paste(workingDirectory, phenoDataPrefix,"_transcript_results_sig.tsv",sep=""), sep="\t")
write.table(sig_genes,row.names = FALSE,  paste(workingDirectory, phenoDataPrefix,"_gene_results_sig.tsv",sep=""), sep="\t")

# Exit the R session
quit(save="no")