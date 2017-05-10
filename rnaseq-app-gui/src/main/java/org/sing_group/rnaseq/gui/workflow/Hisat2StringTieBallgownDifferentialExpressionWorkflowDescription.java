package org.sing_group.rnaseq.gui.workflow;

import org.sing_group.rnaseq.api.entities.WorkflowDescription;

public class Hisat2StringTieBallgownDifferentialExpressionWorkflowDescription
	implements WorkflowDescription {

	@Override
	public String getTitle() {
		return "Differential expression analysis using HISAT2, StringTie and Ballgown";
	}

	@Override
	public String getShortDescription() {
		return	"This workflow allows performing a differential expression "
			+ "analysis using HISAT2 to align sample reads, StringTie to "
			+ "assemble transcripts and Ballgown perform the differential "
			+ "expression itself. This pipeline has been described by "
			+ "Pertea, M. et al. in 'Transcript-level expression analysis of "
			+ "RNA-seq experiments with HISAT, StringTie and Ballgown' (Nature "
			+ "Protocols 11, 1650–1667 (2016), doi:10.1038/nprot.2016.095).";
	}
}
