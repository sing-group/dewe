package org.sing_group.rnaseq.api.persistence.entities.event;

public interface ReferenceGenomeDatabaseListener {
	public abstract void referenceGenomeAdded();

	public abstract void referenceGenomeRemoved();
}
