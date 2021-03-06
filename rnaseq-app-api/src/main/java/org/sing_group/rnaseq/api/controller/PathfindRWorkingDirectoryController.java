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
package org.sing_group.rnaseq.api.controller;

import java.util.List;
import java.util.Optional;

import org.sing_group.rnaseq.api.entities.pathfindr.PathfindRPathways;

/**
 * The interface for controlling the edgeR R package working directory where
 * results generated by {@link EdgeRController} are stored.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface PathfindRWorkingDirectoryController {

	/**
	 * If the genes table is present, then it returns the {@code EdgeRGenes}
	 * list. Otherwise it returns an empty {@code Optional}.
	 *
	 * @return the {@code EdgeRGenes} list wrapped in an {@code Optional}
	 */
	public abstract Optional<PathfindRPathways> getPathways();

	/**
	 * Returns a list containing the names of the working directory files that
	 * have a view associated to them.
	 *
	 * @return a list containing the names of the working directory files that
	 *         have a view associated to them
	 */
	public abstract List<String> getMissingWorkingDirectoryFiles();


}
