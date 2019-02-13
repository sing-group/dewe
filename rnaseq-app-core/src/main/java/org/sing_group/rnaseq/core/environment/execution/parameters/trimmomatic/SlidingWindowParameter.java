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
package org.sing_group.rnaseq.core.environment.execution.parameters.trimmomatic;

import org.sing_group.rnaseq.api.environment.execution.parameters.trimmomatic.TrimmomaticParameter;

public class SlidingWindowParameter implements TrimmomaticParameter {
	private static final long serialVersionUID = 1L;

	public static final String DESCRIPTION = "Performs a sliding window "
		+ "trimming, cutting once the average quality within the window falls "
		+ "below a threshold. By considering multiple bases, a single poor "
		+ "quality base will not cause the removal of high quality data later "
		+ "in the read.";
	public static final String DESCRIPTION_WINDOW_SIZE = 
		"The number of bases to average across.";
	public static final String DESCRIPTION_REQUIRED_QUALITY = 
		"The average quality required.";

	private int windowSize;
	private int requiredQuality;

	public SlidingWindowParameter(int windowSize, int requiredQuality) {
		this.windowSize = windowSize;
		this.requiredQuality = requiredQuality;
	}

	@Override
	public String getParameter() {
		return "SLIDINGWINDOW";
	}

	@Override
	public String getValue() {
		return Integer.toString(this.windowSize) + ":"
			+ Integer.toString(this.requiredQuality);
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@Override
	public boolean isValidValue() {
		return true;
	}
	
	@Override
	public String toString() {
		return this.getParameter() + ":" + this.getValue();
	}
}
