/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola,
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

public class LeadingParameter implements TrimmomaticParameter {
	private static final long serialVersionUID = 1L;

	public static final String DESCRIPTION = "Remove low quality bases from "
		+ "the beginning. As long as a base has a value below this threshold "
		+ "the base is removed and the next base will be investigated.";
	public static final String DESCRIPTION_QUALITY = 
		"The minimum quality required to keep a base.";

	private int quality;

	public LeadingParameter(int quality) {
		this.quality = quality;
	}

	@Override
	public String getParameter() {
		return "LEADING";
	}

	@Override
	public String getValue() {
		return Integer.toString(this.quality);
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
