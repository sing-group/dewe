package org.sing_group.rnaseq.api.entities;

import java.io.File;
import java.io.Serializable;

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
	 * Returns the sample reads file (or mate) 2.
	 *
	 * @return the sample reads file (or mate) 2
	 */
	abstract File getReadsFile2();
}
