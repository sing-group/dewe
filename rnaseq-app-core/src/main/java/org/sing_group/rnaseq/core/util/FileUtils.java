package org.sing_group.rnaseq.core.util;

import java.io.File;

public class FileUtils {

	public static String removeExtension(File file) {
		String fname = file.getName();
		int pos = fname.lastIndexOf(".");
		if (pos > 0) {
			fname = fname.substring(0, pos);
		}
		return fname;
	}

	public static String getAbsolutePath(File parent, String child) {
		if (parent == null) {
			return child;
		} else {
			return new File(parent, child).getAbsolutePath();
		}
	}
}
