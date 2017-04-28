package org.sing_group.rnaseq.api.entities.ballgown;

/**
 * The interface that defines a Ballgown transcript.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface BallgownTranscript {

	/**
	 * Returns the gene ids.
	 *
	 * @return the gene ids
	 */
	public abstract String getGeneIds();

	/**
	 * Returns the gene names.
	 *
	 * @return the gene names
	 */
	public abstract String getGeneNames();

	/**
	 * Returns the transcript names.
	 *
	 * @return the transcript names
	 */
	public abstract String getTranscriptNames();

	/**
	 * Returns the transcript id.
	 *
	 * @return the transcript id
	 */
	public abstract int getTranscriptId();

	/**
	 * Returns the fold change associated to this gene.
	 *
	 * @return the fold change associated to this gene
	 */
	public abstract double getFoldChange();

	/**
	 * Returns the p-value associated to this gene.
	 *
	 * @return the p-value associated to this gene
	 */
	public abstract double getPval();

	/**
	 * Returns the q-value associated to this gene.
	 *
	 * @return the q-value associated to this gene
	 */
	public abstract double getQval();
}
