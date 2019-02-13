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

public class MaxInfoParameter implements TrimmomaticParameter {
	private static final long serialVersionUID = 1L;

	public static final String DESCRIPTION = "Performs an adaptive quality "
		+ "trim, balancing the benefits of retaining longer reads against the "
		+ "costs of retaining bases with errors.";
	public static final String DESCRIPTION_TARGET_LENGTH = 
		"The read length which is likely to allow the location of the read "
		+ "within the target sequence to be determined.";
	public static final String DESCRIPTION_STRICTNESS = 
		"A value between 0 and 1 that specifies the balance between "
		+ "preserving as much read length as possible vs. removal of "
		+ "incorrect bases. A low value of this parameter (<0.2) favours "
		+ "longer reads, while a high value (>0.8) favours read correctness.";

	private int targetLength;
	private int strictness;

	public MaxInfoParameter(int targetLength, int strictness) {
		this.targetLength = targetLength;
		this.strictness = strictness;
	}

	@Override
	public String getParameter() {
		return "MAXINFO";
	}

	@Override
	public String getValue() {
		return Integer.toString(this.targetLength) + ":"
			+ Integer.toString(this.strictness);
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
