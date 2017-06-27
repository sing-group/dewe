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
