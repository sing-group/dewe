/*
 * #%L
 * DEWE GUI
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola,
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
package org.sing_group.rnaseq.gui.components.wizard.steps;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.sing_group.rnaseq.api.controller.WorkflowController.Parameters;
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
						getWorkingDirectory(), getSamples(), getParametersMap())
					);
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

				private Map<Parameters, String> getParametersMap() {
					Map<Parameters, String> parameters = new HashMap<>();
					parameters.put(Parameters.BOWTIE2, "--sensitive");
					return parameters;
				}

			});
		TestUtils.showStepComponent(step);
	}
}
