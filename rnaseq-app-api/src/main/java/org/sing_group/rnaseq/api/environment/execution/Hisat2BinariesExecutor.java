package org.sing_group.rnaseq.api.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.Hisat2Binaries;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenome;

public interface Hisat2BinariesExecutor
	extends BinariesExecutor<Hisat2Binaries> {

	public abstract ExecutionResult buildIndex(File genome, File outDir,
		String baseName) throws ExecutionException, InterruptedException;

	public abstract ExecutionResult alignReads(Hisat2ReferenceGenome genome,
		File reads1, File reads2, File output)
		throws ExecutionException, InterruptedException;
}
