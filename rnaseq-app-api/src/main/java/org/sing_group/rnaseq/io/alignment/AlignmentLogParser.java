/*
 * #%L
 * DEWE API
 * %%
 * Copyright (C) 2016 - 2017 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
