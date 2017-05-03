package org.sing_group.rnaseq.gui.components.wizard.steps;

import org.sing_group.rnaseq.api.persistence.ReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenome;

/**
 * An extension of {@code ReferenceGenomeSelectionStep} that allows the
 * selection of {@code Hisat2ReferenceGenome} objects.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class Hisat2ReferenceGenomeSelectionStep
	extends ReferenceGenomeSelectionStep<Hisat2ReferenceGenome> {

	/**
	 * Creates a new {@code Hisat2ReferenceGenomeSelectionStep} using the
	 * specified {@code databaseManager}.
	 * 
	 * @param databaseManager the {@code ReferenceGenomeDatabaseManager}
	 */
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
