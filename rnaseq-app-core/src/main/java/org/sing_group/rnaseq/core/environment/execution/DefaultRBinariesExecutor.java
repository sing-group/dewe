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
