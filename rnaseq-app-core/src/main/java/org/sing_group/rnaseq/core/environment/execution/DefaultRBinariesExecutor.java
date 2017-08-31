package org.sing_group.rnaseq.core.environment.execution;

import static java.nio.file.Files.write;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.sing_group.rnaseq.api.environment.binaries.RBinaries;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.RBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.core.environment.execution.check.DefaultRBinariesChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default {@code RBinariesExecutor} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultRBinariesExecutor
	extends AbstractBinariesExecutor<RBinaries> implements RBinariesExecutor {
	private final static Logger LOG = LoggerFactory.getLogger(DefaultRBinariesExecutor.class);  
	
	/**
	 * Creates a new {@code DefaultRBinariesExecutor} instance to execute
	 * the specified {@code RBinaries}.
	 * 
	 * @param binaries the {@code RBinaries} to execute
	 * @throws BinaryCheckException if any of the commands can't be executed
	 */
	public DefaultRBinariesExecutor(RBinaries binaries)
		throws BinaryCheckException {
		this.setBinaries(binaries);
	}

	@Override
	public void setBinaries(RBinaries binaries) throws BinaryCheckException {
		super.setBinaries(binaries);
		this.checkBinaries();
	}

	@Override
	public void checkBinaries() throws BinaryCheckException {
		DefaultRBinariesChecker.checkAll(binaries);
	}

	@Override
	public ExecutionResult runScript(File script, String... args)
		throws ExecutionException, InterruptedException {
		final String[] newArgsArray = new String[args.length+1];
		newArgsArray[0] = script.getAbsolutePath();
		System.arraycopy(args, 0, newArgsArray, 1, args.length);

		return executeCommand(
				LOG,
				this.binaries.getRscript(),
				newArgsArray
			);
	}

	public static File asScriptFile(String script, String name)
			throws IOException {
		Path tmpScriptFile = Files.createTempFile(name, ".R");
		write(tmpScriptFile, script.getBytes());
		return tmpScriptFile.toFile();
	}

	public static String asString(InputStream inputstream) {
		Scanner scanner = new Scanner(inputstream);
		StringBuilder sb = new StringBuilder();
		while (scanner.hasNextLine()) {
			sb.append(scanner.nextLine()).append("\n");
		}
		scanner.close();
		return sb.toString();
	}
}
