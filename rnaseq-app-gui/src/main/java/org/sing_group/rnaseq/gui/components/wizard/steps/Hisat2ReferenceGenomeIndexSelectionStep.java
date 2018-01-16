/*
 * #%L
 * DEWE GUI
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
 * 			Borja Sánchez, and Anália Lourenço
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
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
