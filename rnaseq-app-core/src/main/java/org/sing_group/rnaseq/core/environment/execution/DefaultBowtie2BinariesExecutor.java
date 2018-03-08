/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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

import org.sing_group.rnaseq.api.environment.binaries.Bowtie2Binaries;
import org.sing_group.rnaseq.api.environment.execution.Bowtie2BinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.api.environment.execution.parameters.bowtie2.Bowtie2EndToEndConfiguration;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenomeIndex;
import org.sing_group.rnaseq.core.environment.execution.check.DefaultBowtie2BinariesChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default {@code Bowtie2BinariesExecutor} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultBowtie2BinariesExecutor
	extends AbstractBinariesExecutor<Bowtie2Binaries>
	implements Bowtie2BinariesExecutor {
	private final static Logger LOG = LoggerFactory.getLogger(DefaultBowtie2BinariesExecutor.class);  
	
	/**
	 * Creates a new {@code DefaultBowtie2BinariesExecutor} instance to execute
	 * the specified {@code Bowtie2Binaries}.
	 * 
	 * @param binaries the {@code Bowtie2Binaries} to execute
	 * @throws BinaryCheckException if any of the commands can't be executed
	 */
	public DefaultBowtie2BinariesExecutor(Bowtie2Binaries binaries)
		throws BinaryCheckException {
		this.setBinaries(binaries);
	}

	@Override
	public void setBinaries(Bowtie2Binaries binaries)
		throws BinaryCheckException {
		super.setBinaries(binaries);
		this.checkBinaries();
	}

	@Override
	public void checkBinaries() throws BinaryCheckException {
		DefaultBowtie2BinariesChecker.checkAll(binaries);
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
	public ExecutionResult alignReads(Bowtie2ReferenceGenomeIndex genome,
		File reads1, File reads2, Bowtie2EndToEndConfiguration configuration,
		File output
	) throws ExecutionException, InterruptedException {
		return alignReads(genome, reads1, reads2, configuration, output, null);
	}

	@Override
	public ExecutionResult alignReads(Bowtie2ReferenceGenomeIndex genome,
		File reads, Bowtie2EndToEndConfiguration configuration,
		File output
	) throws ExecutionException, InterruptedException {
		return alignReads(genome, reads, configuration, output, null);
	}

	@Override
	public ExecutionResult alignReads(Bowtie2ReferenceGenomeIndex genome,
		File reads1, File reads2, Bowtie2EndToEndConfiguration configuration,
		File output, File alignmentLog
	) throws ExecutionException, InterruptedException {
		return executeCommand(
			null,
			alignmentLog,
			LOG,
			this.binaries.getAlignReads(),
			"--threads",
			getThreads(),
			configuration.getParameter(),
			"-x",
			genome.getQuotedReferenceGenomeIndex(),
			"-1",
			reads1.getAbsolutePath(),
			"-2",
			reads2.getAbsolutePath(),
			"-S",
			output.getAbsolutePath()
		);
	}

	@Override
	public ExecutionResult alignReads(Bowtie2ReferenceGenomeIndex genome,
		File reads, Bowtie2EndToEndConfiguration configuration,
		File output, File alignmentLog
	) throws ExecutionException, InterruptedException {
		return executeCommand(
			null,
			alignmentLog,
			LOG,
			this.binaries.getAlignReads(),
			"--threads",
			getThreads(),
			configuration.getParameter(),
			"-x",
			genome.getQuotedReferenceGenomeIndex(),
			"-U",
			reads.getAbsolutePath(),
			"-S",
			output.getAbsolutePath()
		);
	}
}
