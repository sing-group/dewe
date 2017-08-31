package org.sing_group.rnaseq.aibench.gui.workflow;

import org.sing_group.rnaseq.aibench.gui.wizard.AIBenchBowtieStringTieAndRDifferentialExpressionWizard;
import org.sing_group.rnaseq.gui.workflow.Bowtie2StringTieDifferentialExpressionWorkflowDescription;

/**
 * The AIBench description for the differential expression workflow that uses
 * Bowtie2, StringTie and R packages.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class Bowtie2StringTieDifferentialExpressionAIBenchWorkflowDescription
	extends Bowtie2StringTieDifferentialExpressionWorkflowDescription
	implements AIBenchWorkflowDescription {

	@Override
	public void launchWorkflowWizard() {
		AIBenchBowtieStringTieAndRDifferentialExpressionWizard.showWizard();
	}
}
