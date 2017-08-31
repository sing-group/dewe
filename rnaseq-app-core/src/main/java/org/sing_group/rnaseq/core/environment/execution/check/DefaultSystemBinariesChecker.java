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

	@Override
	public void checkSed() throws BinaryCheckException {
		String runCommand = this.binaries.getSed() + " --version";
		checkCommand(runCommand, 12);
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
