/*
 * #%L
 * DEWE Core
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
package org.sing_group.rnaseq.core.controller;

import org.sing_group.rnaseq.api.controller.AppController;
import org.sing_group.rnaseq.api.controller.BallgownController;
import org.sing_group.rnaseq.api.controller.Bowtie2Controller;
import org.sing_group.rnaseq.api.controller.EdgeRController;
import org.sing_group.rnaseq.api.controller.FastQcController;
import org.sing_group.rnaseq.api.controller.Hisat2Controller;
import org.sing_group.rnaseq.api.controller.HtseqController;
import org.sing_group.rnaseq.api.controller.RController;
import org.sing_group.rnaseq.api.controller.SamtoolsController;
import org.sing_group.rnaseq.api.controller.StringTieController;
import org.sing_group.rnaseq.api.controller.SystemController;
import org.sing_group.rnaseq.api.controller.WorkflowController;
import org.sing_group.rnaseq.api.environment.AppEnvironment;
import org.sing_group.rnaseq.api.environment.binaries.Bowtie2Binaries;
import org.sing_group.rnaseq.api.environment.binaries.FastQcBinaries;
import org.sing_group.rnaseq.api.environment.binaries.Hisat2Binaries;
import org.sing_group.rnaseq.api.environment.binaries.HtseqBinaries;
import org.sing_group.rnaseq.api.environment.binaries.RBinaries;
import org.sing_group.rnaseq.api.environment.binaries.SamtoolsBinaries;
import org.sing_group.rnaseq.api.environment.binaries.StringTieBinaries;
import org.sing_group.rnaseq.api.environment.binaries.SystemBinaries;
import org.sing_group.rnaseq.api.environment.execution.Bowtie2BinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.FastQcBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.Hisat2BinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.HtseqBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.RBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.SamtoolsBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.StringTieBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.SystemBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.api.persistence.ReferenceGenomeIndexDatabaseManager;
import org.sing_group.rnaseq.core.environment.execution.DefaultBowtie2BinariesExecutor;
import org.sing_group.rnaseq.core.environment.execution.DefaultFastQcBinariesExecutor;
import org.sing_group.rnaseq.core.environment.execution.DefaultHisat2BinariesExecutor;
import org.sing_group.rnaseq.core.environment.execution.DefaultHtseqBinariesExecutor;
import org.sing_group.rnaseq.core.environment.execution.DefaultRBinariesExecutor;
import org.sing_group.rnaseq.core.environment.execution.DefaultSamtoolsBinariesExecutor;
import org.sing_group.rnaseq.core.environment.execution.DefaultStringTieBinariesExecutor;
import org.sing_group.rnaseq.core.environment.execution.DefaultSystemBinariesExecutor;

/**
 * The default {@code AppController} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultAppController implements AppController {
	private static DefaultAppController INSTANCE;
	private AppEnvironment environment;
	private DefaultBowtie2Controller bowtie2Controller;
	private DefaultSamtoolsController samtoolsController;
	private DefaultStringTieController stringTieController;
	private DefaultHtseqController htseqController;
	private DefaultRController rController;
	private DefaultBallgownController ballgownController;
	private DefaultEdgeRController edgeRController;
	private DefaultSystemController systemController;
	private DefaultWorkflowController workflowController;
	private DefaultHisat2Controller hisat2Controller;
	private DefaultFastQcController fastQcController;

	/**
	 * Returns the singleton {@code DefaultAppController} instance.
	 *
	 * @return the singleton {@code DefaultAppController} instance
	 */
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
		this.setStringTieController();
		this.setHtseqController();
		this.setRController();
		this.setBallgownController();
		this.setEdgeRController();
		this.setSystemController();
		this.setHisat2Controller();
		this.setWorkflowController();
		this.setFastQcController();
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

	private void setStringTieController() throws BinaryCheckException {
		this.stringTieController = new DefaultStringTieController();
		this.stringTieController.setStringTieBinariesExecutor(
			this.createStringTieBinariesExecutor(this.environment.getStringTieBinaries())
		);
		this.stringTieController.setSystemBinariesExecutor(
			this.createSystemBinariesExecutor(this.environment.getSystemBinaries())
		);
	}

	private void setHtseqController() throws BinaryCheckException {
		this.htseqController = new DefaultHtseqController();
		this.htseqController.setHtseqBinariesExecutor(
			this.createHtseqBinariesExecutor(this.environment.getHtseqBinaries())
		);
	}

	private void setRController() throws BinaryCheckException {
		this.rController = new DefaultRController();
		this.rController.setRBinariesExecutor(
			this.createRBinariesExecutor(this.environment.getRBinaries())
		);
	}

	private void setBallgownController() throws BinaryCheckException {
		this.ballgownController = new DefaultBallgownController();
		this.ballgownController.setRBinariesExecutor(
			this.createRBinariesExecutor(this.environment.getRBinaries())
		);
	}

	private void setEdgeRController() throws BinaryCheckException {
		this.edgeRController = new DefaultEdgeRController();
		this.edgeRController.setRBinariesExecutor(
			this.createRBinariesExecutor(this.environment.getRBinaries())
		);
	}

	private void setSystemController() throws BinaryCheckException {
		this.systemController = new DefaultSystemController();
		this.systemController.setSystemBinariesExecutor(
			this.createSystemBinariesExecutor(this.environment.getSystemBinaries())
		);
	}

	private void setWorkflowController() {
		this.workflowController = new DefaultWorkflowController();
	}

	private void setHisat2Controller() throws BinaryCheckException {
		this.hisat2Controller = new DefaultHisat2Controller();
		this.hisat2Controller.setHisat2BinariesExecutor(
			this.createHisat2BinariesExecutor(this.environment.getHisat2Binaries())
		);
	}

	private Bowtie2BinariesExecutor createBowtie2BinariesExecutor(
		Bowtie2Binaries bowtie2Binaries
	) throws BinaryCheckException {
		return new DefaultBowtie2BinariesExecutor(bowtie2Binaries);
	}

	private void setFastQcController() throws BinaryCheckException {
		this.fastQcController = new DefaultFastQcController();
		this.fastQcController.setFastQcBinariesExecutor(
			this.createFastQcBinariesExecutor(this.environment.getFastQcBinaries())
		);
	}

	private FastQcBinariesExecutor createFastQcBinariesExecutor(
		FastQcBinaries fastQcBinaries
	) throws BinaryCheckException {
		return new DefaultFastQcBinariesExecutor(fastQcBinaries);
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
	public RController getRController() {
		return this.rController;
	}

	private StringTieBinariesExecutor createStringTieBinariesExecutor(
		StringTieBinaries stringTieBinaries
	) throws BinaryCheckException {
		return new DefaultStringTieBinariesExecutor(stringTieBinaries);
	}

	private HtseqBinariesExecutor createHtseqBinariesExecutor(
		HtseqBinaries htseqBinaries
	) throws BinaryCheckException {
		return new DefaultHtseqBinariesExecutor(htseqBinaries);
	}

	private RBinariesExecutor createRBinariesExecutor(
		RBinaries rBinaries
	) throws BinaryCheckException {
		return new DefaultRBinariesExecutor(rBinaries);
	}

	private SystemBinariesExecutor createSystemBinariesExecutor(
		SystemBinaries systemBinaries
	) throws BinaryCheckException {
		return new DefaultSystemBinariesExecutor(systemBinaries);
	}

	private Hisat2BinariesExecutor createHisat2BinariesExecutor(
		Hisat2Binaries hisat2Binaries
	) throws BinaryCheckException {
		return new DefaultHisat2BinariesExecutor(hisat2Binaries);
	}

	@Override
	public Hisat2Controller getHisat2Controller() {
		return this.hisat2Controller;
	}

	@Override
	public StringTieController getStringTieController() {
		return this.stringTieController;
	}

	@Override
	public HtseqController getHtseqController() {
		return this.htseqController;
	}

	@Override
	public BallgownController getBallgownController() {
		return this.ballgownController;
	}

	@Override
	public EdgeRController getEdgeRController() {
		return this.edgeRController;
	}

	@Override
	public SystemController getSystemController() {
		return this.systemController;
	}

	@Override
	public WorkflowController getWorkflowController() {
		return this.workflowController;
	}

	@Override
	public FastQcController getFastQcController() {
		return this.fastQcController;
	}

	@Override
	public ReferenceGenomeIndexDatabaseManager getReferenceGenomeDatabaseManager() {
		return this.environment.getReferenceGenomeDatabaseManager();
	}

	@Override
	public int getThreads() {
		return this.environment.getThreads();
	}
}
