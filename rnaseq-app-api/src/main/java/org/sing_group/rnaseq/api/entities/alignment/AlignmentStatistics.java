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
