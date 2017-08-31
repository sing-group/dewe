package org.sing_group.rnaseq.core.entities;

import java.util.Collection;
import java.util.LinkedList;

import org.sing_group.rnaseq.api.entities.FileBasedSample;
import org.sing_group.rnaseq.api.entities.FileBasedSamples;

/**
 * The default {@code FileBasedSamples} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 * @param <T> the type of the samples in the list
 */
public class DefaultFileBasedSamples<T extends FileBasedSample>
	extends LinkedList<T> implements FileBasedSamples<T> {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a list containing the elements of the specified collection.
	 * 
	 * @param samples the collection whose samples are to be placed into this 
	 * 		  list
	 */
	public DefaultFileBasedSamples(Collection<T> samples) {
		super(samples);
	}
}
