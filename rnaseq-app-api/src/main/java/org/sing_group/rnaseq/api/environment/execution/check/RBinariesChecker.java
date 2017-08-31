package org.sing_group.rnaseq.api.environment.execution.check;

import org.sing_group.rnaseq.api.environment.binaries.RBinaries;

/**
 * The interface that defines a R binaries checker.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface RBinariesChecker extends BinariesChecker<RBinaries> {
	/**
	 * Checks the Rscript command.
	 * 
	 * @throws BinaryCheckException if the command can't be executed
	 */
	public void checkRscript() throws BinaryCheckException;
}
