package org.sing_group.rnaseq.core.environment.execution.check;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.sing_group.rnaseq.api.environment.binaries.Hisat2Binaries;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.api.environment.execution.check.Hisat2BinariesChecker;

public class DefaultHisat2BinariesChecker implements Hisat2BinariesChecker {

	private Hisat2Binaries binaries;

	public DefaultHisat2BinariesChecker(Hisat2Binaries binaries) {
		this.setBinaries(binaries);
	}

	@Override
	public void setBinaries(Hisat2Binaries binaries) {
		this.binaries = binaries;
	}

	public static void checkAll(Hisat2Binaries binaries)
	throws BinaryCheckException {
		new DefaultHisat2BinariesChecker(binaries).checkAll();
	}

	@Override
	public void checkAll() throws BinaryCheckException {
		this.checkAlignReads();
		this.checkBuildIndex();
	}

	@Override
	public void checkBuildIndex() throws BinaryCheckException {
		checkCommandVersion(this.binaries.getBuildIndex(), 7);
	}

	@Override
	public void checkAlignReads() throws BinaryCheckException {
		checkCommandVersion(this.binaries.getAlignReads(), 7);
	}

	protected static void checkCommandVersion(String command, int outputLines)
		throws BinaryCheckException {
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
			if (countLines != outputLines) {
				throw new BinaryCheckException("Unrecognized version output", runCommand);
			}

			final String[] lines = sb.toString().split("\n");

			if (!lines[0].contains("version")) {
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
