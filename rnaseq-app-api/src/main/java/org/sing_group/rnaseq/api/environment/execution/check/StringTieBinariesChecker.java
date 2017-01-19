package org.sing_group.rnaseq.api.environment.execution.check;

import org.sing_group.rnaseq.api.environment.binaries.StringTieBinaries;

public interface StringTieBinariesChecker
	extends BinariesChecker<StringTieBinaries> 
	{
	public void checkStringTie() throws BinaryCheckException;

}
