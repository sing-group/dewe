package org.sing_group.rnaseq.core.entities;

import java.io.File;

import org.sing_group.rnaseq.api.entities.FastqReadsSample;

/**
 * The default {@code FastqReadsSample} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultFastqReadsSample implements FastqReadsSample {
	private static final long serialVersionUID = 1L;

	private String name;
	private String condition;
	private File readsFile1;
	private File readsFile2;

	/**
	 * Creates a new {@code DefaultFastqReadsSample} with the specified initial
	 * values.
	 *
	 * @param name the sample name
	 * @param type the sample condition
	 * @param readsFile1 the sample reads file (or mate) 1
	 * @param readsFile2 the sample reads file (or mate) 2
	 */
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

	@Override
	public boolean equals(Object aThat) {
		if (aThat == null) {
			return false;
		}
		if (!(aThat instanceof DefaultFastqReadsSample)) {
			return false;
		}
		DefaultFastqReadsSample that = (DefaultFastqReadsSample) aThat;

		return this.name.equals(that.name)
			&& this.condition.equals(that.condition)
			&& this.readsFile1.equals(that.readsFile1)
			&& that.readsFile2.equals(that.readsFile2);
	}
}
