package org.sing_group.rnaseq.core.environment;

import org.sing_group.rnaseq.api.environment.REnvironment;

public class DefaultREnvironment implements REnvironment {

	private static DefaultREnvironment INSTANCE;

	public static DefaultREnvironment getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DefaultREnvironment();
		}
		return INSTANCE;
	}

	@Override
	public String getDefaultRscript() {
		return "Rscript";
	}
}
