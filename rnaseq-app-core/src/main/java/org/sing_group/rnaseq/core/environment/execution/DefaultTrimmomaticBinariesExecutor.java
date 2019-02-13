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

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.sing_group.rnaseq.api.environment.binaries.TrimmomaticBinaries;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.TrimmomaticBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.api.environment.execution.parameters.trimmomatic.TrimmomaticParameter;
import org.sing_group.rnaseq.core.environment.execution.check.DefaultTrimmomaticBinariesChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default {@code TrimmomaticBinaries} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultTrimmomaticBinariesExecutor
	extends AbstractBinariesExecutor<TrimmomaticBinaries>
	implements TrimmomaticBinariesExecutor {
	private final static Logger LOG = LoggerFactory.getLogger(DefaultTrimmomaticBinariesExecutor.class);  
	
	/**
	 * Creates a new {@code DefaultTrimmomaticBinariesExecutor} instance to execute
	 * the specified {@code TrimmomaticBinaries}.
	 * 
	 * @param binaries the {@code TrimmomaticBinaries} to execute
	 * @throws BinaryCheckException if any of the commands can't be executed
	 */
	public DefaultTrimmomaticBinariesExecutor(TrimmomaticBinaries binaries)
		throws BinaryCheckException {
		this.setBinaries(binaries);
	}

	@Override
	public void setBinaries(TrimmomaticBinaries binaries)
		throws BinaryCheckException {
		super.setBinaries(binaries);
		this.checkBinaries();
	}

	@Override
	public void checkBinaries() throws BinaryCheckException {
		DefaultTrimmomaticBinariesChecker.checkAll(binaries);
	}

	@Override
	public ExecutionResult filterSingleEndReads(File inputFile, File outputFile,
		TrimmomaticParameter... parameters)
		throws ExecutionException, InterruptedException {

		return executeCommand(
			LOG, 
			this.binaries.getTrimmomatic(), 
			asStringArray(
				new String[]{
					"SE", 
					"-threads", getThreads(),
					inputFile.getAbsolutePath(),
					outputFile.getAbsolutePath()
				},
				parameters
			)
		);
	}

	@Override
	public ExecutionResult filterPairedEndReads(File reads1, File reads2,
		File outputBase, TrimmomaticParameter... parameters)
		throws ExecutionException, InterruptedException {

		return executeCommand(
			LOG, 
			this.binaries.getTrimmomatic(), 
			asStringArray(
				new String[]{
					"PE", 
					"-threads", getThreads(),
					reads1.getAbsolutePath(),
					reads2.getAbsolutePath(),
					"-baseout", outputBase.getAbsolutePath()
				},
				parameters
			)
		);
	}

	private String[] asStringArray(String[] parameters,
		TrimmomaticParameter[] tParameters
	) {
		List<String> toret = new LinkedList<>();
		toret.addAll(Arrays.asList(parameters));
		for (TrimmomaticParameter p : tParameters) {
			toret.add(p.toString());
		}
		return toret.toArray(new String[toret.size()]);
	}
}
