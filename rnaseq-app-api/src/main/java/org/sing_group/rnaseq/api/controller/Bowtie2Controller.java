package org.sing_group.rnaseq.api.controller;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.Bowtie2BinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenome;

public interface Bowtie2Controller {
	public abstract void setBowtie2BinariesExecutor(
		Bowtie2BinariesExecutor executor);

	public abstract void buildIndex(File genome, File outDir, String baseName)
		throws ExecutionException, InterruptedException;

	public abstract void alignReads(Bowtie2ReferenceGenome genome, File reads1,
		File reads2, File output)
		throws ExecutionException, InterruptedException;;
}
