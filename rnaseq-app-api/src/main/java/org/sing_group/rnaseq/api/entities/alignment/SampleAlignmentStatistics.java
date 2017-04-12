package org.sing_group.rnaseq.api.entities.alignment;

/**
 * The read mapping statistics associated to a sample name.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface SampleAlignmentStatistics {
	/**
	 * Returns the sample name.
	 * 
	 * @return the sample name
	 */
	public String getSampleName();

	/**
	 * Returns the {@code AlignmentStatistics}.
	 * 
	 * @return the {@code AlignmentStatistics}.
	 */
	public AlignmentStatistics getStatistics();
}
