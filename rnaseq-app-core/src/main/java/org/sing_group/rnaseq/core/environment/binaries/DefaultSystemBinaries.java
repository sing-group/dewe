/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
 * 			Borja Sánchez, and Anália Lourenço
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.sing_group.rnaseq.core.environment.binaries;

import static org.sing_group.rnaseq.core.util.FileUtils.findParentDirectory;
import static org.sing_group.rnaseq.core.util.FileUtils.getAbsolutePath;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.SystemBinaries;
import org.sing_group.rnaseq.core.environment.DefaultSystemEnvironment;

/**
 * The default {@code SystemBinaries} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultSystemBinaries implements SystemBinaries {
	private File[] baseDirectories;
	private String cmdJoin;
	private String cmdSed;
	private String cmdAwk;

	/**
	 * Creates a new {@code DefaultSystemBinaries} with the specified base
	 * directories.
	 * 
	 * @param paths the directories where the binaries are located
	 */
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
		this.cmdAwk = getAbsolutePath(
			findParentDirectory(baseDirectories, defaultAwk()).get(),
			defaultAwk()
		);
	}

	private String defaultJoin() {
		return DefaultSystemEnvironment.getInstance().getDefaultJoin();
	}

	private String defaultSed() {
		return DefaultSystemEnvironment.getInstance().getDefaultSed();
	}

	private String defaultAwk(){
		return DefaultSystemEnvironment.getInstance().getDefaultAwk();
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
	public String getAwk(){
		return this.cmdAwk;
	}
}
