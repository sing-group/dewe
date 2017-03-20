package org.sing_group.rnaseq.aibench.gui.dialogs;

import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenome;
import org.sing_group.rnaseq.core.persistence.entities.DefaultBowtie2ReferenceGenome;

public class Bowtie2ReferenceGenomeParamProvider
	extends AbstractReferenceGenomeParamProvider {

	@Override
	protected boolean filterGenome(ReferenceGenome genome) {
		return (genome instanceof DefaultBowtie2ReferenceGenome);
	}
}