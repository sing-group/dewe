package org.sing_group.rnaseq.core.environment;

import org.sing_group.rnaseq.api.environment.HtseqEnvironment;

public class DefaultHtseqEnvironment implements HtseqEnvironment {

	private static DefaultHtseqEnvironment INSTANCE;

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
