/*
 * #%L
 * DEWE API
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
package org.sing_group.rnaseq.api.persistence;

import java.util.List;

import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenomeIndex;
import org.sing_group.rnaseq.api.persistence.entities.event.ReferenceGenomeIndexDatabaseListener;

/**
 * The interface that defines the reference genome index database manager.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface ReferenceGenomeIndexDatabaseManager {
	/**
	 * Returns a list with the available {@code ReferenceGenomeIndex}.
	 * 
	 * @return a list with the available {@code ReferenceGenomeIndex}
	 */
	public abstract List<ReferenceGenomeIndex> listIndexes();

	/**
	 * Adds the specified index to the database.
	 * 
	 * @param index the {@code ReferenceGenomeIndex} to add
	 */
	public abstract void addIndex(ReferenceGenomeIndex index);

	/**
	 * Removes the specified index from the database.
	 * 
	 * @param index the {@code ReferenceGenomeIndex} to remove
	 */
	public abstract void removeIndex(ReferenceGenomeIndex index);

	/**
	 * Adds the specified database listener to receive events from the database.
	 *
	 * @param listener the {@code ReferenceGenomeIndexDatabaseListener}
	 */
	public abstract void addReferenceGenomeIndexDatabaseListener(
		ReferenceGenomeIndexDatabaseListener listener);

	/**
	 * Returns a list with the available {@code ReferenceGenomeIndex} of the
	 * specified class.
	 * 
	 * @param indexClass the class that indexes must have
	 * @param <T> the type of the reference genome indexes
	 * 
	 * @return a list with the available {@code ReferenceGenomeIndex} of the
	 *         specified class
	 */
	public abstract <T extends ReferenceGenomeIndex> List<T> listIndexes(
		Class<T> indexClass);

	/**
	 * Returns a list with the available {@code ReferenceGenomeIndex} of the
	 * specified class that are valid (using
	 * {@code ReferenceGenomeIndex#isValidIndex()}).
	 * 
	 * @param indexClass the class that indexes must have
	 * @param <T> the type of the reference genome indexes
	 * 
	 * @return a list with the available {@code ReferenceGenomeIndex} of the
	 *         specified class
	 */
	public abstract <T extends ReferenceGenomeIndex> List<T> listValidIndexes(
		Class<T> indexClass);

	/**
	 * Returns {@code true} if an index with the specified {@code name} exists
	 * in the database and {@code false} otherwise.
	 * 
	 * @param name the name to check
	 * @return {@code true} if an index with the specified {@code name} exists
	 *         in the database and {@code false} otherwise
	 */
	public abstract boolean existsName(String name);
}
