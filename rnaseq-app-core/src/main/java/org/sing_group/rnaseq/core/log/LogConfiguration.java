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
package org.sing_group.rnaseq.core.log;

import org.slf4j.Marker;
import org.slf4j.helpers.BasicMarkerFactory;

/**
 * Log configuration settings.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class LogConfiguration {
	public static final String EXECUTION_STD_MARKER_LABEL = "EXECUTION_STD";
	public static final String EXECUTION_ERROR_MARKER_LABEL = "EXECUTION_ERROR";

	public static final Marker MARKER_EXECUTION_STD;
	public static final Marker MARKER_EXECUTION_ERROR;

	static {
		final BasicMarkerFactory markerFactory = new BasicMarkerFactory();
		MARKER_EXECUTION_STD = markerFactory.getMarker(LogConfiguration.EXECUTION_STD_MARKER_LABEL);
		MARKER_EXECUTION_ERROR = markerFactory.getMarker(LogConfiguration.EXECUTION_ERROR_MARKER_LABEL);
	}
}