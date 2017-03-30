package org.sing_group.rnaseq.core.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.sing_group.rnaseq.api.persistence.ReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenome;
import org.sing_group.rnaseq.api.persistence.entities.event.ReferenceGenomeDatabaseListener;
import org.sing_group.rnaseq.core.persistence.entities.DefaultReferenceGenomeDatabase;

public class DefaultReferenceGenomeDatabaseManager
	implements ReferenceGenomeDatabaseManager {
	private static DefaultReferenceGenomeDatabaseManager INSTANCE = null;
	private DefaultReferenceGenomeDatabase database = new DefaultReferenceGenomeDatabase();
	private File file;
	
	private DefaultReferenceGenomeDatabaseManager() {
	}

	public static DefaultReferenceGenomeDatabaseManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DefaultReferenceGenomeDatabaseManager();
		}
		return INSTANCE;
	}

	public void setPersistenceStorageFile(File file) {
		this.file = file;
	}
	
	public void loadDatabase() 
		throws FileNotFoundException, IOException, ClassNotFoundException 
	{
		FileInputStream fis = new FileInputStream(this.file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		DefaultReferenceGenomeDatabase readObject = (DefaultReferenceGenomeDatabase) ois.readObject();
		this.database = readObject;
		ois.close();
		fis.close();
	}

	@Override
	public List<ReferenceGenome> listReferenceGenomes() {
		return database.listReferenceGenomes();
	}

	@Override
	public void addReferenceGenome(ReferenceGenome refGenome) {
		this.database.addReferenceGenome(refGenome);
	}

	@Override
	public void removeReferenceGenome(ReferenceGenome refGenome) {
		this.database.removeReferenceGenome(refGenome);
	}

	public void persistDatabase() throws IOException {
		FileOutputStream fos = new FileOutputStream(this.file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(this.database);
		oos.close();
		fos.close();
	}

	@Override
	public void addReferenceGenomeDatabaseListener(
			ReferenceGenomeDatabaseListener listener) {
		this.database.addReferenceGenomeDatabaseListener(listener);
	}

	@Override
	public <T extends ReferenceGenome> List<T> listReferenceGenomes(
		Class<T> referenceGenomeClass
	) {
		return database.listReferenceGenomes(referenceGenomeClass);
	}

	@Override
	public <T extends ReferenceGenome> List<T> listValidReferenceGenomes(
		Class<T> referenceGenomeClass
	) {
		return database.listValidReferenceGenomes(referenceGenomeClass);
	}
}
