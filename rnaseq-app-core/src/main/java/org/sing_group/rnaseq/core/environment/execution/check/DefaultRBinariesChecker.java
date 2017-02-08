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

public class DefaultRBinariesChecker implements RBinariesChecker {

	private RBinaries binaries;

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
