package org.sing_group.rnaseq.core.util;

import java.io.File;
import java.util.Optional;

public class FileUtils {

	/**
	 * Returns the name of {@code file} without the extension (e.g.: for a file
	 * with name {@code data.csv}, {@code data} is returned).
	 * 
	 * @param file a {@code File}.
	 * @return the name of the file without the extension.
	 */
	public static String removeExtension(File file) {
		String fname = file.getName();
		int pos = fname.lastIndexOf(".");
		if (pos > 0) {
			fname = fname.substring(0, pos);
		}
		return fname;
	}

	/**
	 * Returns the absolute path of {@code child} inside {@code parent}.
	 * 
	 * @param parent a {@code File}.
	 * @param child a file name.
	 * @return the absolute path of {@code child} inside {@code parent}.
	 */
	public static String getAbsolutePath(File parent, String child) {
		if (parent == null) {
			return child;
		} else {
			return new File(parent, child).getAbsolutePath();
		}
	}

	/**
	 * Converts an array of files into an array of strings containing the
	 * corresponding absolute paths of the files.
	 * 
	 * @param files an array of {@code File}s.
	 * @return an array of {@cdode String}s strings containing the corresponding
	 * absolute paths of the files.
	 */
	public static String[] asString(File[] files) {
		String[] asString = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			asString[i] = files[i].getAbsolutePath();
		}
		return asString;
	}

	/**
	 * Traverses an array of files looking for a file with name
	 * {@code fileName}. If found, it is returned wrapped as an
	 * {@code Optional<file>}.
	 * 
	 * @param files an array of files.
	 * @param fileName a file name.
	 * @return an {@code Optional<file>} where a file with {@code fileName} can
	 * be found.
	 */
	public static Optional<File> findParentDirectory(File[] files,
		String fileName
	) {
		for (File f : files) {
			if (contains(f, fileName)) {
				return Optional.of(f);
			}
		}
		return Optional.empty();
	}

	/**
	 * Returns true if {@code directory} contains a file with name
	 * {@Code fileName}.
	 * 
	 * @param directory a directory.
	 * @param fileName a file name.
	 * @return {@code true} if {@code directory} contains a file with name
	 * 	{@code fileName} and false otherwise.
	 */
	public static boolean contains(File directory, String fileName) {
		for(File file : directory.listFiles()) {
			if(file.getName().equals(fileName)) {
				return true;
			}
		}
		return false;
	}
}
