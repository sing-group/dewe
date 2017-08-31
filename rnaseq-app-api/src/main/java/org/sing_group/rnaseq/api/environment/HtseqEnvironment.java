package org.sing_group.rnaseq.api.environment;

/**
 * The interface that defines the HTSeq environment.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface HtseqEnvironment {
	/**
	 * Returns the default name of the htseq-count command.
	 * 
	 * @return the default name of the htseq-count command
	 */
	public abstract String getDefaultHtseqCount();
}
