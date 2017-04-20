package org.sing_group.rnaseq.core.util;

import java.io.File;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * An utility class that provides methods related with logging functionalities.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class LoggingUtils {

	/**
	 * Creates a new {@code FileAppender} for the specified {@code file} and
	 * adds it to {@code logger}.
	 * 
	 * @param file the {@code File} to write the log
	 * @param logger the {@code Logger} to add the new appender
	 * 
	 * @return the created {@code FileAppender}
	 */
	public static FileAppender createAndAddFileAppender(File file,
		Logger logger, String name
	) {
		FileAppender fa = new FileAppender();
		fa.setName(name);
		fa.setFile(file.getAbsolutePath());
		fa.setLayout(new PatternLayout("[%d{HH:mm:ss}] %m%n"));
		fa.setThreshold(Level.DEBUG);
		fa.setAppend(true);
		fa.activateOptions();
		logger.addAppender(fa);

		return fa;
	}
}
