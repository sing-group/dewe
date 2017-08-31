package org.sing_group.rnaseq.api.environment.execution;

import org.sing_group.rnaseq.api.environment.binaries.Binaries;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;

/**
 * The interface that defines a {@code Binaries} executor.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 * @param <B> the type of the {@code Binaries} to execute
 */
public interface BinariesExecutor<B extends Binaries> {
	/**
	 * Sets the binaries to execute.
	 * 
	 * @param binaries the binaries to execute
	 * @throws BinaryCheckException if an error occurs while checking the 
	 * 		   binaries
	 */
	public abstract void setBinaries(B binaries)
		throws BinaryCheckException;

	/**
	 * Checks that the established binaries can be used.
	 * 
	 * @throws BinaryCheckException if an error occurs while checking the 
	 * 		   binaries
	 */
	public abstract void checkBinaries()
		throws BinaryCheckException;
	
	/**
	 * An input line call back.
	 * 
	 * @author Hugo López-Fernández
	 * @author Aitor Blanco-Míguez
	 *
	 */
	public static interface InputLineCallback {
		public void info(String message);
		public void line(String line);
		public void error(String message, Exception e);
		public void inputStarted();
		public void inputFinished();
	}
}