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
}
