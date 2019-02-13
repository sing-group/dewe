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
package org.sing_group.rnaseq.core.environment.execution.check;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.sing_group.rnaseq.api.environment.binaries.FastQcBinaries;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.api.environment.execution.check.FastQcBinariesChecker;

/**
 * The default {@code FastQcBinariesChecker} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultFastQcBinariesChecker implements FastQcBinariesChecker {

	private FastQcBinaries binaries;

	/**
	 * Creates a new {@code DefaultFastQcBinariesChecker} instance to check the
	 * specified {@code FastQcBinaries}.
	 *
	 * @param binaries the {@code FastQcBinaries} to execute
	 */
	public DefaultFastQcBinariesChecker(FastQcBinaries binaries) {
		this.setBinaries(binaries);
	}

	@Override
	public void setBinaries(FastQcBinaries binaries) {
		this.binaries = binaries;
	}

	public static void checkAll(FastQcBinaries binaries)
	throws BinaryCheckException {
		new DefaultFastQcBinariesChecker(binaries).checkAll();
	}

	@Override
	public void checkAll() throws BinaryCheckException {
		this.checkFastQc();
	}

	@Override
	public void checkFastQc() throws BinaryCheckException {
		checkCommand(this.binaries.getFastQc());
	}

	protected static void checkCommand(String command) throws BinaryCheckException {
		final Runtime runtime = Runtime.getRuntime();

		String runCommand = command + " --version";

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

			if (countLines != 1) {
				throw new BinaryCheckException("Unrecognized version output", runCommand);
			}

			final String[] lines = sb.toString().split("\n");

			if (!lines[0].contains("FastQC")) {
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
}
