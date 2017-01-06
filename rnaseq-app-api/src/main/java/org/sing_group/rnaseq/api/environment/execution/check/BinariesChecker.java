package org.sing_group.rnaseq.api.environment.execution.check;

import org.sing_group.rnaseq.api.environment.binaries.Binaries;

public interface BinariesChecker<B extends Binaries> {
	public abstract void setBinaries(B binaries);

	public abstract void checkAll() throws BinaryCheckException;
}