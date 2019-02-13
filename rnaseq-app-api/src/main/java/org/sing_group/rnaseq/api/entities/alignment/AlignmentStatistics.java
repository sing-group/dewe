/*
 * #%L
 * DEWE API
 * %%
 * Copyright (C) 2016 - 2019 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
package org.sing_group.rnaseq.api.entities.alignment;

/**
 * The read mapping statistics.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface AlignmentStatistics {
	public static final String OVERALL_ALIGNMENT_RATE = "Overall alignment rate (%)";
	public static final String UNIQUELY_ALIGNED_READS = "Uniquely aligned reads (%)";
	public static final String MULTIMAPPED_READS = "Multimapped reads (%)";

	/**
	 * Returns the overall alignment rate.
	 * 
	 * @return the overall alignment rate
	 */
	public Double getOverallAlignmentRate();

	/**
	 * Returns the uniquely aligned reads percentage.
	 * 
	 * @return the uniquely aligned reads percentage
	 */
	public Double getUniquelyAlignedReads();

	/**
	 * The multimapped reads percentage.
	 * 
	 * @return multimapped reads percentage
	 */
	public Double getMultimappedReads();

	public default String asString() {
		return new StringBuilder()
				.append(OVERALL_ALIGNMENT_RATE)
				.append(getOverallAlignmentRate())
				.append("; ")
				.append(UNIQUELY_ALIGNED_READS)
				.append(getUniquelyAlignedReads())
				.append("; ")
				.append(MULTIMAPPED_READS)
				.append(getMultimappedReads())
				.append("; ")
			.toString();
	}
}
