/*
 * #%L
 * DEWE API
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
package org.sing_group.rnaseq.api.environment.binaries;

/**
 * The interface that defines system binaries.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface SystemBinaries extends Binaries {
	public final static String SYSTEM_PREFIX = "system.";
	public final static String BASE_DIRECTORY_PROP = SYSTEM_PREFIX + "binDir";
	public final static String BASE_DIRECTORY_2_PROP = SYSTEM_PREFIX + "binDir2";

	/**
	 * Returns a string with the full path to the join command.
	 *  
	 * @return a string with the full path to the join command
	 */
	public abstract String getJoin();

	/**
	 * Returns a string with the full path to the sed command.
	 *  
	 * @return a string with the full path to the sed command
	 */
	public abstract String getSed();

	/**
	 * Returns a string with the full path to the awk command.
	 *  
	 * @return a string with the full path to the awk command
	 */
	public abstract String getAwk();
}
