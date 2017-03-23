package org.sing_group.rnaseq.api.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.Bowtie2Binaries;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenome;

public interface Bowtie2BinariesExecutor
	extends BinariesExecutor<Bowtie2Binaries> 
	{
	public abstract ExecutionResult buildIndex(File genome, File outDir,
		String baseName) throws ExecutionException, InterruptedException;

	public abstract ExecutionResult alignReads(Bowtie2ReferenceGenome genome,
		File reads1, File reads2, File output)
		throws ExecutionException, InterruptedException;
}
