package org.sing_group.rnaseq.core.persistence.entities;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenomeIndex;
import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenomeIndexDatabase;
import org.sing_group.rnaseq.api.persistence.entities.event.ReferenceGenomeIndexDatabaseListener;

/**
 * The default {@code ReferenceGenomeIndexDatabase} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultReferenceGenomeIndexDatabase
	implements ReferenceGenomeIndexDatabase {
	private static final long serialVersionUID = 1L;

	private List<ReferenceGenomeIndex> referenceGenomes = new LinkedList<>();
	private transient List<ReferenceGenomeIndexDatabaseListener> listeners = 
		new LinkedList<>();

	@Override
	public List<ReferenceGenomeIndex> listIndexes() {
		return referenceGenomes;
	}

	@Override
	public void addIndex(ReferenceGenomeIndex refGenome) {
		this.referenceGenomes.add(refGenome);
		this.notifyReferenceGenomeAdded();
	}

	private void notifyReferenceGenomeAdded() {
		getListenersList().forEach(
			ReferenceGenomeIndexDatabaseListener::referenceGenomeIndexAdded);
	}

	@Override
	public void addReferenceGenomeIndexDatabaseListener(
		ReferenceGenomeIndexDatabaseListener listener) {
		getListenersList().add(listener);
	}

	private List<ReferenceGenomeIndexDatabaseListener> getListenersList() {
		if(this.listeners == null) {
			this.listeners = new LinkedList<>();
		}
		return this.listeners;
	}

	@Override
	public <T extends ReferenceGenomeIndex> List<T> listIndexes(
		Class<T> referenceGenomeClass
	) {
		return (List<T>) listIndexes().stream()
				.filter(r -> referenceGenomeClass.isInstance(r))
				.map(r -> referenceGenomeClass.cast(r))
				.collect(Collectors.toList());
	}

	@Override
	public <T extends ReferenceGenomeIndex> List<T> listValidIndexes(
		Class<T> referenceGenomeClass
	) {
		return (List<T>) listIndexes(referenceGenomeClass).stream()
				.filter(r -> r.isValidIndex())
				.map(r -> referenceGenomeClass.cast(r))
				.collect(Collectors.toList());
	}

	@Override
	public void removeIndex(ReferenceGenomeIndex refGenome) {
		this.referenceGenomes.remove(refGenome);
		this.notifyReferenceGenomeRemoved();
	}

	private void notifyReferenceGenomeRemoved() {
		getListenersList().forEach(
			ReferenceGenomeIndexDatabaseListener::referenceGenomeIndexRemoved);
	}
}
