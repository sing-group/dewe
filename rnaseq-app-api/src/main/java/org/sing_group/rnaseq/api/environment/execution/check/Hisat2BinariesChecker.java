package org.sing_group.rnaseq.api.environment.execution.check;

import org.sing_group.rnaseq.api.environment.binaries.Hisat2Binaries;

/**
 * The interface that defines a HISAT2 binaries checker.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface Hisat2BinariesChecker extends BinariesChecker<Hisat2Binaries> {
	/**
	 * Checks the build index command.
	 * 
	 * @throws BinaryCheckException if the command can't be executed
	 */
	public void checkBuildIndex() throws BinaryCheckException;

	/**
	 * Checks the align reads command.
	 * 
	 * @throws BinaryCheckException if the command can't be executed
	 */
	public void checkAlignReads() throws BinaryCheckException;
}
