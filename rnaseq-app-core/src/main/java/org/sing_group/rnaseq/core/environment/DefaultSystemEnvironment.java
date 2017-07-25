package org.sing_group.rnaseq.core.environment;

import org.sing_group.rnaseq.api.environment.SystemEnvironment;

public class DefaultSystemEnvironment implements SystemEnvironment {

	private static DefaultSystemEnvironment INSTANCE;

	public static DefaultSystemEnvironment getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DefaultSystemEnvironment();
		}
		return INSTANCE;
	}

	@Override
	public String getDefaultJoin() {
		return "join";
	}

	@Override
	public String getDefaultSed() {
		return "sed";
	}

	@Override
	public String getDefaultAwk(){
		return "awk";
	}
}
