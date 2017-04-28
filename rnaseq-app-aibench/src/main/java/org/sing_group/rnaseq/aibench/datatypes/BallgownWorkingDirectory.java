package org.sing_group.rnaseq.aibench.datatypes;

import java.io.File;

import es.uvigo.ei.aibench.core.datatypes.annotation.Datatype;
import es.uvigo.ei.aibench.core.datatypes.annotation.Structure;

/**
 * An AIBench datatype to wrap a Ballgown working directory.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
@Datatype(
	structure = Structure.SIMPLE,
	namingMethod = "getName",
	renameable = true,
	clipboardClassName = "Ballgown working directory",
	autoOpen = true
)
public class BallgownWorkingDirectory {
	private File workingDirectory;

	/**
	 * Creates a new {@code BallgownWorkingDirectory} with the specified
	 * {@code workingDirectory}.
	 *
	 * @param workingDirectory the file that represents the working directory
	 */
	public BallgownWorkingDirectory(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	/**
	 * Returns the name of the datatype
	 *
	 * @return the name of the datatype
	 */
	public String getName() {
		return this.workingDirectory.getName();
	}

	/**
	 * Returns the {@code File} that represents the working directory.
	 *
	 * @return the {@code File} that represents the working directory
	 */
	public File getWorkingDirectory() {
		return this.workingDirectory;
	}
}