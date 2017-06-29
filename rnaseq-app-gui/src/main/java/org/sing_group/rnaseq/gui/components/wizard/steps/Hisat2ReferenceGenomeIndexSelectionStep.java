package org.sing_group.rnaseq.gui.components.wizard.steps;

import org.sing_group.rnaseq.api.persistence.ReferenceGenomeIndexDatabaseManager;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenomeIndex;

/**
 * An extension of {@code ReferenceGenomeIndexSelectionStep} that allows the
 * selection of {@code Hisat2ReferenceGenomeIndex} objects.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class Hisat2ReferenceGenomeIndexSelectionStep
	extends ReferenceGenomeIndexSelectionStep<Hisat2ReferenceGenomeIndex> {

	/**
	 * Creates a new {@code Hisat2ReferenceGenomeIndexSelectionStep} using the
	 * specified {@code databaseManager}.
	 *
	 * @param databaseManager the {@code ReferenceGenomeIndexDatabaseManager}
	 */
	public Hisat2ReferenceGenomeIndexSelectionStep(
		ReferenceGenomeIndexDatabaseManager databaseManager
	) {
		super(databaseManager, Hisat2ReferenceGenomeIndex.class);
	}

	@Override
	protected String getReferenceGenomeType() {
		return "HISAT2";
	}
}
