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
