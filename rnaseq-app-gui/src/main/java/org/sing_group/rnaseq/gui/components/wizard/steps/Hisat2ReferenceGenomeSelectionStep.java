package org.sing_group.rnaseq.gui.components.wizard.steps;

import org.sing_group.rnaseq.api.persistence.ReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenome;

public class Hisat2ReferenceGenomeSelectionStep
		extends ReferenceGenomeSelectionStep<Hisat2ReferenceGenome> {

	public Hisat2ReferenceGenomeSelectionStep(
		ReferenceGenomeDatabaseManager databaseManager
	) {
		super(databaseManager, Hisat2ReferenceGenome.class);
	}

	@Override
	protected String getReferenceGenomeType() {
		return "hisat2";
	}

	@Override
	public void stepEntered() {
	}
}
