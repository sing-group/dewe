package org.sing_group.rnaseq.api.environment.binaries;

/**
 * The interface that defines HTSeq binaries.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface HtseqBinaries extends Binaries {
	public final static String HTSEQ_PREFIX = "htseq.";
	public final static String BASE_DIRECTORY_PROP = HTSEQ_PREFIX + "binDir";

	/**
	 * Returns a string with the full path to the htseq-count command.
	 *  
	 * @return a string with the full path to the htseq-count command
	 */
	public abstract String getHtseqCount();
}
