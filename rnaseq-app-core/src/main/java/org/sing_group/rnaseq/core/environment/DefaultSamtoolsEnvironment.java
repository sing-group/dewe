package org.sing_group.rnaseq.core.environment;

import org.sing_group.rnaseq.api.environment.SamtoolsEnvironment;

public class DefaultSamtoolsEnvironment implements SamtoolsEnvironment {

	private static DefaultSamtoolsEnvironment INSTANCE;

	public static DefaultSamtoolsEnvironment getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DefaultSamtoolsEnvironment();
		}
		return INSTANCE;
	}

	@Override
	public String getDefaultSamToBam() {
		return "samtools";
	}
}
