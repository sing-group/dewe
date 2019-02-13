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
package org.sing_group.rnaseq.api.environment.execution;

import org.sing_group.rnaseq.api.environment.binaries.Binaries;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;

/**
 * The interface that defines a {@code Binaries} executor.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 * @param <B> the type of the {@code Binaries} to execute
 */
public interface BinariesExecutor<B extends Binaries> {
	/**
	 * Sets the binaries to execute.
	 * 
	 * @param binaries the binaries to execute
	 * @throws BinaryCheckException if an error occurs while checking the 
	 * 		   binaries
	 */
	public abstract void setBinaries(B binaries)
		throws BinaryCheckException;

	/**
	 * Checks that the established binaries can be used.
	 * 
	 * @throws BinaryCheckException if an error occurs while checking the 
	 * 		   binaries
	 */
	public abstract void checkBinaries()
		throws BinaryCheckException;
	
	/**
	 * An input line call back.
	 * 
	 * @author Hugo López-Fernández
	 * @author Aitor Blanco-Míguez
	 *
	 */
	public static interface InputLineCallback {
		public void info(String message);
		public void line(String line);
		public void error(String message, Exception e);
		public void inputStarted();
		public void inputFinished();
	}
}