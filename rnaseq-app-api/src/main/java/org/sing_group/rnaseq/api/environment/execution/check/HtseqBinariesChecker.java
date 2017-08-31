package org.sing_group.rnaseq.api.environment.execution.check;

import org.sing_group.rnaseq.api.environment.binaries.HtseqBinaries;

/**
 * The interface that defines an HTSeq binaries checker.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface HtseqBinariesChecker extends BinariesChecker<HtseqBinaries> {
	/**
	 * Checks the htseq-count command.
	 * 
	 * @throws BinaryCheckException if the command can't be executed
	 */
	public void checkHtseqCount() throws BinaryCheckException;
}
