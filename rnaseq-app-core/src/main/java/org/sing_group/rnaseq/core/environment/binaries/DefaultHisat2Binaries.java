package org.sing_group.rnaseq.core.environment.binaries;

import static org.sing_group.rnaseq.core.util.FileUtils.getAbsolutePath;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.Hisat2Binaries;
import org.sing_group.rnaseq.core.environment.DefaultHisat2Environment;

public class DefaultHisat2Binaries implements Hisat2Binaries {

	private File baseDirectory;
	private String cmdAlign;

	public DefaultHisat2Binaries(String baseDirectoryPath) {
		this.setBaseDirectory(baseDirectoryPath);
	}

	private void setBaseDirectory(String path) {
		this.setBaseDirectory(
			path == null || path.isEmpty() ? null : new File(path)
		);
	}

	private void setBaseDirectory(File path) {
		this.baseDirectory = path;

		this.cmdAlign = getAbsolutePath(
			this.baseDirectory, defaultCmdAlign()
		);
	}

	private String defaultCmdAlign() {
		return DefaultHisat2Environment.getInstance().getDefaultAlign();
	}

	@Override
	public String getAlignReads() {
		return this.cmdAlign;
	}
}
