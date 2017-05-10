package org.sing_group.rnaseq.io.alignment;

import java.io.File;
import java.io.IOException;

import org.sing_group.rnaseq.api.entities.alignment.AlignmentStatistics;

/**
 * A Bowtie2/HISAT2 aligner log parser.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface AlignmentLogParser {
	/**
	 * Parses all strings in {@code lines}.
	 * 
	 * @param lines one or more strings to parse
	 */
	public void parseLogLines(String... lines);

	/**
	 * Parses an entire log file.
	 * 
	 * @param logFile the File to parse
	 * @throws IOException if an error occurs reading the file
	 */
	public void parseLogFile(File logFile) throws IOException;
	
	/**
	 * Returns an {@code AlignmentStatistics} object containing the alignment
	 * statistics.
	 * 
	 * @return an {@code AlignmentStatistics} object containing the alignment
	 *         statistics
	 */
	public AlignmentStatistics getAlignmentStatistics();
}
