package org.sing_group.rnaseq.core.io.alignment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Stream;

import org.sing_group.rnaseq.api.entities.alignment.AlignmentStatistics;
import org.sing_group.rnaseq.core.entities.alignment.DefaultAlignmentStatistics;
import org.sing_group.rnaseq.io.alignment.AlignmentLogParser;

/**
 * The default implementation of the {@link AlignmentLogParser}.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultAlignmentLogParser implements AlignmentLogParser {
	public static final String MARKER_OVERALL_ALIGNMENT = "overall alignment rate";
	public static final String MARKER_UNIQUELY_ALIGNED = "aligned concordantly exactly 1 time";
	public static final String MARKER_MULTIMAPPED = "aligned concordantly >1 times";
	
	private Double overallAlignmentRate = null;
	private Double uniquelyAlignedReads = null;
	private Double multimappedReads = null;

	@Override
	public void parseLogLines(String... lines) {
		Stream.of(lines).forEach(this::parseLogLine);
	}

	private void parseLogLine(String logLine) {
		if (isOverallAlignmentRateLine(logLine)) {
			this.overallAlignmentRate = extractAlignmentRate(logLine);
		} else if (isUniquelyAlignedReadsLine(logLine)) {
			this.uniquelyAlignedReads = extractAlignmentRate(logLine);
		} else if (isMultimappedReadsLine(logLine)) {
			this.multimappedReads = extractAlignmentRate(logLine);
		}
	}

	private double extractAlignmentRate(String logLine) {
		int percentageIndex = logLine.indexOf("%");
		String percentage = logLine.substring(0, percentageIndex);
		if (logLine.contains("(")) {
			percentage = percentage.substring(percentage.indexOf("(") + 1);
		}
		percentage = percentage.trim();
		return Double.valueOf(percentage);
	}

	private boolean isOverallAlignmentRateLine(String logLine) {
		return logLine.contains(MARKER_OVERALL_ALIGNMENT);
	}

	private boolean isUniquelyAlignedReadsLine(String logLine) {
		return logLine.contains(MARKER_UNIQUELY_ALIGNED);
	}

	private boolean isMultimappedReadsLine(String logLine) {
		return logLine.contains(MARKER_MULTIMAPPED);
	}

	@Override
	public void parseLogFile(File logFile) throws IOException {
		List<String> lines = Files.readAllLines(logFile.toPath());
		parseLogLines(lines.toArray(new String[lines.size()]));
	}

	@Override
	public AlignmentStatistics getAlignmentStatistics() {
		return new DefaultAlignmentStatistics(overallAlignmentRate,
			uniquelyAlignedReads, multimappedReads);
	}
}
