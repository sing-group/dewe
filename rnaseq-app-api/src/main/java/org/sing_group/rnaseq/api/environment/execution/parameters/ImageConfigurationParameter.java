/*
 * #%L
 * DEWE API
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
package org.sing_group.rnaseq.api.environment.execution.parameters;

/**
 * The configuration of an image to be exported.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface ImageConfigurationParameter {

	/**
	 * The supported image formats.
	 *
	 * @author Hugo López-Fernández
	 * @author Aitor Blanco-Míguez
	 *
	 */
	public enum Format {
		PNG("png"), JPEG("jpeg"), TIFF("tiff");

		private String extension;

		Format(String extension) {
			this.extension = extension;
		};

		public String getExtension() {
			return extension;
		}
	}

	/**
	 * Returns the {@code Format} of the image.
	 *
	 * @return the {@code Format} of the image
	 */
	public abstract Format getFormat();

	/**
	 * Returns the image width.
	 *
	 * @return the image width
	 */
	public abstract int getWidth();

	/**
	 * Returns the image height.
	 *
	 * @return the image height
	 */
	public abstract int getHeight();

	/**
	 * Returns whether the image is colored or not.
	 *
	 * @return whether the image is colored or not
	 */
	public abstract boolean isColored();
}
