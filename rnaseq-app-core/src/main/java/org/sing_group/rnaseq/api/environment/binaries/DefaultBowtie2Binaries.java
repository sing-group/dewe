package org.sing_group.rnaseq.api.environment.binaries;

import static org.sing_group.rnaseq.core.util.FileUtils.getAbsolutePath;

import java.io.File;

import org.sing_group.rnaseq.api.environment.DefaultBowtie2Environment;

public class DefaultBowtie2Binaries implements Bowtie2Binaries {

	private File baseDirectory;
	private String cmdBuildIndex;
	private String cmdAlign;

	public DefaultBowtie2Binaries(String baseDirectoryPath) {
		this.setBaseDirectory(baseDirectoryPath);
	}

	private void setBaseDirectory(String path) {
		this.setBaseDirectory(
			path == null || path.isEmpty() ? null : new File(path)
		);
	}

	private void setBaseDirectory(File path) {
		this.baseDirectory = path;

		this.cmdBuildIndex = getAbsolutePath(
			this.baseDirectory, defaultCmdBuildIndex()
		);
		this.cmdAlign = getAbsolutePath(
			this.baseDirectory, defaultCmdAlign()
		);
	}

	private String defaultCmdBuildIndex() {
		return DefaultBowtie2Environment.getInstance().getDefaultBuildIndex();
	}
	
	private String defaultCmdAlign() {
		return DefaultBowtie2Environment.getInstance().getDefaultAlign();
	}

	@Override
	public File getBaseDirectory() {
		return this.baseDirectory;
	}

	@Override
	public String getBuildIndex() {
		return this.cmdBuildIndex;
	}

	@Override
	public String getAlignReads() {
		return this.cmdAlign;
	}
}
