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
package org.sing_group.rnaseq.api.controller;

import java.util.Optional;

import org.sing_group.rnaseq.api.environment.AppEnvironment;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.api.persistence.ReferenceGenomeIndexDatabaseManager;

/**
 * The interface that defines the application controller.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface AppController {
	/**
	 * Sets the application environment.
	 *
	 * @param environment a {@code AppEnvironment}
	 * @throws BinaryCheckException if an error occurs while checking the
	 * 		   binaries
	 */
	public abstract void setAppEvironment(AppEnvironment environment)
		throws BinaryCheckException;

	/**
	 * Returns the {@code Bowtie2Controller}.
	 *
	 * @return the {@code Bowtie2Controller}
	 */
	public abstract Bowtie2Controller getBowtie2Controller();

	/**
	 * Returns the {@code Bowtie2Controller}.
	 *
	 * @return the {@code Bowtie2Controller}
	 */
	public abstract SamtoolsController getSamtoolsController();

	/**
	 * Returns the {@code Bowtie2Controller}.
	 *
	 * @return the {@code Bowtie2Controller}
	 */
	public abstract StringTieController getStringTieController();

	/**
	 * Returns the {@code HtseqController}.
	 *
	 * @return the {@code HtseqController}
	 */
	public abstract HtseqController getHtseqController();

	/**
	 * Returns the {@code RController}.
	 *
	 * @return the {@code RController}
	 */
	public abstract RController getRController();

	/**
	 * Returns the {@code BallgownController}.
	 *
	 * @return the {@code BallgownController}
	 */
	public abstract BallgownController getBallgownController();

	/**
	 * Returns the {@code EdgeRController}.
	 *
	 * @return the {@code EdgeRController}
	 */
	public abstract EdgeRController getEdgeRController();

	/**
	 * Returns the {@code SystemController}.
	 *
	 * @return the {@code SystemController}
	 */
	public abstract SystemController getSystemController();

	/**
	 * Returns the {@code WorkflowController}.
	 *
	 * @return the {@code WorkflowController}
	 */
	public abstract WorkflowController getWorkflowController();

	/**
	 * Returns the {@code Hisat2Controller}.
	 *
	 * @return the {@code Hisat2Controller}
	 */
	public abstract Hisat2Controller getHisat2Controller();

	/**
	 * Returns the {@code FastQcController}.
	 *
	 * @return the {@code FastQcController}
	 */
	public abstract FastQcController getFastQcController();

	/**
	 * Returns the {@code TrimmomaticController}.
	 *
	 * @return the {@code TrimmomaticController}
	 */
	public abstract TrimmomaticController getTrimmomaticController();

	/**
	 * Returns the {@code PathfindRBallgownController}.
	 *
	 * @return the {@code PathfindRBallgownController}
	 */
	public abstract PathfindRBallgownController getPathfindRBallgownController();
	
	/**
	 * Returns the {@code PathfindREdgeRController}.
	 *
	 * @return the {@code PathfindREdgeRController}
	 */
	public abstract PathfindREdgeRController getPathfindREdgeRController();
	
	/**
	 * Returns the {@code IgvBrowserController}.
	 *
	 * @return the {@code IgvBrowserController}
	 */
	public abstract IgvBrowserController getIgvBrowserController();
	
	/**
	 * Returns the {@code DEOverlapsController}.
	 *
	 * @return the {@code DEOverlapsController}
	 */
	public abstract DEOverlapsController getDEOverlapsController();

	/**
	 * Returns the {@code ReferenceGenomeIndexDatabaseManager}.
	 *
	 * @return the {@code ReferenceGenomeIndexDatabaseManager}
	 */
	public abstract ReferenceGenomeIndexDatabaseManager getReferenceGenomeDatabaseManager();

	/**
	 * Returns the configured number of threads.
	 *
	 * @return the configured number of threads
	 */
	public abstract int getThreads();

	/**
	 * Returns the value of the specified property, wrapped as an optional which
	 * can be empty if the property is not found.
	 *
	 * @param propertyName the name of the property to look for
	 * @return the value of the specified property.
	 */
	public abstract Optional<String> getProperty(String propertyName);
}
