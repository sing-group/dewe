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
package org.sing_group.rnaseq.api.controller;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.SystemBinariesExecutor;

/**
 * The interface for controlling system commands.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface SystemController {
	/**
	 * Sets the {@code SystemBinariesExecutor} that should be used to execute
	 * system commands.
	 * 
	 * @param executor the {@code SystemBinariesExecutor}
	 */
	public abstract void setSystemBinariesExecutor(
		SystemBinariesExecutor executor);

	/**
	 * Joins files {@code a} and {@code b} and writes the result into
	 * {@code result}.
	 * 
	 * @param a the first file to be joined
	 * @param b the second file to be joined
	 * @param result the file to store the result
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract void join(File a, File b, File result)
		throws ExecutionException, InterruptedException;

	/**
	 * Joins all files in {@code files} and writes the result into
	 * {@code result}.
	 * 
	 * @param files the array of files to be joined
	 * @param result the file to store the result
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */	
	public abstract void join(File[] files, File result)
		throws ExecutionException, InterruptedException;

	/**
	 * Executes sed using the specified parameters. 
	 * 
	 * @param params the parameters to be passed to sed
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract void sed(String...params)
		throws ExecutionException, InterruptedException;
	
	/**
	 * Executes awk using the specified parameters. 
	 * 
	 * @param result the file to store the result
	 * @param params the parameters to be passed to awk
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract void awk(File result, String...params)
		throws ExecutionException, InterruptedException;
}
