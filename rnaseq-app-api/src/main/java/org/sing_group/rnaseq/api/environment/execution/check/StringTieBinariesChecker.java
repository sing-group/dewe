package org.sing_group.rnaseq.api.environment.execution.check;

import org.sing_group.rnaseq.api.environment.binaries.StringTieBinaries;

/**
 * The interface that defines a StringTie binaries checker.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface StringTieBinariesChecker
	extends BinariesChecker<StringTieBinaries> {
	/**
	 * Checks the StringTie command.
	 * 
	 * @throws BinaryCheckException if the command can't be executed
	 */
	public void checkStringTie() throws BinaryCheckException;

}
