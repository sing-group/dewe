/*
 * #%L
 * DEWE API
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
package org.sing_group.rnaseq.api.environment;

/**
 * The interface that defines the system environment.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface SystemEnvironment {
	/**
	 * Returns the name of the default join command.
	 * 
	 * @return the name of the default join command
	 */
	public abstract String getDefaultJoin();

	/**
	 * Returns the name of the default sed command.
	 * 
	 * @return the name of the default sed command
	 */
	public abstract String getDefaultSed();

	/**
	 * Returns the name of the default awk command.
	 * 
	 * @return the name of the default awk command
	 */
	public abstract String getDefaultAwk();
}
