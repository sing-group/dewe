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
	private Format format;
	private int width;
	private int height;

	/**
	 * Creates a new {@code DefaultImageConfigurationParameter} instance with
	 * the specified values.
	 *
	 * @param format the image format
	 * @param width the image width
	 * @param height the image height
	 */
	public DefaultImageConfigurationParameter(Format format, int width,
		int height) {
		this.format = format;
		this.width = width;
		this.height = height;
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
}
