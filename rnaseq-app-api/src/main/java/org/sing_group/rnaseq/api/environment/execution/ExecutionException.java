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
package org.sing_group.rnaseq.api.environment.execution;

/**
 * A class that represents an exception occurred during binaries execution.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class ExecutionException extends Exception {
	private static final long serialVersionUID = 1L;

	private final int exitStatus;
	private final String command;

	/**
	 * Creates a new {@code ExecutionException} for the specified command and
	 * status.
	 * 
	 * @param exitStatus the exit status of the command
	 * @param command the command being executed
	 */
	public ExecutionException(int exitStatus, String command) {
		super();
		this.exitStatus = exitStatus;
		this.command = command;
	}

	/**
	 * Creates a new {@code ExecutionException} for the specified command and
	 * status with an informative message.
	 * 
	 * @param exitStatus the exit status of the command
	 * @param message the exception message
	 * @param command the command being executed
	 */
	public ExecutionException(int exitStatus, String message, String command) {
		super(message);
		this.exitStatus = exitStatus;
		this.command = command;
	}

	/**
	 * Creates a new {@code ExecutionException} for the specified command and
	 * status with a {@code Throwable} cause.
	 * 
	 * @param exitStatus the exit status of the command
	 * @param cause a {@code Throwable} object.
	 * @param command the command being executed
	 */
	public ExecutionException(int exitStatus, Throwable cause, String command) {
		super(cause);
		this.exitStatus = exitStatus;
		this.command = command;
	}

	/**
	 * Creates a new {@code ExecutionException} for the specified command and
	 * status with an informative message and a {@code Throwable} cause.
	 * 
	 * @param exitStatus the exit status of the command
	 * @param message the exception message
	 * @param cause a {@code Throwable} object. 
	 * @param command the command being executed
	 */
	public ExecutionException(int exitStatus, String message, Throwable cause, 
		String command
	) {
		super(message, cause);
		this.exitStatus = exitStatus;
		this.command = command;
	}

	/**
	 * Returns the command being executed.
	 * 
	 * @return the command being executed
	 */
	public String getCommand() {
		return command;
	}
	
	/**
	 * Returns the exit status of the command.
	 * 
	 * @return the exit status of the command
	 */
	public int getExitStatus() {
		return exitStatus;
	}
}
