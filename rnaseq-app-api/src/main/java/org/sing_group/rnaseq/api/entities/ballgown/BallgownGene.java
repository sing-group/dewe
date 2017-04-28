package org.sing_group.rnaseq.api.entities.ballgown;

/**
 * The interface that defines a Ballgown gene.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface BallgownGene {

	/**
	 * Returns the gene id.
	 *
	 * @return the gene id
	 */
	public abstract String getId();

	/**
	 * Returns the gene name.
	 *
	 * @return the gene name
	 */
	public abstract String getGeneName();

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
