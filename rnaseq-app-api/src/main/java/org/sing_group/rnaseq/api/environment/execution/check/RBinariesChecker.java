package org.sing_group.rnaseq.api.environment.execution.check;

import org.sing_group.rnaseq.api.environment.binaries.RBinaries;

public interface RBinariesChecker
	extends BinariesChecker<RBinaries> 
	{
	public void checkRscript() throws BinaryCheckException;

}
