package org.sing_group.rnaseq.core.persistence.entities;

import java.util.LinkedList;
import java.util.List;

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
}
