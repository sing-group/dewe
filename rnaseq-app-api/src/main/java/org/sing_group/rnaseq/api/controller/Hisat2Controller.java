package org.sing_group.rnaseq.api.controller;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.Hisat2BinariesExecutor;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenome;

public interface Hisat2Controller {
	public abstract void setHisat2BinariesExecutor(
		Hisat2BinariesExecutor executor);

	public abstract void alignReads(Hisat2ReferenceGenome genome, File reads1,
		File reads2, File output)
		throws ExecutionException, InterruptedException;;
}
