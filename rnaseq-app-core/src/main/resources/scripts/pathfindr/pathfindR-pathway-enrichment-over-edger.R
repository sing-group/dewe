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

tsv <- read.table(file = paste(workingDirectory,"DE_significant_genes.tsv", sep=""), sep = "\t", header = TRUE)
deleted <- tsv[, -1]
reordered <- deleted[, c(1,3,2)]
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
	
	
	## Read in gene mapping
	mapping=read.table(paste(workingDirectory, "GeneID_to_GeneName.txt", sep=""), header=FALSE, stringsAsFactors=FALSE, row.names=1)
	if(!exists("mapping")) {
	  mapping <- data.frame(v1 = character(), v2 = character())
	}
		
	## Read in count matrix
	dat=read.table(paste(workingDirectory, "gene_read_counts_table_all.tsv", sep=""), header=TRUE, stringsAsFactors=FALSE, row.names=1, check.names=FALSE)
	
	## Read class labels from count matrix file
	class <- factor(as.character(dat[nrow(dat),]))
	
	## The last 5 rows are summary data and the last one contains the labels, remove
	rawdata=as.data.frame(lapply(dat[1:(length(rownames(dat))-6),], as.numeric))
	colnames(rawdata) <- colnames(dat)
	rownames(rawdata) <- rownames(dat[1:(length(rownames(dat))-6),])
	
	counts <- merge(rawdata, mapping, by = 0)
	counts <- counts[,-1]
	counts <- cbind(counts[,ncol(counts)], counts[,-ncol(counts)])	
	colnames(counts) <- c(c("gene"), colnames(counts)[-1])
	counts <- aggregate(.~gene, counts, sum)
	exp_mat <- counts[,-1]
	rownames(exp_mat) <- counts[,1]
	
	conditions <- unique(class)
	
	samples <- colnames(exp_mat)
	
	cases <- samples[class==conditions[2]]
		
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
