package org.sing_group.rnaseq.api.entities.edger;

/**
 * The interface that defines an EdgeR gene.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface EdgeRGene {

	/**
	 * Returns the gene.
	 *
	 * @return the gene
	 */
	public abstract String getGene();

	/**
	 * Returns the gene name.
	 *
	 * @return the gene name
	 */
	public abstract String getGeneName();

	/**
	 * Returns the log fold change associated to this gene.
	 *
	 * @return the log fold change associated to this gene
	 */
	public abstract double getLogFoldChange();

	/**
	 * Returns the p-value associated to this gene.
	 *
	 * @return the p-value associated to this gene
	 */
	public abstract double getPval();
}
