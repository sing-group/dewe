## Script input parameters:
##  1.- working directory: path to the directory where Ballgown results are stored.
##  2.- output directory: path to the directory where results should be stored.
##  3.- human genes: boolean indicating whether the input genes are human gene symbols or not.
##  4.- gene sets: the gene sets to be used for enrichment analysis:
##                 "KEGG", "Reactome", "BioCarta", "GO-All", "GO-BP", "GO-CC", "GO-MF".
##  5.- clustering method: the agglomeration method to be used to cluster pathways:
##                         "ward.D", "ward.D2", "single", "complete", "average" (= UPGMA),
##                         "mcquitty" (= WPGMA), "median" (= WPGMC) or "centroid" (= UPGMC).

suppressPackageStartupMessages(library(pathfindR))

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

tsv <- read.table(file = paste(workingDirectory,"phenotype-data_gene_results_sig.tsv", sep=""), sep = "\t", header = TRUE)
nodot <- tsv[tsv[ ,6]!='.', ]
unique <- nodot[!duplicated(nodot), ]
deleted <- unique[, -c(1,2,5)]
reordered <- deleted[, c(3,1,2)]
reordered[, 2] <- log(reordered[2], 2)

human <- as.logical(args[3]);

pdf(paste(outputDirectory, 'pathfindR_enrichment_summary.pdf',sep=""))
RA_output <- run_pathfindR(reordered, output = paste(outputDirectory,"results", sep=""), human_genes = human, gene_sets = args[4])
dev.off()

write.table(RA_output, row.names = FALSE, paste(outputDirectory, "pathfindR_enriched_pathways.tsv",sep=""), sep="\t")

if(nrow(RA_output) != 0){

	pdf(paste(outputDirectory, 'pathfindR_clustering.pdf',sep=""))
	RA_clustered <- cluster_pathways(RA_output, plot_dend = TRUE, plot_hmap = TRUE, plot_clusters_graph = TRUE, hclu_method = args[5])
	tmp <- cluster_pathways(RA_output, method = "fuzzy", hclu_method = args[5])
	dev.off()
	
	write.table(RA_clustered, row.names = FALSE, paste(outputDirectory, "pathfindR_clustered_pathways.tsv",sep=""), sep="\t")
	
	representative <- RA_clustered[RA_clustered$Status == "Representative", ]
	fpkms <- read.table(file = paste(workingDirectory,"phenotype-data_gene_results_sig_fpkm_plus_1.tsv", sep=""), sep = "\t", header = TRUE)
	fpkms <- fpkms[,-c(2,3)]
	fpkms <- fpkms[fpkms[ ,1]!='.', ]
	fpkms <- aggregate(.~gene, fpkms, sum)
	cols <- colnames(fpkms)
	colnames(fpkms) <- sub("FPKM.","",cols)
	exp_mat <- fpkms[,-1]
	rownames(exp_mat) <- fpkms[,1]
	phenodata <- read.table(file = paste(workingDirectory,"phenotype-data.csv", sep="/"), sep = ",", header = TRUE)
	cases <- phenodata[phenodata[,2]!=phenodata$type[1],]
	cases <- cases[,1]
	cases <- t(cases)
	conditions <- unique(phenodata$type)
	
	pdf(paste(outputDirectory, 'pathfindR_score_matrix.pdf',sep=""))
	score_matrix <- calculate_pw_scores(RA_clustered, exp_mat, cases, case_control_titles = c(toString(conditions[2]), toString(conditions[1])))
	score_matrix_representative <- calculate_pw_scores(representative, exp_mat, cases, case_control_titles = c(toString(conditions[2]), toString(conditions[1])))
	dev.off()
	
	write.table(score_matrix, row.names = TRUE, paste(outputDirectory, "pathfindR_score_matrix.tsv",sep=""), sep="\t")
	write.table(score_matrix_representative, row.names = TRUE, paste(outputDirectory, "pathfindR_score_matrix_representative.tsv",sep=""), sep="\t")
}else{
	write.table(RA_output, row.names = FALSE, paste(outputDirectory, "pathfindR_clustered_pathways.tsv",sep=""), sep="\t")
}
## Exit the R session
quit(save="no")
