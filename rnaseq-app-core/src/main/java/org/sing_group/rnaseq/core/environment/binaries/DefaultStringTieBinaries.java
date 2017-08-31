package org.sing_group.rnaseq.core.environment.binaries;

import static org.sing_group.rnaseq.core.util.FileUtils.getAbsolutePath;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.StringTieBinaries;
import org.sing_group.rnaseq.core.environment.DefaultStringTieEnvironment;

/**
 * The default {@code StringTieBinaries} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultStringTieBinaries implements StringTieBinaries {
	private File baseDirectory;
	private String cmdStringTie;

	/**
	 * Creates a new {@code DefaultStringTieBinaries} with the specified base
	 * directory.
	 * 
	 * @param baseDirectoryPath the directory where the binaries are located
	 */
	public DefaultStringTieBinaries(String baseDirectoryPath) {
		this.setBaseDirectory(baseDirectoryPath);
	}

	private void setBaseDirectory(String path) {
		this.setBaseDirectory(
			path == null || path.isEmpty() ? null : new File(path)
		);
	}

	private void setBaseDirectory(File path) {
		this.baseDirectory = path;

		this.cmdStringTie = getAbsolutePath(
			this.baseDirectory, defaultStringTie()
		);
	}

	private String defaultStringTie() {
		return DefaultStringTieEnvironment.getInstance().getDefaultStringTie();
	}

	@Override
	public String getStringTie() {
		return this.cmdStringTie;
	}
}
