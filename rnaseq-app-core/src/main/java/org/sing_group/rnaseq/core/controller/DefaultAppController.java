package org.sing_group.rnaseq.core.controller;

import org.sing_group.rnaseq.api.controller.AppController;
import org.sing_group.rnaseq.api.controller.BallgownController;
import org.sing_group.rnaseq.api.controller.Bowtie2Controller;
import org.sing_group.rnaseq.api.controller.EdgeRController;
import org.sing_group.rnaseq.api.controller.HtseqController;
import org.sing_group.rnaseq.api.controller.RController;
import org.sing_group.rnaseq.api.controller.SamtoolsController;
import org.sing_group.rnaseq.api.controller.StringTieController;
import org.sing_group.rnaseq.api.controller.SystemController;
import org.sing_group.rnaseq.api.environment.AppEnvironment;
import org.sing_group.rnaseq.api.environment.binaries.Bowtie2Binaries;
import org.sing_group.rnaseq.api.environment.binaries.HtseqBinaries;
import org.sing_group.rnaseq.api.environment.binaries.RBinaries;
import org.sing_group.rnaseq.api.environment.binaries.SamtoolsBinaries;
import org.sing_group.rnaseq.api.environment.binaries.StringTieBinaries;
import org.sing_group.rnaseq.api.environment.binaries.SystemBinaries;
import org.sing_group.rnaseq.api.environment.execution.Bowtie2BinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.HtseqBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.RBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.SamtoolsBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.StringTieBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.SystemBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.api.persistence.ReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.core.environment.execution.DefaultBowtie2BinariesExecutor;
import org.sing_group.rnaseq.core.environment.execution.DefaultHtseqBinariesExecutor;
import org.sing_group.rnaseq.core.environment.execution.DefaultRBinariesExecutor;
import org.sing_group.rnaseq.core.environment.execution.DefaultSamtoolsBinariesExecutor;
import org.sing_group.rnaseq.core.environment.execution.DefaultStringTieBinariesExecutor;
import org.sing_group.rnaseq.core.environment.execution.DefaultSystemBinariesExecutor;

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
	public ReferenceGenomeDatabaseManager getReferenceGenomeDatabaseManager() {
		return this.environment.getReferenceGenomeDatabaseManager();
	}
}
