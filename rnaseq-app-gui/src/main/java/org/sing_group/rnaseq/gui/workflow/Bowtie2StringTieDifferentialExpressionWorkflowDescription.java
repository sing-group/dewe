package org.sing_group.rnaseq.gui.workflow;

import org.sing_group.rnaseq.api.entities.WorkflowDescription;

/**
 * The Bowtie2, StringTie and R packages workflow description.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class Bowtie2StringTieDifferentialExpressionWorkflowDescription
	implements WorkflowDescription {

	@Override
	public String getTitle() {
		return "Differential expression analysis using Bowtie2, StringTie and R";
	}

	@Override
	public String getShortDescription() {
		return	"This workflow allows performing a differential expression "
			+ "analysis using Bowtie2 to align sample reads, StringTie to "
			+ "assemble transcripts and two R libraries, Ballgown and EdgeR, to "
			+ "perform the differential expression itself. This pipeline is "
			+ "able to compare two conditions with at least two samples each.";
	}
}
