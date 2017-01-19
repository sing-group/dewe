package org.sing_group.rnaseq.core.environment.execution.check;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.sing_group.rnaseq.api.environment.binaries.StringTieBinaries;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.api.environment.execution.check.StringTieBinariesChecker;

public class DefaultStringTieBinariesChecker implements StringTieBinariesChecker {

	private StringTieBinaries binaries;

	public DefaultStringTieBinariesChecker(StringTieBinaries binaries) {
		this.setBinaries(binaries);
	}

	@Override
	public void setBinaries(StringTieBinaries binaries) {
		this.binaries = binaries;
	}

	public static void checkAll(StringTieBinaries binaries)
	throws BinaryCheckException {
		new DefaultStringTieBinariesChecker(binaries).checkAll();
	}

	@Override
	public void checkAll() throws BinaryCheckException {
		this.checkStringTie();
	}

	@Override
	public void checkStringTie() throws BinaryCheckException {
		checkCommand(this.binaries.getStringTie());
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
