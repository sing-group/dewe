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
package org.sing_group.rnaseq.api.environment.execution.check;

import org.sing_group.rnaseq.api.environment.binaries.SystemBinaries;

/**
 * The interface that defines a system binaries checker.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface SystemBinariesChecker extends BinariesChecker<SystemBinaries> {
	/**
	 * Checks the join command.
	 *
	 * @throws BinaryCheckException if the command can't be executed
	 */
	public void checkJoin() throws BinaryCheckException;

	/**
	 * Checks the sed command.
	 *
	 * @throws BinaryCheckException if the command can't be executed
	 */
	public void checkSed() throws BinaryCheckException;

	/**
	 * Checks the awk command.
	 *
	 * @throws BinaryCheckException if the command can't be executed
	 */
	public void checkAwk() throws BinaryCheckException;
}
