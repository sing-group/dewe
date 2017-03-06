package org.sing_group.rnaseq.api.controller;

import org.sing_group.rnaseq.api.environment.AppEnvironment;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.api.persistence.ReferenceGenomeDatabaseManager;

public interface AppController {
	public abstract void setAppEvironment(AppEnvironment environment)
		throws BinaryCheckException;

	public abstract Bowtie2Controller getBowtie2Controller();

	public abstract SamtoolsController getSamtoolsController();

	public abstract StringTieController getStringTieController();

	public abstract HtseqController getHtseqController();

	public abstract RController getRController();

	public abstract BallgownController getBallgownController();

	public abstract EdgeRController getEdgeRController();

	public abstract SystemController getSystemController();

	public abstract WorkflowController getWorkflowController();

	public abstract ReferenceGenomeDatabaseManager getReferenceGenomeDatabaseManager();
}
