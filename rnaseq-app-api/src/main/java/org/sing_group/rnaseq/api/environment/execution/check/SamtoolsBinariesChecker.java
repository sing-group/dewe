package org.sing_group.rnaseq.api.environment.execution.check;

import org.sing_group.rnaseq.api.environment.binaries.SamtoolsBinaries;

/**
 * The interface that defines a samtools binaries checker.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface SamtoolsBinariesChecker
	extends BinariesChecker<SamtoolsBinaries> {
	/**
	 * Checks the convert command.
	 * 
	 * @throws BinaryCheckException if the command can't be executed
	 */
	public void checkConvertSamToBam() throws BinaryCheckException;

}
