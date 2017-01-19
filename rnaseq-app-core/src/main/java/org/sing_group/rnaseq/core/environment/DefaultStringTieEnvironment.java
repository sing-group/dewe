package org.sing_group.rnaseq.core.environment;

import org.sing_group.rnaseq.api.environment.StringTieEnvironment;

public class DefaultStringTieEnvironment implements StringTieEnvironment {

	private static DefaultStringTieEnvironment INSTANCE;

	public static DefaultStringTieEnvironment getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DefaultStringTieEnvironment();
		}
		return INSTANCE;
	}

	@Override
	public String getDefaultStringTie() {
		return "stringtie";
	}
}
