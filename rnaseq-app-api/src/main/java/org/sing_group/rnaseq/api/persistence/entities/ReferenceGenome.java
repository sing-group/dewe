package org.sing_group.rnaseq.api.persistence.entities;

import java.io.File;
import java.io.Serializable;
import java.util.Optional;

public interface ReferenceGenome extends Serializable {
	public abstract boolean isValid();

	public abstract String getType();

	public abstract File getReferenceGenome();

	public abstract Optional<String> getReferenceGenomeIndex();
}
