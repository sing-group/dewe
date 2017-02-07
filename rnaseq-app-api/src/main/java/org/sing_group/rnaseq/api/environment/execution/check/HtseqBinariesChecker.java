package org.sing_group.rnaseq.api.environment.execution.check;

import org.sing_group.rnaseq.api.environment.binaries.HtseqBinaries;

public interface HtseqBinariesChecker extends BinariesChecker<HtseqBinaries> {
	public void checkHtseqCount() throws BinaryCheckException;
}
