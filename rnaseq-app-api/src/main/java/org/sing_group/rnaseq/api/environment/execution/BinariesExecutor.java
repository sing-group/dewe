package org.sing_group.rnaseq.api.environment.execution;

import org.sing_group.rnaseq.api.environment.binaries.Binaries;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;

public interface BinariesExecutor<B extends Binaries> {
	public abstract void setBinaries(B binaries)
		throws BinaryCheckException;

	public abstract void checkBinaries()
		throws BinaryCheckException;
	
	public static interface InputLineCallback {
		public void info(String message);
		public void line(String line);
		public void error(String message, Exception e);
		public void inputStarted();
		public void inputFinished();
	}
}