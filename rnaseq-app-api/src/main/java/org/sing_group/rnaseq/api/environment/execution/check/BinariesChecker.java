package org.sing_group.rnaseq.api.environment.execution.check;

import org.sing_group.rnaseq.api.environment.binaries.Binaries;

/**
 * The interface that defines a binaries checker.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 * @param <B> the type of the {@code Binaries} to execute
 */
public interface BinariesChecker<B extends Binaries> {
	/**
	 * Sets the binaries to check.
	 * 
	 * @param binaries the binaries to be check
	 */
	public abstract void setBinaries(B binaries);

	/**
	 * Checks all commands associated to the current binaries.
	 * 
	 * @throws BinaryCheckException if any of the commands can't be executed
	 */
	public abstract void checkAll() throws BinaryCheckException;
}