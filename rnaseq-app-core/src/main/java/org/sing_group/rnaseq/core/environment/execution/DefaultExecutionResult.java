/*
 * #%L
 * DEWE Core
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
package org.sing_group.rnaseq.core.environment.execution;

import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;

/**
 * The default {@code ExecutionResult} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultExecutionResult implements ExecutionResult {
	private final int exitStatus;
	private final String output;
	private final String error;
	
	/**
	 * Creates a new {@code DefaultExecutionResult} instance with the specified
	 * exit status.
	 * 
	 * @param exitStatus the exit status
	 */
	public DefaultExecutionResult(int exitStatus) {
		this(exitStatus, null, null);
	}
	
	/**
	 * Creates a new {@code DefaultExecutionResult} instance with the specified
	 * exit status and the output and error results.
	 * 
	 * @param exitStatus the exit status
	 * @param output the output string
	 * @param error the error string
	 */	
	public DefaultExecutionResult(int exitStatus, String output, String error) {
		this.exitStatus = exitStatus;
		this.output = output;
		this.error = error;
	}
	
	@Override
	public int getExitStatus() {
		return exitStatus;
	}
	
	@Override
	public String getOutput() {
		return output;
	}
	
	@Override
	public String getError() {
		return error;
	}
}