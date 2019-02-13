/*
 * #%L
 * DEWE API
 * %%
 * Copyright (C) 2016 - 2019 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola,
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
package org.sing_group.rnaseq.api.entities;

import java.util.Collections;
import java.util.List;

import javax.swing.Action;

/**
 * The description of a workflow.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface WorkflowDescription {

	/**
	 * Returns the title of the workflow.
	 *
	 * @return the title of the workflow
	 */
	public abstract String getTitle();

	/**
	 * Returns a short description of the workflow.
	 *
	 * @return a short description of the workflow
	 */
	public abstract String getShortDescription();

	/**
	 * Whether the run workflow option is enabled.
	 *
	 * @return {@code true} if the run workflow option is enabled and
	 *         {@code false} otherwise
	 */
	public default boolean isRunWorkflowOptionEnabled() {
		return true;
	};

	/**
	 * Whether the import workflow option is enabled.
	 *
	 * @return {@code true} if the import workflow option is enabled and
	 *         {@code false} otherwise
	 */
	public default boolean isImportWorkflowOptionEnabled() {
		return true;
	};

	/**
	 * Returns a list of additional workflow actions.
	 *
	 * @return a list of additional {@code Action}s
	 */
	public default List<Action> getAdditionalWorkflowActions() {
		return Collections.emptyList();
	}
}
