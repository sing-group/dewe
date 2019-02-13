/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2019 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
	 * @param name the name for the new appender
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
