package org.sing_group.rnaseq.core.environment.binaries;

import static org.sing_group.rnaseq.core.util.FileUtils.getAbsolutePath;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.RBinaries;
import org.sing_group.rnaseq.core.environment.DefaultREnvironment;

public class DefaultRBinaries implements RBinaries {
	private File baseDirectory;
	private String cmdRscript;

	public DefaultRBinaries(String path) {
		this.setBaseDirectory(
			path == null || path.isEmpty() ? null : new File(path)
		);
	}

	private void setBaseDirectory(File path) {
		this.baseDirectory = path;

		this.cmdRscript = getAbsolutePath(
			this.baseDirectory, defaultRscript()
		);
	}

	private String defaultRscript() {
		return DefaultREnvironment.getInstance().getDefaultRscript();
	}

	@Override
	public String getRscript() {
		return this.cmdRscript;
	}
}
