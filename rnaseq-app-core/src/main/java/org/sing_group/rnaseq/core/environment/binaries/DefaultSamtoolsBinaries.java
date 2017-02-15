package org.sing_group.rnaseq.core.environment.binaries;

import static org.sing_group.rnaseq.core.util.FileUtils.getAbsolutePath;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.SamtoolsBinaries;
import org.sing_group.rnaseq.core.environment.DefaultSamtoolsEnvironment;

public class DefaultSamtoolsBinaries implements SamtoolsBinaries {
	private File baseDirectory;
	private String cmdSamToBam;

	public DefaultSamtoolsBinaries(String path) {
		this.setBaseDirectory(
			path == null || path.isEmpty() ? null : new File(path)
		);
	}

	private void setBaseDirectory(File path) {
		this.baseDirectory = path;

		this.cmdSamToBam = getAbsolutePath(
			this.baseDirectory, defaultSamToBam()
		);
	}

	private String defaultSamToBam() {
		return DefaultSamtoolsEnvironment.getInstance().getDefaultSamToBam();
	}

	@Override
	public String getSamToBam() {
		return this.cmdSamToBam;
	}

}
