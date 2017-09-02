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
package org.sing_group.rnaseq.core.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.SamtoolsBinaries;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.SamtoolsBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.core.environment.execution.check.DefaultSamtoolsBinariesChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default {@code SamtoolsBinariesExecutor} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultSamtoolsBinariesExecutor
	extends AbstractBinariesExecutor<SamtoolsBinaries>
	implements SamtoolsBinariesExecutor {
	private final static Logger LOG = LoggerFactory.getLogger(DefaultSamtoolsBinariesExecutor.class);  
	
	/**
	 * Creates a new {@code DefaultSamtoolsBinariesExecutor} instance to execute
	 * the specified {@code SamtoolsBinaries}.
	 * 
	 * @param binaries the {@code SamtoolsBinaries} to execute
	 * @throws BinaryCheckException if any of the commands can't be executed
	 */	
	public DefaultSamtoolsBinariesExecutor(SamtoolsBinaries binaries)
		throws BinaryCheckException {
		this.setBinaries(binaries);
	}

	@Override
	public void setBinaries(SamtoolsBinaries binaries)
		throws BinaryCheckException {
		super.setBinaries(binaries);
		this.checkBinaries();
	}

	@Override
	public void checkBinaries() throws BinaryCheckException {
		DefaultSamtoolsBinariesChecker.checkAll(binaries);
	}

	@Override
	public ExecutionResult samToBam(File sam, File bam)
		throws ExecutionException, InterruptedException {
		return executeCommand(
			LOG,
			this.binaries.getSamToBam(),
			"sort",
			"--threads", 
			getThreads(),
			"-o",
			bam.getAbsolutePath(),
			sam.getAbsolutePath()
		);
	}
}
