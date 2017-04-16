package org.sing_group.rnaseq.core.environment.execution.parameters.bowtie2;

import org.sing_group.rnaseq.api.environment.execution.parameters.bowtie2.Bowtie2EndToEndConfiguration;

public enum DefaultBowtie2EndToEndConfiguration
	implements Bowtie2EndToEndConfiguration {
	SENSITIVE("--sensitive", "Same as: -D 15 -R 2 -L 22 -i S,1,1.15 (default in --end-to-end mode)"),
	VERY_SENSITIVE("--very-sensitive", "Same as: -D 20 -R 3 -N 0 -L 20 -i S,1,0.50"),
	FAST("--fast", "Same as: -D 10 -R 2 -N 0 -L 22 -i S,0,2.50"),
	VERY_FAST("--very-fast", "Same as: -D 5 -R 1 -N 0 -L 22 -i S,0,2.50");

	private String parameter;
	private String description;

	DefaultBowtie2EndToEndConfiguration(String parameter, String description) {
		this.parameter = parameter;
		this.description = description;
	}

	@Override
	public String getParameter() {
		return parameter;
	}

	@Override
	public String getValue() {
		return "";
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public boolean isValidValue() {
		return true;
	}

	@Override
	public String toString() {
		return this.parameter;
	}
}
