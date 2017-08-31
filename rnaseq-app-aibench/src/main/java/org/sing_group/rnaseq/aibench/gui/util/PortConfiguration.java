package org.sing_group.rnaseq.aibench.gui.util;

/**
 * Constants to use in the configuration of operation ports.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class PortConfiguration {

	public static final String EXTRAS_BAM_FILES = "selectionMode=files, " +
		"filters=.*\\.bam|:BAM files (.bam)";

	public static final String EXTRAS_SAM_FILES = "selectionMode=files, " +
		"filters=.*\\.sam|:SAM files (.sam)";

	public static final String EXTRAS_GTF_FILES = "selectionMode=files, " +
		"filters=.*\\.gtf|:Gene transfer format (GTF) files (.gtf)";

	public static final String EXTRAS_GENOME_FA_FILES = "selectionMode=files, " +
		"filters=.*\\.fa|:Fasta (FA) reference genome files (.fa)";

	public static final String EXTRAS_FASTQ_FILES = "selectionMode=files, " +
		"filters=" + 
			".*\\.fq|: FASTQ (.fq) format files;" +
			".*\\.fastq|: FASTQ (.fastq) format files;" +
			".*\\.fastq.gz|: Compressed FASTQ (.fastq.gz) format files";
}
