/*
 * #%L
 * DEWE API
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
import org.sing_group.rnaseq.api.environment.execution.SamtoolsBinariesExecutor;

/**
 * The interface for controlling samtools commands.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface SamtoolsController {
	/**
	 * Sets the {@code SamtoolsBinariesExecutor} that should be used to execute
	 * samtools commands.
	 * 
	 * @param executor the {@code SamtoolsBinariesExecutor}
	 */	
	public abstract void setSamtoolsBinariesExecutor(
		SamtoolsBinariesExecutor executor);

	/**
	 * Converts a file in SAM format into a file in BAM format.
	 * 
	 * @param sam the input file in SAM format
	 * @param bam the output file in BAM format
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract void samToBam(File sam, File bam)
		throws ExecutionException, InterruptedException;
}
