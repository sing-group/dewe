package org.sing_group.rnaseq.api.environment.execution.check;

import org.sing_group.rnaseq.api.environment.binaries.SystemBinaries;

public interface SystemBinariesChecker
	extends BinariesChecker<SystemBinaries> 
	{
	public void checkJoin() throws BinaryCheckException;

	public void checkSed() throws BinaryCheckException;

	public void checkEnsgidsToSymbols() throws BinaryCheckException;
}
