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
package org.sing_group.rnaseq.core.entities.pathfindr;

import org.sing_group.rnaseq.api.entities.pathfindr.PathfindRPathway;

/**
 * The default {@code PathfindRPathway} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see PathfindRPathway
 */
public class DefaultPathfindRPathway implements PathfindRPathway {
	private String pathway;
	private String pathwayName;
	private double logFoldChange;
	private int    occurrence;
	private double lowPVal;
	private double highPVal;
	private String upGenes;
	private String downGenes;
	private int    cluster;
	private String status;

	/**
	 * Creates a new {@code DefaultEdgeRGene} instance with the specified
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
	public DefaultPathfindRPathway( String pathway,
									String pathwayName,
									double logFoldChange,
									int    occurrence,
									double lowPVal,
									double highPVal,
									String upGenes,
									String downGenes,
									int cluster,
									String status
	) {
		this.pathway = pathway;
		this.pathwayName = pathwayName;
		this.logFoldChange = logFoldChange;
		this.occurrence = occurrence;
		this.lowPVal = lowPVal;
		this.highPVal = highPVal;
		this.upGenes = upGenes;
		this.downGenes = downGenes;
		this.cluster  = cluster;
		this.status  = status;
	}
	
	@Override
	public String getPathway() {
		return this.pathway;
	}
	
	@Override
	public String getPathwayName() {
		return this.pathwayName;
	}

	@Override
	public double getLogFoldChange() {
		return this.logFoldChange;
	}
	
	@Override
	public int getOccurrence() {
		return this.occurrence;
	}
	
	@Override
	public double getLowPval() {
		return this.lowPVal;
	}
	
	@Override
	public double getHighPval() {
		return this.highPVal;
	}
	
	@Override
	public String getDownGenes() {
		return this.downGenes;
	}
	
	@Override
	public String getUpGenes() {
		return this.upGenes;
	}
	
	@Override
	public int getCluster() {
		return this.cluster;
	}
	
	@Override
	public String getStatus() {
		return this.status;
	}

	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder();
		toString
			.append("Pathway = ").append(this.pathway)
			.append("; Pathway name = ").append(this.pathwayName)
			.append("; Log fold change = ").append(this.logFoldChange)
			.append("; Occurrence = ").append(this.occurrence)
			.append("; Lowest p-val = ").append(this.lowPVal)
			.append("; Highest p-val = ").append(this.highPVal)
			.append("; Down-regulated genes = ").append(this.downGenes)
			.append("; Up-regulated genes = ").append(this.upGenes)
			.append("; Cluster = ").append(this.cluster)
			.append("; Status = ").append(this.status);

		return toString.toString();
	}

	@Override
	public boolean equals(Object aThat) {
		if (aThat == null) {
			return false;
		}
		if (!(aThat instanceof PathfindRPathway)) {
			return false;
		}
		PathfindRPathway that = (PathfindRPathway) aThat;

		return this.pathway.equals(that.getPathway());
	}
}
