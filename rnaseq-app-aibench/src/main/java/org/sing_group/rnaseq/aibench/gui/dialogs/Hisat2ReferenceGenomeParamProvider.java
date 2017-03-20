package org.sing_group.rnaseq.aibench.gui.dialogs;

import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenome;
import org.sing_group.rnaseq.core.persistence.entities.DefaultHisat2ReferenceGenome;

public class Hisat2ReferenceGenomeParamProvider
	extends AbstractReferenceGenomeParamProvider {

	@Override
	protected boolean filterGenome(ReferenceGenome genome) {
		return (genome instanceof DefaultHisat2ReferenceGenome);
	}
}