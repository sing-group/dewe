## Writes filtered transcripts under a specified p-value
##
## Script input parameters:
##  1.- working directory: path to the directory that contains the ballgown data structure.
##  2.- p-value: the maximum p-value of transcripts.

args <- commandArgs(TRUE)

## Parse input parameters
workingDirectory <- args[1];
if(substring(workingDirectory, nchar(workingDirectory)) != "/") {
	workingDirectory <- paste(workingDirectory, "/", sep="")
}
setwd(workingDirectory)

pValue <- args[2]

library(ballgown)
library(genefilter)
library(dplyr)
library(devtools)

## Create the tables directory if neccessary
tablesDirectory <- file.path(workingDirectory, "user-tables")

if(!dir.exists(tablesDirectory)){
	dir.create(tablesDirectory)
}

## Reload the ballgown data structure
load("bg.rda")

bg_filt = subset (bg,"rowVars(texpr(bg)) > 1", genomesubset=TRUE)
bg_filt_table = texpr(bg_filt, 'all')

results_transcripts = stattest(bg_filt, feature="transcript", covariate="type", getFC=TRUE, meas="FPKM")
results_transcripts = data.frame(geneNames=ballgown::geneNames(bg_filt), geneIDs=ballgown::geneIDs(bg_filt), transcriptNames=ballgown::transcriptNames(bg_filt), results_transcripts)

sig_transcripts = subset(results_transcripts, results_transcripts$pval<pValue)

write.table(sig_transcripts, row.names = FALSE, paste(tablesDirectory, "/transcript_results_sig_", pValue, ".tsv",sep=""), sep="\t")

## Exit the R session
quit(save="no")