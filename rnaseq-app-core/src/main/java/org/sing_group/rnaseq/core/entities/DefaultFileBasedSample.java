package org.sing_group.rnaseq.core.entities;

import java.io.File;

import org.sing_group.rnaseq.api.entities.FileBasedSample;

public class DefaultFileBasedSample implements FileBasedSample {
	private static final long serialVersionUID = 1L;
	private String name;
	private String type;
	private File file;

	public DefaultFileBasedSample(String name, String type, File file) {
		this.name = name;
		this.type = type;
		this.file = file;
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
		return this.file;
	}
}
