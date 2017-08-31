package org.sing_group.rnaseq.core.environment.execution.check;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.sing_group.rnaseq.api.environment.binaries.Bowtie2Binaries;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.api.environment.execution.check.Bowtie2BinariesChecker;

/**
 * The default {@code Bowtie2BinariesChecker} implementation.
 *  
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultBowtie2BinariesChecker implements Bowtie2BinariesChecker {
	private Bowtie2Binaries binaries;

	/**
	 * Creates a new {@code DefaultBowtie2BinariesChecker} instance to check the
	 * specified {@code Bowtie2Binaries}.
	 * 
	 * @param binaries the {@code Bowtie2Binaries} to execute
	 */
	public DefaultBowtie2BinariesChecker(Bowtie2Binaries binaries) {
		this.setBinaries(binaries);
	}

	@Override
	public void setBinaries(Bowtie2Binaries binaries) {
		this.binaries = binaries;
	}

	public static void checkAll(Bowtie2Binaries binaries)
	throws BinaryCheckException {
		new DefaultBowtie2BinariesChecker(binaries).checkAll();
	}

	@Override
	public void checkAll() throws BinaryCheckException {
		this.checkBuildIndex();
		this.checkAlignReads();
	}
	
	@Override
	public void checkBuildIndex() throws BinaryCheckException {
		checkCommand(this.binaries.getBuildIndex());
	}

	@Override
	public void checkAlignReads() throws BinaryCheckException {
		checkCommand(this.binaries.getAlignReads());
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
			if (countLines != 7) {
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
