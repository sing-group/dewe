package org.sing_group.rnaseq.gui.components.wizard.steps;

import java.io.File;

import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenomeIndex;
import org.sing_group.rnaseq.core.controller.helper.AbstractDifferentialExpressionWorkflow;
import org.sing_group.rnaseq.gui.util.TestUtils;

public class WizardSummaryStepTest {
	public static void main(String[] args) {

		WizardSummaryStep step = new WizardSummaryStep(
			new WizardSummaryProvider() {
				@Override
				public String getSummary() {
					StringBuilder sb = new StringBuilder();
					sb.append(AbstractDifferentialExpressionWorkflow.getSummary(
						getReferenceGenome(), getReferenceAnnotationFile(),
						getWorkingDirectory(), getSamples()));
					return sb.toString();
				}

				private File getWorkingDirectory() {
					return new File("working-dir");
				}

				private FastqReadsSamples getSamples() {
					return TestUtils.TEST_FASTQ_READS;
				}

				private File getReferenceAnnotationFile() {
					return new File("genes.gtf");
				}

				private ReferenceGenomeIndex getReferenceGenome() {
					return TestUtils.createReferenceGenomeDatabaseManager()
						.listIndexes().get(0);
				}
			});
		TestUtils.showStepComponent(step);
	}
}
