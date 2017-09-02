/*
 * #%L
 * DEWE API
 * %%
 * Copyright (C) 2016 - 2017 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
