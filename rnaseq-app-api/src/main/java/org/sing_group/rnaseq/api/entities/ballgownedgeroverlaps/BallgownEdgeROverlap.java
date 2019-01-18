/*
 * #%L
 * DEWE API
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
package org.sing_group.rnaseq.api.entities.ballgownedgeroverlaps;

/**
 * The interface that defines an Ballgown and edgeR overlap.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface BallgownEdgeROverlap {
	
	/**
	 * Returns the gene.
	 *
	 * @return the gene
	 */
	public abstract String getGene();

	/**
	 * Returns the log fold change associated to this gene in Ballgown DE.
	 *
	 * @return the log fold change associated to this gene 
	 */
	public abstract double getBallgownLogFoldChange();

	/**
	 * Returns the log fold change associated to this gene in edgeR DE.
	 *
	 * @return the log fold change associated to this gene
	 */
	public abstract double getEdgeRLogFoldChange();

	/**
	 * Returns the Ballgown p-value associated to this gene.
	 *
	 * @return the Ballgown p-value associated to this gene
	 */
	public abstract double getBallgownPval();

	/**
	 * Returns the edgeR p-value associated to this gene.
	 *
	 * @return the edgeR p-value associated to this gene
	 */
	public abstract double getEdgeRPval();

}
