package org.sing_group.rnaseq.core.environment.binaries;

import static org.sing_group.rnaseq.core.util.FileUtils.findParentDirectory;
import static org.sing_group.rnaseq.core.util.FileUtils.getAbsolutePath;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.SystemBinaries;
import org.sing_group.rnaseq.core.environment.DefaultSystemEnvironment;

public class DefaultSystemBinaries implements SystemBinaries {
	private File[] baseDirectories;
	private String cmdJoin;
	private String cmdSed;
	private String cmdEnsgidsToSymbols;

	public DefaultSystemBinaries(String...paths) {
		this.setBaseDirectory(paths);
	}

	private void setBaseDirectory(String...paths) {
		this.baseDirectories = new File[paths.length];
		for(int i = 0; i < paths.length; i++) {
			this.baseDirectories[i] = new File(paths[i]);
		}
		this.cmdJoin = getAbsolutePath(
			findParentDirectory(baseDirectories, defaultJoin()).get(), 
			defaultJoin()
		);
		this.cmdSed = getAbsolutePath(
			findParentDirectory(baseDirectories, defaultSed()).get(), 
			defaultSed()
		);
		this.cmdEnsgidsToSymbols = getAbsolutePath(
			findParentDirectory(baseDirectories, defaultEnsgidsToSymbols()).get(), 
			defaultEnsgidsToSymbols()
		);
	}

	private String defaultJoin() {
		return DefaultSystemEnvironment.getInstance().getDefaultJoin();
	}

	private String defaultSed() {
		return DefaultSystemEnvironment.getInstance().getDefaultSed();
	}

	private String defaultEnsgidsToSymbols() {
		return DefaultSystemEnvironment.getInstance().getDefaultEnsgidsToSymbols();
	}

	@Override
	public String getJoin() {
		return this.cmdJoin;
	}

	@Override
	public String getSed() {
		return this.cmdSed;
	}
	
	@Override
	public String getEnsgidsToSymbols() {
		return this.cmdEnsgidsToSymbols;
	}
}