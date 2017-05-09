package org.sing_group.rnaseq.aibench.gui.dialogs;

import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenomeIndex;
import org.sing_group.rnaseq.core.persistence.entities.DefaultHisat2ReferenceGenomeIndex;

/**
 * An extension of {@code AbstractReferenceGenomeIndexParamProvider} that allows
 * selecting HISAT2 reference genome indexes.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class Hisat2ReferenceGenomeIndexParamProvider
	extends AbstractReferenceGenomeIndexParamProvider {

	@Override
	protected boolean filterGenome(ReferenceGenomeIndex genome) {
		return (genome instanceof DefaultHisat2ReferenceGenomeIndex);
	}
}