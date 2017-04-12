package org.sing_group.rnaseq.core.entities.alignment;

import org.sing_group.rnaseq.api.entities.alignment.AlignmentStatistics;
import org.sing_group.rnaseq.api.entities.alignment.SampleAlignmentStatistics;

/**
 * The default {@code SampleAlignmentStatistics} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultSampleAlignmentStatistics
	implements SampleAlignmentStatistics {

	private String sampleName;
	private AlignmentStatistics statistics;

	/**
	 * Creates a new instance of {@code DefaultAlignmentStatistics} with the
	 * specified values.
	 * 
	 * @param sampleName the sample name
	 * @param statistics the {@code AlignmentStatistics}
	 */
	public DefaultSampleAlignmentStatistics(String sampleName,
		AlignmentStatistics statistics
	) {
		this.sampleName = sampleName;
		this.statistics = statistics;
	}

	@Override
	public String getSampleName() {
		return sampleName;
	}

	@Override
	public AlignmentStatistics getStatistics() {
		return statistics;
	}
}
