/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2017 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
package org.sing_group.rnaseq.core.environment.execution.check;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

import org.sing_group.rnaseq.api.environment.binaries.RBinaries;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.api.environment.execution.check.RBinariesChecker;

/**
 * The default {@code RBinariesChecker} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultRBinariesChecker implements RBinariesChecker {

	private RBinaries binaries;

	/**
	 * Creates a new {@code DefaultRBinariesChecker} instance to check the
	 * specified {@code RBinaries}.
	 * 
	 * @param binaries the {@code RBinaries} to execute
	 */
	public DefaultRBinariesChecker(RBinaries binaries) {
		this.setBinaries(binaries);
	}

	@Override
	public void setBinaries(RBinaries binaries) {
		this.binaries = binaries;
	}

	public static void checkAll(RBinaries binaries)
	throws BinaryCheckException {
		new DefaultRBinariesChecker(binaries).checkAll();
	}

	@Override
	public void checkAll() throws BinaryCheckException {
		this.checkRscript();
	}
	
	@Override
	public void checkRscript() throws BinaryCheckException {
		final Runtime runtime = Runtime.getRuntime();

		File scriptFile = null;
		try {
			scriptFile = createTmpScriptFile();
		} catch (IOException e) {
			throw new BinaryCheckException("Error while checking version", e, "");
		}
		
		String runCommand = this.binaries.getRscript() +  " --no-save --no-restore " + scriptFile.getAbsolutePath();
		
		try {
			final Process process = runtime.exec(runCommand);
			
			final BufferedReader br = new BufferedReader(
				new InputStreamReader(process.getInputStream()));
			
			StringBuilder sb = new StringBuilder();
			
			String line;
			int countLines = 0;
			while ((line = br.readLine()) != null) {
				sb.append(line).append('\n');
				countLines++;
			}

			if (countLines != 15) {
				throw new BinaryCheckException("Unrecognized version output", runCommand);
			}
			
			final int exitStatus = process.waitFor();
			if (exitStatus != 0) {
				throw new BinaryCheckException(
					"Invalid exit status: " + exitStatus, 
					runCommand
				);
			}
		} catch (IOException e) {
			throw new BinaryCheckException("Error while checking version", e, runCommand);
		} catch (InterruptedException e) {
			throw new BinaryCheckException("Error while checking version", e, runCommand);
		}
	}

	private static File createTmpScriptFile() throws IOException {
		Path tempScript = Files.createTempFile("rbinarieschecker", ".R");
		Files.write(tempScript, "version".getBytes());
		return tempScript.toFile();
	}
}
