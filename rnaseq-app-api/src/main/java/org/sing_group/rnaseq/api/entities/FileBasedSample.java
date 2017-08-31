package org.sing_group.rnaseq.api.entities;

import java.io.File;
import java.io.Serializable;

/**
 * The interface that defines a sample with an associated file.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface FileBasedSample extends Serializable {
	/**
	 * Returns the sample name.
	 *
	 * @return the sample name
	 */
	abstract String getName();

	/**
	 * Returns the sample type.
	 *
	 * @return the sample type
	 */
	abstract String getType();

	/**
	 * Returns the file associated to the sample.
	 *
	 * @return the file associated to the sample
	 */
	abstract File getFile();
}
