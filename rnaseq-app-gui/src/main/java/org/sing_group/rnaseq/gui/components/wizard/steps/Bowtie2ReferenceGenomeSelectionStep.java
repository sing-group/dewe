package org.sing_group.rnaseq.gui.components.wizard.steps;

import org.sing_group.rnaseq.api.persistence.ReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenome;

/**
 * An extension of {@code ReferenceGenomeSelectionStep} that allows the
 * selection of {@code Bowtie2ReferenceGenome} objects.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class Bowtie2ReferenceGenomeSelectionStep
	extends ReferenceGenomeSelectionStep<Bowtie2ReferenceGenome> {

	/**
	 * Creates a new {@code Bowtie2ReferenceGenomeSelectionStep} using the
	 * specified {@code databaseManager}.
	 * 
	 * @param databaseManager the {@code ReferenceGenomeDatabaseManager}
	 */
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
