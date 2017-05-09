package org.sing_group.rnaseq.api.persistence.entities;

import java.io.Serializable;
import java.util.List;

import org.sing_group.rnaseq.api.persistence.entities.event.ReferenceGenomeIndexDatabaseListener;
/**
 * The interface that defines the reference genome index database.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface ReferenceGenomeIndexDatabase extends Serializable {
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
}
