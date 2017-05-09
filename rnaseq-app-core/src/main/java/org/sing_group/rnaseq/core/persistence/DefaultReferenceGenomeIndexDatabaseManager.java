package org.sing_group.rnaseq.core.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

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
	private File file;
	
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
	 * Sets the file where the database must be saved.
	 * 
	 * @param file the file where the database must be saved.
	 */
	public void setPersistenceStorageFile(File file) {
		this.file = file;
	}
	
	/**
	 * Loads the database using the persistence storage file established with
	 * {@link DefaultReferenceGenomeIndexDatabaseManager#setPersistenceStorageFile(File)}.
	 * 
	 * @throws FileNotFoundException if the file does not exist
	 * @throws IOException if an error occurs when reading the file
	 * @throws ClassNotFoundException if an error occurs when deserializing the
	 * 		   database
	 */
	public void loadDatabase() 
		throws FileNotFoundException, IOException, ClassNotFoundException 
	{
		FileInputStream fis = new FileInputStream(this.file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		DefaultReferenceGenomeIndexDatabase readObject = 
			(DefaultReferenceGenomeIndexDatabase) ois.readObject();
		this.database = readObject;
		ois.close();
		fis.close();
	}

	/**
	 * Saves the database in the persistence storage file established with
	 * {@link DefaultReferenceGenomeIndexDatabaseManager#setPersistenceStorageFile(File)}.
	 * 
	 * @throws IOException if an error occurs when reading the file
	 */
	public void persistDatabase() throws IOException {
		FileOutputStream fos = new FileOutputStream(this.file);
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
