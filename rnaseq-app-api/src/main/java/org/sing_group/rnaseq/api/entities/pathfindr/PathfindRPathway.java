/*
 * #%L
 * DEWE API
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
package org.sing_group.rnaseq.api.entities.pathfindr;

/**
 * The interface that defines an PathfindR pathway.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface PathfindRPathway {
	
	/**
	 * Returns the pathway.
	 *
	 * @return the pathway
	 */
	public abstract String getPathway();

	/**
	 * Returns the pathway name.
	 *
	 * @return the pathway name
	 */
	public abstract String getPathwayName();

	/**
	 * Returns the log fold change associated to this pathway.
	 *
	 * @return the log fold change associated to this pathway
	 */
	public abstract double getLogFoldChange();

	/**
	 * Returns the occurrence associated to this pathway.
	 *
	 * @return the occurrence associated to this pathway
	 */
	public abstract int getOccurrence();

	/**
	 * Returns the lowest p-value associated to this pathway.
	 *
	 * @return the lowest p-value associated to this pathway
	 */
	public abstract double getLowPval();

	/**
	 * Returns the highest p-value associated to this pathway.
	 *
	 * @return the highest p-value associated to this pathway
	 */
	public abstract double getHighPval();

	/**
	 * Returns the down-regulated genes associated to this pathway.
	 *
	 * @return the down-regulated genes associated to this pathway
	 */
	public abstract String getDownGenes();

	/**
	 * Returns the up-regulated genes associated to this pathway.
	 *
	 * @return the up-regulated genes associated to this pathway
	 */
	public abstract String getUpGenes();

	/**
	 * Returns the cluster associated to this pathway.
	 *
	 * @return the cluster associated to this pathway
	 */
	public abstract int getCluster();

	/**
	 * Returns whether the pathway is representative in its cluster.
	 *
	 * @return whether the pathway is representative in its cluster
	 */
	public abstract String getStatus();
}
