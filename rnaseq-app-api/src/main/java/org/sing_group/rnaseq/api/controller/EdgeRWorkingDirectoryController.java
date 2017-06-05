package org.sing_group.rnaseq.api.controller;

import java.util.Optional;

import org.sing_group.rnaseq.api.entities.edger.EdgeRGenes;

/**
 * The interface for controlling the edgeR R package working directory where
 * results generated by {@link EdgeRController} are stored.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface EdgeRWorkingDirectoryController {

	/**
	 * If the genes table is present, then it returns the {@code EdgeRGenes}
	 * list. Otherwise it returns an empty {@code Optional}.
	 *
	 * @return the {@code EdgeRGenes} list wrapped in an {@code Optional}
	 */
	public abstract Optional<EdgeRGenes> getGenes();
}