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
import java.util.LinkedList;
import java.util.List;

import org.sing_group.rnaseq.api.environment.binaries.Hisat2Binaries;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.Hisat2BinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenomeIndex;
import org.sing_group.rnaseq.core.environment.execution.check.DefaultHisat2BinariesChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default {@code Hisat2BinariesExecutor} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultHisat2BinariesExecutor extends
	AbstractBinariesExecutor<Hisat2Binaries> implements Hisat2BinariesExecutor {
	private final static Logger LOG = LoggerFactory.getLogger(DefaultHisat2BinariesExecutor.class);	  
	
	/**
	 * Creates a new {@code DefaultHisat2BinariesExecutor} instance to execute
	 * the specified {@code Hisat2Binaries}.
	 * 
	 * @param binaries the {@code Hisat2Binaries} to execute
	 * @throws BinaryCheckException if any of the commands can't be executed
	 */
	public DefaultHisat2BinariesExecutor(Hisat2Binaries binaries)
		throws BinaryCheckException {
		this.setBinaries(binaries);
	}
	
	@Override
	public void setBinaries(Hisat2Binaries binaries)
		throws BinaryCheckException {
		super.setBinaries(binaries);
		this.checkBinaries();
	}

	@Override
	public void checkBinaries() throws BinaryCheckException {
		DefaultHisat2BinariesChecker.checkAll(binaries);
	}

	@Override
	public ExecutionResult buildIndex(File genome, File outDir, String baseName)
		throws ExecutionException, InterruptedException {

		return executeCommand(
			LOG,
			this.binaries.getBuildIndex(),
			genome.getAbsolutePath(),
			new File(outDir, baseName).getAbsolutePath()
		);
	}

	@Override
	public ExecutionResult alignReads(Hisat2ReferenceGenomeIndex genome,
		File reads1, File reads2, boolean dta, String params, File output
	) throws ExecutionException, InterruptedException {
		return alignReads(genome, reads1, reads2, dta, params, output, null);
	}

	@Override
	public ExecutionResult alignReads(Hisat2ReferenceGenomeIndex genome,
		File reads, boolean dta, String params, File output
	) throws ExecutionException, InterruptedException {
		return alignReads(genome, reads, dta, params, output, null);
	}

	@Override
	public ExecutionResult alignReads(Hisat2ReferenceGenomeIndex genome,
		File reads1, File reads2, boolean dta, String params, File output,
		File alignmentLog
	) throws ExecutionException, InterruptedException {
		List<String> paramsList = new LinkedList<>();
		paramsList.add("--threads");
		paramsList.add(getThreads());
		paramsList.add(dta ? "--dta" : "");
		paramsList.addAll(splitParams(params));
		paramsList.add("-x");
		paramsList.add(genome.getQuotedReferenceGenomeIndex());
		paramsList.add("-1");
		paramsList.add(escapeWhiteSpaces(reads1));
		paramsList.add("-2");
		paramsList.add(escapeWhiteSpaces(reads2));
		paramsList.add("-S");
		paramsList.add(escapeWhiteSpaces(output));
		
		return executeCommand(
			null,
			alignmentLog,
			LOG,
			this.binaries.getAlignReads(),
			toParamArray(paramsList)
		);
	}
	
	@Override
	public ExecutionResult alignReads(Hisat2ReferenceGenomeIndex genome,
		File reads, boolean dta, String params, File output, File alignmentLog
	) throws ExecutionException, InterruptedException {
		List<String> paramsList = new LinkedList<>();
		paramsList.add("--threads");
		paramsList.add(getThreads());
		paramsList.add(dta ? "--dta" : "");
		paramsList.addAll(splitParams(params));
		paramsList.add("-x");
		paramsList.add(genome.getQuotedReferenceGenomeIndex());
		paramsList.add("-U");
		paramsList.add(escapeWhiteSpaces(reads));
		paramsList.add("-S");
		paramsList.add(escapeWhiteSpaces(output));
		
		return executeCommand(
			null,
			alignmentLog,
			LOG,
			this.binaries.getAlignReads(),
			toParamArray(paramsList)
		);
	}
}
