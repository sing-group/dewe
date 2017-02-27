package org.sing_group.rnaseq.core.entities.ballgown;

import java.io.File;

import org.sing_group.rnaseq.api.entities.ballgown.BallgownSample;

public class DefaultBallgownSample implements BallgownSample {
	private static final long serialVersionUID = 1L;
	private String name;
	private String type;
	private File directory;

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
