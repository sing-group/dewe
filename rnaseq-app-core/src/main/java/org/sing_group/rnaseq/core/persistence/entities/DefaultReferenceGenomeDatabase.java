package org.sing_group.rnaseq.core.persistence.entities;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenome;
import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenomeDatabase;
import org.sing_group.rnaseq.api.persistence.entities.event.ReferenceGenomeDatabaseListener;

public class DefaultReferenceGenomeDatabase implements ReferenceGenomeDatabase {
	private static final long serialVersionUID = 1L;

	private List<ReferenceGenome> referenceGenomes = new LinkedList<>();
	private transient List<ReferenceGenomeDatabaseListener> listeners = new LinkedList<>();

	@Override
	public List<ReferenceGenome> listReferenceGenomes() {
		return referenceGenomes;
	}

	@Override
	public void addReferenceGenome(ReferenceGenome refGenome) {
		this.referenceGenomes.add(refGenome);
		this.notifyReferenceGenomeAdded();
	}

	private void notifyReferenceGenomeAdded() {
		getListenersList().forEach(
			ReferenceGenomeDatabaseListener::referenceGenomeAdded);
	}

	@Override
	public void addReferenceGenomeDatabaseListener(
		ReferenceGenomeDatabaseListener listener) {
		getListenersList().add(listener);
	}

	private List<ReferenceGenomeDatabaseListener> getListenersList() {
		if(this.listeners == null) {
			this.listeners = new LinkedList<>();
		}
		return this.listeners;
	}

	@Override
	public <T extends ReferenceGenome> List<T> listReferenceGenomes(
		Class<T> referenceGenomeClass
	) {
		return (List<T>) listReferenceGenomes().stream()
				.filter(r -> referenceGenomeClass.isInstance(r))
				.map(r -> referenceGenomeClass.cast(r))
				.collect(Collectors.toList());
	}

	@Override
	public <T extends ReferenceGenome> List<T> listValidReferenceGenomes(
		Class<T> referenceGenomeClass
	) {
		return (List<T>) listReferenceGenomes(referenceGenomeClass).stream()
				.filter(r -> r.isValid())
				.map(r -> referenceGenomeClass.cast(r))
				.collect(Collectors.toList());
	}

	public void removeReferenceGenome(ReferenceGenome refGenome) {
		this.referenceGenomes.remove(refGenome);
		this.notifyReferenceGenomeRemoved();
	}

	private void notifyReferenceGenomeRemoved() {
		getListenersList().forEach(
			ReferenceGenomeDatabaseListener::referenceGenomeRemoved);
	}
}
