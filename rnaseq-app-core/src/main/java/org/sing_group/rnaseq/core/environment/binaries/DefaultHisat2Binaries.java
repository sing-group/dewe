/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2019 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
