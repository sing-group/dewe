/*
 * #%L
 * DEWE Core
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
