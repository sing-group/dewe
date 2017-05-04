package org.sing_group.rnaseq.core.environment.execution;

import static org.sing_group.rnaseq.core.util.FileUtils.asString;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.sing_group.rnaseq.api.environment.binaries.SystemBinaries;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.SystemBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.core.environment.execution.check.DefaultSystemBinariesChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSystemBinariesExecutor
	extends AbstractBinariesExecutor<SystemBinaries>
	implements SystemBinariesExecutor {
	private final static Logger LOG = LoggerFactory.getLogger(DefaultSystemBinariesExecutor.class);  
	
	public DefaultSystemBinariesExecutor(SystemBinaries binaries) throws BinaryCheckException {
		this.setBinaries(binaries);
	}
	
	@Override
	public void setBinaries(SystemBinaries binaries) throws BinaryCheckException {
		super.setBinaries(binaries);
		this.checkBinaries();
	}

	@Override
	public void checkBinaries() throws BinaryCheckException {
		DefaultSystemBinariesChecker.checkAll(binaries);
	}

	@Override
	public ExecutionResult join(File a, File b, File result)
		throws ExecutionException, InterruptedException {
		return 	executeCommand(
			result, 
			LOG, 
			this.binaries.getJoin(), 
			asString(new File[]{a, b})
		);
	}

	@Override
	public ExecutionResult join(File[] files, File result)
		throws ExecutionException, InterruptedException {
		if(files.length < 2) {
			throw new ExecutionException(-1, "Two or more files required", "");
	}
		StringBuilder inThreadTxtSb = new StringBuilder();
		StringBuilder errThreadTxtSb = new StringBuilder();

		File nextJoinAFile = files[0];
		
		for(int i = 1; i < files.length; i++) {
			File nextJoinBFile = files[i];
			try {
				File resultFile = Files.createTempFile("tmp-join", ".tsv").toFile();
				ExecutionResult executionResult = join(nextJoinAFile, nextJoinBFile, resultFile);
				inThreadTxtSb.append(executionResult.getOutput());
				errThreadTxtSb.append(executionResult.getError());
				nextJoinAFile = resultFile;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			Files.copy(nextJoinAFile.toPath(), new FileOutputStream(result));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new DefaultExecutionResult(0, inThreadTxtSb.toString(), errThreadTxtSb.toString());
	}

	@Override
	public ExecutionResult sed(String...params)
		throws ExecutionException, InterruptedException {
		return executeCommand(LOG, this.binaries.getSed(), params);
	}
	
	@Override
	public ExecutionResult awk(File output, String...params)
		throws ExecutionException, InterruptedException {
		return executeCommand(output, LOG, this.binaries.getAwk(), params);
	}


	@Override
	public ExecutionResult ensgidsToSymbols(File referenceAnnotationFile,
			File outputFile) throws ExecutionException, InterruptedException {
		return 	executeCommand(
			LOG, 
			this.binaries.getEnsgidsToSymbols(), 
			referenceAnnotationFile.getAbsolutePath(), 
			outputFile.getAbsolutePath()
		);
	}
}
