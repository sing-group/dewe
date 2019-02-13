/*
 * #%L
 * DEWE API
 * %%
 * Copyright (C) 2016 - 2019 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola,
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
package org.sing_group.rnaseq.api.entities;

import java.io.File;
import java.io.Serializable;
import java.util.Optional;

/**
 * The interface that defines a sample with a pair of fastq reads files.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface FastqReadsSample extends Serializable {
	/**
	 * Returns the sample name.
	 *
	 * @return the sample name
	 */
	abstract String getName();

	/**
	 * Returns the sample condition.
	 *
	 * @return the sample condition
	 */
	abstract String getCondition();

	/**
	 * Returns the sample reads file (or mate) 1.
	 *
	 * @return the sample reads file (or mate) 1
	 */
	abstract File getReadsFile1();

	/**
	 * Return {@code true} if the sample is paired-end and {@code false}
	 * otherwise.
	 *
	 * @return {@code true} if the sample is paired-end and {@code false}
	 * 			otherwise
	 */
	default boolean isPairedEnd() {
		return this.getReadsFile2().isPresent();
	}

	/**
	 * Returns the sample reads file (or mate) 2 wrapped as an optional because
	 * it may not be present if the sample is single-end.
	 *
	 * @return the sample reads file (or mate) 2
	 */
	abstract Optional<File> getReadsFile2();
}
