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
