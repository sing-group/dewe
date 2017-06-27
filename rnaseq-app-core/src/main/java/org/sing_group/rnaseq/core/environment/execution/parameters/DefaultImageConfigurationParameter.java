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
