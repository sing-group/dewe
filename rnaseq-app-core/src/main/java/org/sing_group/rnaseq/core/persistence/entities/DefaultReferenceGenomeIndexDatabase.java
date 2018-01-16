/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
 * 			Borja Sánchez, and Anália Lourenço
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
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
