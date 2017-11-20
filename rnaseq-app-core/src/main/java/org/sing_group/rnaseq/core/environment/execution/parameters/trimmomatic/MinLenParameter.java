/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2017 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola,
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

public class MinLenParameter implements TrimmomaticParameter {
	private static final long serialVersionUID = 1L;

	public static final String DESCRIPTION = "Removes reads that "
		+ "fall below the specified minimal length. If required, it should "
		+ "normally be after all other processing steps. Reads removed by this "
		+ "step will be counted and included in the „dropped reads‟ count "
		+ "presented in the trimmomatic summary.";
	public static final String DESCRIPTION_LENGTH = 
		"The minimum length of reads to keep.";

	private int length;

	public MinLenParameter(int length) {
		this.length = length;
	}

	@Override
	public String getParameter() {
		return "MINLEN";
	}

	@Override
	public String getValue() {
		return Integer.toString(this.length);
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
