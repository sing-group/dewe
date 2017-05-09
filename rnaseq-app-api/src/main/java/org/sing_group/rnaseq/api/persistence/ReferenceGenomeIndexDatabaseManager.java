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
