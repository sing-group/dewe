package org.sing_group.rnaseq.core.entities.edgeR;

import org.sing_group.rnaseq.api.entities.edger.EdgeRGene;

/**
 * The default {@code EdgeRGene} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see EdgeRGene
 */
public class DefaultEdgeRGene implements EdgeRGene {
	private String gene;
	private String geneName;
	private double logFoldChange;
	private double pVal;

	/**
	 * Creates a new {@code DefaultEdgeRGene} instance with the specified
	 * initial values.
	 *
	 * @param gene the gene
	 * @param geneName the gene name
	 * @param pVal the p-value associated to this gene
	 * @param logFoldChange the fold change associated to this gene
	 */
	public DefaultEdgeRGene(String gene, String geneName, double pVal,
		double logFoldChange
	) {
		this.gene = gene;
		this.geneName = geneName;
		this.logFoldChange = logFoldChange;
		this.pVal = pVal;
	}
	@Override
	public String getGene() {
		return this.gene;
	}

	@Override
	public String getGeneName() {
		return this.geneName;
	}

	@Override
	public double getLogFoldChange() {
		return this.logFoldChange;
	}

	@Override
	public double getPval() {
		return this.pVal;
	}

	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder();
		toString
			.append("Gene = ").append(this.gene)
			.append("; Gene name = ").append(this.geneName)
			.append("; Log fold change = ").append(this.logFoldChange)
			.append("; p-val = ").append(this.pVal);

		return toString.toString();
	}

	@Override
	public boolean equals(Object aThat) {
		if (aThat == null) {
			return false;
		}
		if (!(aThat instanceof EdgeRGene)) {
			return false;
		}
		EdgeRGene that = (EdgeRGene) aThat;

		return this.gene.equals(that.getGene())
			&& this.geneName.equals(that.getGeneName())
			&& this.logFoldChange == that.getLogFoldChange()
			&& this.pVal == that.getPval();
	}
}
