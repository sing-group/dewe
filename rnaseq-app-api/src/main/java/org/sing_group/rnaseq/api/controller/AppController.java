package org.sing_group.rnaseq.api.controller;

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
}
