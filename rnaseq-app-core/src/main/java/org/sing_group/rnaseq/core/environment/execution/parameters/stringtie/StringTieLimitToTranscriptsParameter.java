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
package org.sing_group.rnaseq.core.environment.execution.parameters.stringtie;

import org.sing_group.rnaseq.api.environment.execution.parameters.Parameter;

public class StringTieLimitToTranscriptsParameter implements Parameter {
	private static final long serialVersionUID = 1L;
	public static final String DEFAULT_VALUE = "-e";

	private static StringTieLimitToTranscriptsParameter instance;

	public static StringTieLimitToTranscriptsParameter getInstance() {
		if (instance == null) {
			instance = new StringTieLimitToTranscriptsParameter();
		}
		return instance;
	}

	@Override
	public String getParameter() {
		return DEFAULT_VALUE;
	}

	@Override
	public String getValue() {
		return "";
	}

	@Override
	public String getDescription() {
		return "Limits the processing of read alignments to only estimate and "
			+ "output the assembled transcripts matching the reference "
			+ "transcripts given with the -G option (requires -G, recommended "
			+ "for -B/-b). With this option, read bundles with no reference "
			+ "transcripts will be entirely skipped, which may provide a "
			+ "considerable speed boost when the given set of reference "
			+ "transcripts is limited to a set of target genes, for example. ";
	}

	@Override
	public boolean isValidValue() {
		return true;
	}
}
