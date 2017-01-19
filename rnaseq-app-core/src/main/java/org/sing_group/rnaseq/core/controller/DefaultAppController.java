package org.sing_group.rnaseq.core.controller;

import org.sing_group.rnaseq.api.controller.AppController;
import org.sing_group.rnaseq.api.controller.Bowtie2Controller;
import org.sing_group.rnaseq.api.controller.SamtoolsController;
import org.sing_group.rnaseq.api.environment.AppEnvironment;
import org.sing_group.rnaseq.api.environment.binaries.Bowtie2Binaries;
import org.sing_group.rnaseq.api.environment.binaries.SamtoolsBinaries;
import org.sing_group.rnaseq.api.environment.execution.Bowtie2BinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.SamtoolsBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.api.persistence.ReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.core.environment.execution.DefaultBowtie2BinariesExecutor;
import org.sing_group.rnaseq.core.environment.execution.DefaultSamtoolsBinariesExecutor;

public class DefaultAppController implements AppController {
	private static DefaultAppController INSTANCE;
	private AppEnvironment environment;
	private DefaultBowtie2Controller bowtie2Controller;
	private DefaultSamtoolsController samtoolsController;

	public static DefaultAppController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DefaultAppController();
		}
		return INSTANCE;
	}

	@Override
	public void setAppEvironment(AppEnvironment environment)
	throws BinaryCheckException {
		this.environment = environment;
		this.setBowtie2Controller();
		this.setSamtoolsController();
	}

	private void setBowtie2Controller() throws BinaryCheckException {
		this.bowtie2Controller = new DefaultBowtie2Controller();
		this.bowtie2Controller.setBowtie2BinariesExecutor(
			this.createBowtie2BinariesExecutor(this.environment.getBowtie2Binaries())
		);
		
	}

	private void setSamtoolsController() throws BinaryCheckException {
		this.samtoolsController = new DefaultSamtoolsController();
		this.samtoolsController.setSamtoolsBinariesExecutor(
			this.createSamtoolsBinariesExecutor(this.environment.getSamtoolsBinaries())
		);
		
	}

	private Bowtie2BinariesExecutor createBowtie2BinariesExecutor(
		Bowtie2Binaries bowtie2Binaries
	) throws BinaryCheckException {
		return new DefaultBowtie2BinariesExecutor(bowtie2Binaries);
	}

	@Override
	public Bowtie2Controller getBowtie2Controller() {
		return this.bowtie2Controller;
	}

	private SamtoolsBinariesExecutor createSamtoolsBinariesExecutor(
		SamtoolsBinaries samtoolsBinaries
	) throws BinaryCheckException {
		return new DefaultSamtoolsBinariesExecutor(samtoolsBinaries);
	}
	
	@Override
	public SamtoolsController getSamtoolsController() {
		return this.samtoolsController;
	}

	@Override
	public ReferenceGenomeDatabaseManager getReferenceGenomeDatabaseManager() {
		return this.environment.getReferenceGenomeDatabaseManager();
	}

}
