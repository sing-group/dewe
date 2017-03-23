package org.sing_group.rnaseq.api.environment.execution.check;

import org.sing_group.rnaseq.api.environment.binaries.Hisat2Binaries;

public interface Hisat2BinariesChecker
	extends BinariesChecker<Hisat2Binaries> 
	{
	public void checkBuildIndex() throws BinaryCheckException;

	public void checkAlignReads() throws BinaryCheckException;
}
