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
package org.sing_group.rnaseq.api.persistence.entities;

import java.io.File;
import java.io.Serializable;
import java.util.Optional;

/**
 * The interface that defines a reference genome index.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface ReferenceGenomeIndex extends Serializable {
	/**
	 * Returns {@code true} if the reference genome index is valid (i.e. 
	 * index files exist) and {@code false} otherwise.
	 *
	 * @return {@code true} if the reference genome index is valid (i.e. 
	 *         index files exist) and {@code false} otherwise
	 */
	public abstract boolean isValidIndex();

	/**
	 * Returns an string that indicates the genome index type.
	 *
	 * @return an string that indicates the genome index type
	 */
	public abstract String getType();

	/**
	 * Returns the genome name.
	 *
	 * @return the genome name
	 */
	public abstract String getName();

	/**
	 * Returns the reference genome file wrapped as an {@code Optional} object.
	 *
	 * @return the reference genome file wrapped as an {@code Optional} object
	 */
	public abstract Optional<File> getReferenceGenome();

	/**
	 * Returns the reference genome index.
	 *
	 * @return the reference genome index
	 */
	public abstract String getReferenceGenomeIndex();
	
	/**
	 * Returns the quoted reference genome index.
	 *
	 * @return the quoted reference genome index
	 */
	default String getQuotedReferenceGenomeIndex() {
		return "'" + getReferenceGenomeIndex() + "'";
	}
}
