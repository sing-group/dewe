package org.sing_group.rnaseq.api.environment.execution.check;

import org.sing_group.rnaseq.api.environment.binaries.SamtoolsBinaries;

public interface SamtoolsBinariesChecker
	extends BinariesChecker<SamtoolsBinaries> 
	{
	public void checkConvertSamToBam() throws BinaryCheckException;

}
