package org.sing_group.rnaseq.core.controller;

import static org.sing_group.rnaseq.core.environment.execution.DefaultRBinariesExecutor.asScriptFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import org.sing_group.rnaseq.api.controller.BallgownController;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownSample;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.RBinariesExecutor;

public class DefaultBallgownController implements BallgownController {
	private static final InputStream SCRIPT_DE_ANALYSIS = 
		DefaultBallgownController.class.getResourceAsStream(
			"/scripts/ballgown-differential-expression.R");
	
	private RBinariesExecutor rBinariesExecutor;

	@Override
	public void setRBinariesExecutor(RBinariesExecutor executor) {
		this.rBinariesExecutor = executor;
	}

	@Override
	public void differentialExpression(List<BallgownSample> samples,
		File outputFolder) throws ExecutionException, InterruptedException {
		ExecutionResult result;
		try {
			File phenotypeData = writePhenotypeData(samples, outputFolder);

			result = this.rBinariesExecutor.runScript(
				asScriptFile(SCRIPT_DE_ANALYSIS, "ballgown-analysis-"),
				outputFolder.getAbsolutePath(),
				phenotypeData.getName());
	
			if (result.getExitStatus() != 0) {
				throw new ExecutionException(result.getExitStatus(),
					"Error executing script. Please, check error log.", "");
			}
		} catch (IOException e) {
			throw new ExecutionException(1,	
				"Error executing script. Please, check error log.", "");
		}
		
	}

	private File writePhenotypeData(List<BallgownSample> samples,
		File outputFolder) throws IOException {
		outputFolder.mkdirs();
		File phenotypeData = new File(outputFolder, "phenotype-data.csv");
		Files.write(phenotypeData.toPath(), phenotypeData(samples).getBytes());
		return phenotypeData;
	}
	
	private String phenotypeData(List<BallgownSample> samples) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"ids\",\"type\",\"path\"\n");
		for(BallgownSample sample : samples) {
			sb
				.append("\"")
				.append(sample.getName())
				.append("\",\"")
				.append(sample.getType())
				.append("\",\"")
				.append(sample.getDirectory().getAbsolutePath())
				.append("\"\n");
		}
		return sb.toString();
	}
}
