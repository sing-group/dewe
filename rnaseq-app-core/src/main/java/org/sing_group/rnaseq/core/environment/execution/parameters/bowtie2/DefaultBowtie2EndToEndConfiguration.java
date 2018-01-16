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
package org.sing_group.rnaseq.core.environment.execution.parameters.bowtie2;

import org.sing_group.rnaseq.api.environment.execution.parameters.bowtie2.Bowtie2EndToEndConfiguration;

/**
 * The default {@code Bowtie2EndToEndConfiguration} implementation. It is
 * implemented as an enum that defines the preset options in the end to end
 * mode.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see <a href=
 *      "http://bowtie-bio.sourceforge.net/bowtie2/manual.shtml#options">Bowtie2
 *      reference manual</a>
 */
public enum DefaultBowtie2EndToEndConfiguration
	implements Bowtie2EndToEndConfiguration {
	SENSITIVE("--sensitive", "Same as: -D 15 -R 2 -L 22 -i S,1,1.15 (default in --end-to-end mode)"),
	VERY_SENSITIVE("--very-sensitive", "Same as: -D 20 -R 3 -N 0 -L 20 -i S,1,0.50"),
	FAST("--fast", "Same as: -D 10 -R 2 -N 0 -L 22 -i S,0,2.50"),
	VERY_FAST("--very-fast", "Same as: -D 5 -R 1 -N 0 -L 22 -i S,0,2.50");

	public static final String DEFAULT_VALUE_STR = "--very-sensitive";
	public static final Bowtie2EndToEndConfiguration DEFAULT_VALUE = VERY_SENSITIVE;

	private String parameter;
	private String description;

	DefaultBowtie2EndToEndConfiguration(String parameter, String description) {
		this.parameter = parameter;
		this.description = description;
	}

	@Override
	public String getParameter() {
		return parameter;
	}

	@Override
	public String getValue() {
		return "";
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public boolean isValidValue() {
		return true;
	}

	@Override
	public String toString() {
		return this.parameter;
	}
}
