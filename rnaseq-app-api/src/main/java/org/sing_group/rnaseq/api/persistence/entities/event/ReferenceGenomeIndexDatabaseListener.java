package org.sing_group.rnaseq.api.persistence.entities.event;

/**
 * The listener interface for receiving reference genome database events.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface ReferenceGenomeIndexDatabaseListener {
	/**
	 * Invoked when a reference genome index is added.
	 */
	public abstract void referenceGenomeIndexAdded();

	/**
	 * Invoked when a reference genome index is removed.
	 */
	public abstract void referenceGenomeIndexRemoved();
}
