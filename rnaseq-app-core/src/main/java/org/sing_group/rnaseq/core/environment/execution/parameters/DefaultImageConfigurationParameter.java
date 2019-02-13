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
package org.sing_group.rnaseq.core.environment.execution.parameters;

import org.sing_group.rnaseq.api.environment.execution.parameters.ImageConfigurationParameter;

/**
 * The default {@code ImageConfigurationParameter} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultImageConfigurationParameter
	implements ImageConfigurationParameter {

	public static ImageConfigurationParameter DEFAULT =
		new DefaultImageConfigurationParameter(Format.JPEG, 1000, 1000, false);

	private Format format;
	private int width;
	private int height;
	private boolean color;

	/**
	 * Creates a new {@code DefaultImageConfigurationParameter} instance with
	 * the specified values.
	 *
	 * @param format the image format
	 * @param width the image width
	 * @param height the image height
	 * @param color whether the image is colored or not
	 */
	public DefaultImageConfigurationParameter(Format format, int width,
		int height, boolean color) {
		this.format = format;
		this.width  = width;
		this.height = height;
		this.color  = color;
	}

	@Override
	public Format getFormat() {
		return format;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public boolean isColored(){
		return color;
	}
}
