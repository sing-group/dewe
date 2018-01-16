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
package org.sing_group.rnaseq.api.environment.execution.check;

/**
 * A class that represents an exception occurred during binaries checking.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class BinaryCheckException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private final String command;

	/**
	 * Creates a new {@code BinaryCheckException} for the specified command.
	 * 
	 * @param command the command being checked
	 */
	public BinaryCheckException(String command) {
		super();
		this.command = command;
	}

	/**
	 * Creates a new {@code BinaryCheckException} for the specified command with
	 * an informative message.
	 * 
	 * @param message the exception message
	 * @param command the command being checked
	 */
	public BinaryCheckException(String message, String command) {
		super(message);
		this.command = command;
	}

	/**
	 * Creates a new {@code BinaryCheckException} for the specified command with
	 * a {@code Throwable} cause.
	 * 
	 * @param cause a {@code Throwable} object.
	 * @param command the command being executed
	 */
	public BinaryCheckException(Throwable cause, String command) {
		super(cause);
		this.command = command;
	}

	/**
	 * Creates a new {@code BinaryCheckException} for the specified command with
	 * an informative message and a {@code Throwable} cause.
	 * 
	 * @param message the exception message
	 * @param cause a {@code Throwable} object.
	 * @param command the command being executed
	 */
	public BinaryCheckException(String message, Throwable cause, String command) {
		super(message, cause);
		this.command = command;
	}
	
	/**
	 * Returns the command being checked.
	 * 
	 * @return the command being checked
	 */
	public String getCommand() {
		return command;
	}
}
