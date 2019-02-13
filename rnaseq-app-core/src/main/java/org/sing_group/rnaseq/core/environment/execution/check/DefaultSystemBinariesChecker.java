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

import org.sing_group.rnaseq.api.environment.binaries.SystemBinaries;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.api.environment.execution.check.SystemBinariesChecker;

/**
 * The default {@code SystemBinariesChecker} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultSystemBinariesChecker implements SystemBinariesChecker {

	private SystemBinaries binaries;

	/**
	 * Creates a new {@code DefaultSystemBinariesChecker} instance to check the
	 * specified {@code SystemBinaries}.
	 *
	 * @param binaries the {@code SystemBinaries} to execute
	 */
	public DefaultSystemBinariesChecker(SystemBinaries binaries) {
		this.setBinaries(binaries);
	}

	@Override
	public void setBinaries(SystemBinaries binaries) {
		this.binaries = binaries;
	}

	public static void checkAll(SystemBinaries binaries)
	throws BinaryCheckException {
		new DefaultSystemBinariesChecker(binaries).checkAll();
	}

	@Override
	public void checkAll() throws BinaryCheckException {
		this.checkJoin();
		this.checkSed();
		this.checkAwk();
	}

	@Override
	public void checkJoin() throws BinaryCheckException {
		String runCommand = this.binaries.getJoin() + " --help";
		checkFirstLineCommandStartsWith(runCommand, "Usage: join");
	}

	public void checkFirstLineCommandStartsWith(String runCommand,
		String expectedFirstLineStart
	) throws BinaryCheckException {
		final Runtime runtime = Runtime.getRuntime();

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
			if (countLines > 0 && sb.toString().startsWith(expectedFirstLineStart)) {
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

	//Older version is 12
	@Override
	public void checkSed() throws BinaryCheckException {
		String runCommand = this.binaries.getSed() + " --version";
		checkCommand(runCommand, 11);
	}

	@Override
	public void checkAwk() throws BinaryCheckException {
		String runCommand = this.binaries.getAwk() + " -W version";
		checkCommand(runCommand, 2);
	}

	public void checkCommand(String runCommand, int expectedOutputLines)
		throws BinaryCheckException {
		final Runtime runtime = Runtime.getRuntime();

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
			if (countLines != expectedOutputLines) {
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
