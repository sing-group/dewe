package org.sing_group.rnaseq.gui.components.wizard.steps;

import org.sing_group.rnaseq.api.persistence.ReferenceGenomeIndexDatabaseManager;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenomeIndex;

/**
 * An extension of {@code ReferenceGenomeIndexSelectionStep} that allows the
 * selection of {@code Bowtie2ReferenceGenomeIndex} objects.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class Bowtie2ReferenceGenomeIndexSelectionStep
	extends ReferenceGenomeIndexSelectionStep<Bowtie2ReferenceGenomeIndex> {

	/**
	 * Creates a new {@code Bowtie2ReferenceGenomeIndexSelectionStep} using the
	 * specified {@code databaseManager}.
	 *
	 * @param databaseManager the {@code ReferenceGenomeIndexDatabaseManager}
	 */
	public Bowtie2ReferenceGenomeIndexSelectionStep(
		ReferenceGenomeIndexDatabaseManager databaseManager
	) {
		super(databaseManager, Bowtie2ReferenceGenomeIndex.class);
	}

	@Override
	protected String getReferenceGenomeType() {
		return "Bowtie2";
	}
}
