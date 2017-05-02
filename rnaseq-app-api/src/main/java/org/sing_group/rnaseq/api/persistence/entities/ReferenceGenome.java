package org.sing_group.rnaseq.api.persistence.entities;

import java.io.File;
import java.io.Serializable;
import java.util.Optional;

/**
 * The interface that defines a reference genome.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface ReferenceGenome extends Serializable {
	/**
	 * Returns {@code true} if the reference genome is valid (i.e. associated
	 * files exist) and {@code false} otherwise.
	 *
	 * @return {@code true} if the reference genome is valid (i.e. associated
	 *         files exist) and {@code false} otherwise
	 */
	public abstract boolean isValid();

	/**
	 * Returns an string that indicates the genome type.
	 *
	 * @return an string that indicates the genome type
	 */
	public abstract String getType();

	/**
	 * Returns the genome name.
	 *
	 * @return the genome name
	 */
	public abstract String getName();

	/**
	 * Returns the reference genome file.
	 *
	 * @return the reference genome file
	 */
	public abstract File getReferenceGenome();

	/**
	 * Returns the reference genome index wrapped as an {@code Optional} object.
	 *
	 * @return the reference genome index wrapped as an {@code Optional} object
	 */
	public abstract Optional<String> getReferenceGenomeIndex();
}
