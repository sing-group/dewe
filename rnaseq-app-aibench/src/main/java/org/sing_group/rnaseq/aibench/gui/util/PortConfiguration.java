/*
 * #%L
 * DEWE
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
 * 			Borja Sánchez, and Anália Lourenço
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
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
