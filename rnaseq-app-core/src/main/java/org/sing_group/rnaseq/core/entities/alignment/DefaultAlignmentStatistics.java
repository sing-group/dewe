/*
 * #%L
 * DEWE Core
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
package org.sing_group.rnaseq.core.entities.alignment;

import org.sing_group.rnaseq.api.entities.alignment.AlignmentStatistics;

/**
 * The default {@code AlignmentStatistics} statistics.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultAlignmentStatistics implements AlignmentStatistics {
	private Double overallAlignmentRate;
	private Double uniquelyAlignedReads;
	private Double multimappedReads;

	/**
	 * Creates a new instance of {@code DefaultAlignmentStatistics} with all
	 * initial values established as {@code Double.NaN}.
	 */
	public DefaultAlignmentStatistics() {
		this(Double.NaN, Double.NaN, Double.NaN);
	}

	/**
	 * Creates a new instance of {@code DefaultAlignmentStatistics} with the
	 * specified initial values.
	 * 
	 * @param overallAlignmentRate the overall alignment rate
	 * @param uniquelyAlignedReads the uniquely aligned reads percentage
	 * @param multimappedReads the multimapped reads percentage
	 */
	public DefaultAlignmentStatistics(Double overallAlignmentRate,
		Double uniquelyAlignedReads, Double multimappedReads
	) {
		this.overallAlignmentRate = overallAlignmentRate;
		this.uniquelyAlignedReads = uniquelyAlignedReads;
		this.multimappedReads = multimappedReads;
	}

	@Override
	public Double getOverallAlignmentRate() {
		return overallAlignmentRate;
	}

	@Override
	public Double getUniquelyAlignedReads() {
		return uniquelyAlignedReads;
	}

	@Override
	public Double getMultimappedReads() {
		return multimappedReads;
	}

	@Override
	public String toString() {
		return asString();
	}
}
