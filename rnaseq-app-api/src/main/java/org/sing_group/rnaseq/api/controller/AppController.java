package org.sing_group.rnaseq.api.controller;

import org.sing_group.rnaseq.api.environment.AppEnvironment;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.api.persistence.ReferenceGenomeDatabaseManager;

public interface AppController {
	public abstract void setAppEvironment(AppEnvironment environment)
		throws BinaryCheckException;

	public abstract Bowtie2Controller getBowtie2Controller();

	public abstract ReferenceGenomeDatabaseManager getReferenceGenomeDatabaseManager();
}
