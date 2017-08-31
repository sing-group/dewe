package org.sing_group.rnaseq.core.environment.binaries;

import static org.sing_group.rnaseq.core.util.FileUtils.getAbsolutePath;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.Hisat2Binaries;
import org.sing_group.rnaseq.core.environment.DefaultHisat2Environment;

/**
 * The default {@code Hisat2Binaries} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultHisat2Binaries implements Hisat2Binaries {
	private File baseDirectory;
	private String cmdBuildIndex;
	private String cmdAlign;

	/**
	 * Creates a new {@code DefaultHisat2Binaries} with the specified base
	 * directory.
	 * 
	 * @param baseDirectoryPath the directory where the binaries are located
	 */
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

		this.cmdBuildIndex = getAbsolutePath(
			this.baseDirectory, defaultCmdBuildIndex()
		);
		this.cmdAlign = getAbsolutePath(
			this.baseDirectory, defaultCmdAlign()
		);
	}

	private String defaultCmdBuildIndex() {
		return DefaultHisat2Environment.getInstance().getDefaultBuildIndex();
	}

	private String defaultCmdAlign() {
		return DefaultHisat2Environment.getInstance().getDefaultAlign();
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
