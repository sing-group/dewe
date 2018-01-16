/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola,
 * 			Borja Sánchez, and Anália Lourenço
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.sing_group.rnaseq.core.entities;

import java.io.File;
import java.util.Optional;

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
	 * Creates a new single-end {@code DefaultFastqReadsSample} with the 
	 * specified initial values.
	 *
	 * @param name the sample name
	 * @param type the sample condition
	 * @param readsFile the sample reads file
	 */
	public DefaultFastqReadsSample(String name, String type, File readsFile) {
		this.name = name;
		this.condition = type;
		this.readsFile1 = readsFile;
		this.readsFile2 = null;
	}

	/**
	 * Creates a new paired-end {@code DefaultFastqReadsSample} with the 
	 * specified initial values.
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
	public Optional<File> getReadsFile2() {
		return Optional.ofNullable(this.readsFile2);
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
			&& (
				this.isPairedEnd() == that.isPairedEnd() ||
				this.getReadsFile2().get().equals(that.getReadsFile2().get())
			);
	}
}
