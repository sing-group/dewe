package org.sing_group.rnaseq.api.environment.execution.check;

import org.sing_group.rnaseq.api.environment.binaries.Bowtie2Binaries;

public interface Bowtie2BinariesChecker
	extends BinariesChecker<Bowtie2Binaries> 
	{
	public void checkBuildIndex() throws BinaryCheckException;

	public void checkAlignReads() throws BinaryCheckException;
}
