package org.sing_group.rnaseq.core.environment.binaries;

import static org.sing_group.rnaseq.core.util.FileUtils.getAbsolutePath;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.HtseqBinaries;
import org.sing_group.rnaseq.core.environment.DefaultHtseqEnvironment;

/**
 * The default {@code HtseqBinaries} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultHtseqBinaries implements HtseqBinaries {
	private File baseDirectory;
	private String cmdHtseqCount;

	/**
	 * Creates a new {@code DefaultHtseqBinaries} with the specified base
	 * directory.
	 * 
	 * @param baseDirectoryPath the directory where the binaries are located
	 */
	public DefaultHtseqBinaries(String baseDirectoryPath) {
		this.setBaseDirectory(baseDirectoryPath);
	}

	private void setBaseDirectory(String path) {
		this.setBaseDirectory(
			path == null || path.isEmpty() ? null : new File(path)
		);
	}

	private void setBaseDirectory(File path) {
		this.baseDirectory = path;

		this.cmdHtseqCount = getAbsolutePath(
			this.baseDirectory, defaultHtseqCount()
		);
	}

	private String defaultHtseqCount() {
		return DefaultHtseqEnvironment.getInstance().getDefaultHtseqCount();
	}

	@Override
	public String getHtseqCount() {
		return this.cmdHtseqCount;
	}
}
