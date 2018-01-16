/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
 * 			Borja Sánchez, and Anália Lourenço
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.sing_group.rnaseq.core.entities.ballgown;

import org.sing_group.rnaseq.api.entities.ballgown.BallgownGene;

/**
 * The default {@code BallgownGene} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see BallgownGene
 */
public class DefaultBallgownGene implements BallgownGene {
	private String id;
	private String geneName;
	private double foldChange;
	private double pVal;
	private double qVal;

	/**
	 * Creates a new {@code DefaultBallgownGene} instance with the specified
	 * initial values.
	 *
	 * @param id the gene id
	 * @param geneName the gene name
	 * @param foldChange the fold change associated to this gene
	 * @param pVal the p-value associated to this gene
	 * @param qVal the q-value associated to this gene
	 */
	public DefaultBallgownGene(String id, String geneName, double foldChange,
		double pVal, double qVal) {
		this.id = id;
		this.geneName = geneName;
		this.foldChange = foldChange;
		this.pVal = pVal;
		this.qVal = qVal;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getGeneName() {
		return this.geneName;
	}

	@Override
	public double getFoldChange() {
		return this.foldChange;
	}

	@Override
	public double getPval() {
		return this.pVal;
	}

	@Override
	public double getQval() {
		return this.qVal;
	}

	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder();
		toString
			.append("ID = ").append(this.id)
			.append("; Gene name = ").append(this.geneName)
			.append("; Fold change = ").append(this.foldChange)
			.append("; p-val = ").append(this.pVal)
			.append("; q-val = ").append(this.qVal);

		return toString.toString();
	}

	@Override
	public boolean equals(Object aThat) {
		if (aThat == null) {
			return false;
		}
		if (!(aThat instanceof BallgownGene)) {
			return false;
		}
		BallgownGene that = (BallgownGene) aThat;

		return this.id.equals(that.getId())
			&& this.geneName.equals(that.getGeneName())
			&& this.foldChange == that.getFoldChange()
			&& this.pVal == that.getPval()
			&& this.qVal == that.getQval();
	}
}
