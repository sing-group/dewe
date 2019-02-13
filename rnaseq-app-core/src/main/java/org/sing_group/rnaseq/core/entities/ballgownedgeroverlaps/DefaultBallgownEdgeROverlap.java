/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2019 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
package org.sing_group.rnaseq.core.entities.ballgownedgeroverlaps;

import org.sing_group.rnaseq.api.entities.ballgownedgeroverlaps.BallgownEdgeROverlap;
import org.sing_group.rnaseq.api.entities.pathfindr.PathfindRPathway;

/**
 * The default {@code BallgownEdgeROverlap} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see PathfindRPathway
 */
public class DefaultBallgownEdgeROverlap implements BallgownEdgeROverlap {
	private String gene;
	private double ballgownLogFoldChange;
	private double edgeRLogFoldChange;
	private double ballgownPVal;
	private double edgeRPVal;

	/**
	 * Creates a new {@code BallgownEdgeROverlap} instance with the specified
	 * initial values.
	 *
	 * @param pathway the pathway
	 * @param geneName the pathway name
	 * @param logFoldChange the fold change associated to this pathway
	 * @param occurrence the occurrences associated to this pathway
	 * @param lowPVal the lowest p-value associated to this pathway
	 * @param highPVal the highest p-value associated to this pathway
	 * @param upGenes the up-regulated genes associated to this pathway
	 * @param downGenes the down-regulated genes associated to this pathway
	 * @param cluster the cluster associated to this pathway
	 * @param status whether the pathway is representative in its cluster
	 */
	public DefaultBallgownEdgeROverlap( String gene,
									double ballgownLogFoldChange,
									double edgeRLogFoldChange,
									double ballgownPVal,
									double edgeRPVal
	) {
		this.gene = gene;
		this.ballgownLogFoldChange = ballgownLogFoldChange;
		this.edgeRLogFoldChange = edgeRLogFoldChange;
		this.ballgownPVal = ballgownPVal;
		this.edgeRPVal = edgeRPVal;
	}
	
	@Override
	public String getGene() {
		return this.gene;
	}
	
	@Override
	public double getBallgownLogFoldChange() {
		return this.ballgownLogFoldChange;
	}

	@Override
	public double getEdgeRLogFoldChange() {
		return this.edgeRLogFoldChange;
	}
	
	@Override
	public double getBallgownPval() {
		return this.ballgownPVal;
	}
	
	@Override
	public double getEdgeRPval() {
		return this.edgeRPVal;
	}
	
	

	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder();
		toString
			.append("Gene = ").append(this.gene)
			.append("; Ballgown log fold change = ").append(this.ballgownLogFoldChange)
			.append("; EdgeR log fold change = ").append(this.edgeRLogFoldChange)
			.append("; Ballgown p-val = ").append(this.ballgownPVal)
			.append("; EdgeR p-val = ").append(this.edgeRPVal);

		return toString.toString();
	}

	@Override
	public boolean equals(Object aThat) {
		if (aThat == null) {
			return false;
		}
		if (!(aThat instanceof BallgownEdgeROverlap)) {
			return false;
		}
		BallgownEdgeROverlap that = (BallgownEdgeROverlap) aThat;

		return this.gene.equals(that.getGene());
	}
}
