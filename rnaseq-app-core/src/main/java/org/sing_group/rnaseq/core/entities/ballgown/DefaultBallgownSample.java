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
package org.sing_group.rnaseq.core.entities.ballgown;

import java.io.File;

import org.sing_group.rnaseq.api.entities.ballgown.BallgownSample;

/**
 * The default {@code BallgownSample} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultBallgownSample implements BallgownSample {
	private static final long serialVersionUID = 1L;
	private String name;
	private String type;
	private File directory;

	/**
	 * Creates a new {@code DefaultBallgownSample} instance.
	 * 
	 * @param name the sample name
	 * @param type the sample type
	 * @param directory the directory where the sample is located
	 */
	public DefaultBallgownSample(String name, String type, File directory) {
		this.name = name;
		this.type = type;
		this.directory = directory;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public File getFile() {
		return this.directory;
	}
}
