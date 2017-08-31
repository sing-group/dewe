package org.sing_group.rnaseq.api.environment.execution.check;

import org.sing_group.rnaseq.api.environment.binaries.SystemBinaries;

/**
 * The interface that defines a system binaries checker.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface SystemBinariesChecker extends BinariesChecker<SystemBinaries> {
	/**
	 * Checks the join command.
	 * 
	 * @throws BinaryCheckException if the command can't be executed
	 */
	public void checkJoin() throws BinaryCheckException;

	/**
	 * Checks the sed command.
	 * 
	 * @throws BinaryCheckException if the command can't be executed
	 */
	public void checkSed() throws BinaryCheckException;
}
