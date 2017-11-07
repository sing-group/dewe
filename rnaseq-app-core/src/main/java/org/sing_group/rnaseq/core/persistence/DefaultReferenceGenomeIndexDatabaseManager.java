/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2017 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
package org.sing_group.rnaseq.core.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.function.Supplier;

import org.sing_group.rnaseq.api.persistence.ReferenceGenomeIndexDatabaseManager;
import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenomeIndex;
import org.sing_group.rnaseq.api.persistence.entities.event.ReferenceGenomeIndexDatabaseListener;
import org.sing_group.rnaseq.core.persistence.entities.DefaultReferenceGenomeIndexDatabase;

/**
 * The default {@code ReferenceGenomeIndexDatabaseManager} implementation. It is
 * implemented as a singleton, so it can be accessed using the
 * {@code DefaultReferenceGenomeIndexDatabaseManager#getInstance()}.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultReferenceGenomeIndexDatabaseManager
	implements ReferenceGenomeIndexDatabaseManager {
	private static DefaultReferenceGenomeIndexDatabaseManager INSTANCE = null;
	private DefaultReferenceGenomeIndexDatabase database = 
		new DefaultReferenceGenomeIndexDatabase();
	private Supplier<File> file;
	
	private DefaultReferenceGenomeIndexDatabaseManager() {
	}

	/**
	 * Returns the singleton {@code DefaultReferenceGenomeIndexDatabaseManager}
	 * instance.
	 * 
	 * @return the singleton instance
	 */
	public static DefaultReferenceGenomeIndexDatabaseManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DefaultReferenceGenomeIndexDatabaseManager();
		}
		return INSTANCE;
	}

	/**
	 * Sets the supplier of the file where the database must be saved.
	 * 
	 * @param supplier the suppleir of the file where the database must be saved
	 */
	public void setPersistenceStorageFileProvider(Supplier<File> supplier) {
		this.file = supplier;
	}
	
	/**
	 * Loads the database using the persistence storage file established with
	 * {@link DefaultReferenceGenomeIndexDatabaseManager#setPersistenceStorageFileProvider(Supplier)}.
	 * 
	 * @throws FileNotFoundException if the file does not exist
	 * @throws IOException if an error occurs when reading the file
	 * @throws ClassNotFoundException if an error occurs when deserializing the
	 * 		   database
	 */
	public void loadDatabase() 
		throws FileNotFoundException, IOException, ClassNotFoundException 
	{
		FileInputStream fis = new FileInputStream(this.file.get());
		ObjectInputStream ois = new ObjectInputStream(fis);
		DefaultReferenceGenomeIndexDatabase readObject = 
			(DefaultReferenceGenomeIndexDatabase) ois.readObject();
		this.database = readObject;
		ois.close();
		fis.close();
	}

	/**
	 * Saves the database in the persistence storage file established with
	 * {@link DefaultReferenceGenomeIndexDatabaseManager#setPersistenceStorageFileProvider(Supplier)}.
	 * 
	 * @throws IOException if an error occurs when reading the file
	 */
	public void persistDatabase() throws IOException {
		FileOutputStream fos = new FileOutputStream(this.file.get());
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(this.database);
		oos.close();
		fos.close();
	}
	
	@Override
	public List<ReferenceGenomeIndex> listIndexes() {
		return database.listIndexes();
	}

	@Override
	public void addIndex(ReferenceGenomeIndex refGenome) {
		this.database.addIndex(refGenome);
	}

	@Override
	public void removeIndex(ReferenceGenomeIndex refGenome) {
		this.database.removeIndex(refGenome);
	}

	@Override
	public void addReferenceGenomeIndexDatabaseListener(
			ReferenceGenomeIndexDatabaseListener listener) {
		this.database.addReferenceGenomeIndexDatabaseListener(listener);
	}

	@Override
	public <T extends ReferenceGenomeIndex> List<T> listIndexes(
		Class<T> referenceGenomeClass
	) {
		return database.listIndexes(referenceGenomeClass);
	}

	@Override
	public <T extends ReferenceGenomeIndex> List<T> listValidIndexes(
		Class<T> referenceGenomeClass
	) {
		return database.listValidIndexes(referenceGenomeClass);
	}
	
	@Override
	public boolean existsName(String name) {
		return listIndexes().stream()
			.filter(g -> g.getName().equals(name)).findAny().isPresent();
	}
}
