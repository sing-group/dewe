package org.sing_group.rnaseq.core.environment;

import org.sing_group.rnaseq.api.environment.HtseqEnvironment;

/**
 * The default {@code HtseqEnvironment} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultHtseqEnvironment implements HtseqEnvironment {

	private static DefaultHtseqEnvironment INSTANCE;

	/**
	 * Returns the {@code DefaultHtseqEnvironment} singleton instance.
	 * 
	 * @return the {@code DefaultHtseqEnvironment} singleton instance
	 */
	public static DefaultHtseqEnvironment getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DefaultHtseqEnvironment();
		}
		return INSTANCE;
	}

	@Override
	public String getDefaultHtseqCount() {
		return "htseq-count";
	}
}
