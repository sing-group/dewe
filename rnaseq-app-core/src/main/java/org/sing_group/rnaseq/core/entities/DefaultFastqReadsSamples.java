package org.sing_group.rnaseq.core.entities;

import java.util.Collection;
import java.util.LinkedList;

import org.sing_group.rnaseq.api.entities.FastqReadsSample;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;

/**
 * The default {@code FastqReadsSamples} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultFastqReadsSamples
	extends LinkedList<FastqReadsSample> implements FastqReadsSamples
{
	private static final long serialVersionUID = 1L;

	/**
     * Constructs an empty list.
     */
	public DefaultFastqReadsSamples() {
		super();
	}

	/**
	 * Constructs a list containing the elements of the specified samples
	 * collection.
	 *
	 * @param samples the initial elements of the list
	 */
	public DefaultFastqReadsSamples(Collection<FastqReadsSample> samples) {
		super(samples);
	}
}
