package org.sing_group.rnaseq.core.entities;

import java.io.File;

import org.sing_group.rnaseq.api.entities.FastqReadsSample;

public class DefaultFastqReadsSample implements FastqReadsSample {
	private static final long serialVersionUID = 1L;

	private String name;
	private String condition;
	private File readsFile1;
	private File readsFile2;

	public DefaultFastqReadsSample(String name, String type, File readsFile1,
		File readsFile2
	) {
		this.name = name;
		this.condition = type;
		this.readsFile1 = readsFile1;
		this.readsFile2 = readsFile2;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getCondition() {
		return this.condition;
	}

	@Override
	public File getReadsFile1() {
		return this.readsFile1;
	}

	@Override
	public File getReadsFile2() {
		return this.readsFile2;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb
			.append("[DefaultFastqReadsSample] ")
			.append("Name: ")
			.append(getName())
			.append("; Condition: ")
			.append(getCondition())
			.append("; F1: ")
			.append(getReadsFile1())
			.append("; F2: ")
			.append(getReadsFile2());
		return sb.toString();
	}
}
