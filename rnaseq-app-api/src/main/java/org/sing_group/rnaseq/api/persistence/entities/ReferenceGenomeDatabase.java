package org.sing_group.rnaseq.api.persistence.entities;

import java.io.Serializable;
import java.util.List;

import org.sing_group.rnaseq.api.persistence.entities.event.ReferenceGenomeDatabaseListener;

public interface ReferenceGenomeDatabase extends Serializable {
	public abstract List<ReferenceGenome> listReferenceGenomes();

	public abstract void addReferenceGenome(ReferenceGenome refGenome);

	public abstract void addReferenceGenomeDatabaseListener(
		ReferenceGenomeDatabaseListener listener);

	public abstract <T extends ReferenceGenome> List<T> listReferenceGenomes(
		Class<T> referenceGenomeClass);

	public abstract <T extends ReferenceGenome> List<T> listValidReferenceGenomes(
		Class<T> referenceGenomeClass);
}
