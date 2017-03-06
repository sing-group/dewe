package org.sing_group.rnaseq.gui.components.wizard.steps;

import org.sing_group.rnaseq.api.persistence.ReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenome;

public class Bowtie2ReferenceGenomeSelectionStep
		extends ReferenceGenomeSelectionStep<Bowtie2ReferenceGenome> {

	public Bowtie2ReferenceGenomeSelectionStep(
		ReferenceGenomeDatabaseManager databaseManager
	) {
		super(databaseManager, Bowtie2ReferenceGenome.class);
	}

	@Override
	protected String getReferenceGenomeType() {
		return "bowtie2";
	}

	@Override
	public void stepEntered() {
	}
}
