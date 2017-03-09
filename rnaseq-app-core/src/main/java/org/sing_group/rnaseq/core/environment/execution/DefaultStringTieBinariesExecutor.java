package org.sing_group.rnaseq.core.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.StringTieBinaries;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.StringTieBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.core.environment.execution.check.DefaultStringTieBinariesChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultStringTieBinariesExecutor
	extends AbstractBinariesExecutor<StringTieBinaries>
	implements StringTieBinariesExecutor {
	private final static Logger LOG = LoggerFactory.getLogger(DefaultStringTieBinariesExecutor.class);  
	
	public DefaultStringTieBinariesExecutor(StringTieBinaries binaries) throws BinaryCheckException {
		this.setBinaries(binaries);
	}
	
	@Override
	public void setBinaries(StringTieBinaries binaries) throws BinaryCheckException {
		super.setBinaries(binaries);
		this.checkBinaries();
	}

	@Override
	public void checkBinaries() throws BinaryCheckException {
		DefaultStringTieBinariesChecker.checkAll(binaries);
	}

	@Override
	public ExecutionResult obtainTranscripts(File referenceAnnotationFile,
			File inputBam, File outputTranscripts)
			throws ExecutionException, InterruptedException {
		return executeCommand(
			LOG,
			this.binaries.getStringTie(),
			"-p 8",
			"-G", 	referenceAnnotationFile.getAbsolutePath(),
			"-e",
			"-B",
			"-o", 	outputTranscripts.getAbsolutePath(),
			inputBam.getAbsolutePath()
		);
	}
	
	@Override
	public ExecutionResult mergeTranscripts(File referenceAnnotationFile,
			File mergeList, File mergedAnnotationFile)
			throws ExecutionException, InterruptedException {
		return executeCommand(
			LOG,
			this.binaries.getStringTie(),
			"--merge", 
			"-p 8",
			"-G", 	referenceAnnotationFile.getAbsolutePath(),
			"-o", 	mergedAnnotationFile.getAbsolutePath(),
			mergeList.getAbsolutePath()
		);
	}

}
