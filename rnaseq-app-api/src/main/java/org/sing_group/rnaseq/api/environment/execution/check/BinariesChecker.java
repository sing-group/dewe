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

import org.sing_group.rnaseq.api.environment.binaries.Binaries;

/**
 * The interface that defines a binaries checker.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 * @param <B> the type of the {@code Binaries} to execute
 */
public interface BinariesChecker<B extends Binaries> {
	/**
	 * Sets the binaries to check.
	 * 
	 * @param binaries the binaries to be check
	 */
	public abstract void setBinaries(B binaries);

	/**
	 * Checks all commands associated to the current binaries.
	 * 
	 * @throws BinaryCheckException if any of the commands can't be executed
	 */
	public abstract void checkAll() throws BinaryCheckException;
}