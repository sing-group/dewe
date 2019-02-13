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
package org.sing_group.rnaseq.core.entities.edgeR;

import java.util.Collection;

import org.sing_group.rnaseq.api.entities.edger.EdgeRSample;
import org.sing_group.rnaseq.api.entities.edger.EdgeRSamples;
import org.sing_group.rnaseq.core.entities.DefaultFileBasedSamples;

/**
 * The default {@code EdgeRSamples} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultEdgeRSamples extends DefaultFileBasedSamples<EdgeRSample>
	implements EdgeRSamples {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a list containing the elements of the specified collection.
	 * 
	 * @param samples the collection whose samples are to be placed into this 
	 * 		  list
	 */
	public DefaultEdgeRSamples(Collection<EdgeRSample> samples) {
		super(samples);
	}
}
