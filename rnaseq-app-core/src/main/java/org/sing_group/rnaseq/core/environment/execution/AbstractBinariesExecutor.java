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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.sing_group.rnaseq.api.environment.binaries.Binaries;
import org.sing_group.rnaseq.api.environment.execution.BinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.log.LogConfiguration;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * An abstract class providing a base implementation of the
 * {@code BinariesExecutor} interface.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 * @param <B> the type of the {@code Binaries} to execute
 */
public abstract class AbstractBinariesExecutor<B extends Binaries>
	implements BinariesExecutor<B> {
	protected B binaries;

	protected String getThreads() {
		return Integer.toString(DefaultAppController.getInstance().getThreads());
	}

	@Override
	public void setBinaries(B binaries) throws BinaryCheckException {
		this.binaries = binaries;
	}

	protected static String commandToString(String command, String... params) {
		final StringBuilder sb = new StringBuilder(command);

		for (String param : params) {
			sb.append(' ').append(param);
		}

		return sb.toString();
	}

	protected static void inputStarted(List<InputLineCallback> callbacks) {
		for (InputLineCallback callback : callbacks) {
			callback.inputStarted();
		}
	}

	protected static void inputFinished(List<InputLineCallback> callbacks) {
		for (InputLineCallback callback : callbacks) {
			callback.inputFinished();
		}
	}

	protected static void notifyInfo(List<InputLineCallback> callbacks,
		String message
	) {
		for (InputLineCallback callback : callbacks) {
			callback.info(message);
		}
	}

	protected static void notifyLine(List<InputLineCallback> callbacks,
		String line
	) {
		for (InputLineCallback callback : callbacks) {
			callback.line(line);
		}
	}

	protected static void notifyError(List<InputLineCallback> callbacks,
		String message, Exception e
	) {
		for (InputLineCallback callback : callbacks) {
			callback.error(message, e);
		}
	}

	protected static ExecutionResult executeCommand(final Logger log,
		final boolean logOutput, List<InputLineCallback> callbacks,
		String command, String... params
	)
		throws ExecutionException, InterruptedException {
		if (callbacks.isEmpty()) {
			return executeCommand(log, logOutput, command, params);
		} else {
			final List<InputLineCallback> newCallbacks = new ArrayList<>(callbacks);

			class LoggerNoOutputCallback implements InputLineCallback {
				@Override
				public void line(String line) {}

				@Override
				public void inputFinished() {}

				@Override
				public void inputStarted() {}

				@Override
				public void info(String message) {
					log.info(LogConfiguration.MARKER_EXECUTION_STD, message);
				}

				@Override
				public void error(String message, Exception e) {
					log.error(LogConfiguration.MARKER_EXECUTION_ERROR, message, e);
				}
			};

			class LoggerCallback extends LoggerNoOutputCallback {
				@Override
				public void line(String line) {
					if (logOutput)
						log.info(LogConfiguration.MARKER_EXECUTION_STD, line);
				}
			};

			if (logOutput) {
				newCallbacks.add(new LoggerCallback());
			} else {
				newCallbacks.add(new LoggerNoOutputCallback());
			}

			return executeCommand(newCallbacks, command, params);
		}
	}

	protected static ExecutionResult executeCommand(
		List<InputLineCallback> callbacks, String command, String... params
	) throws ExecutionException, InterruptedException {
		params = removeEmptyParams(params);
		final String commandString = commandToString(command, params);

		notifyInfo(callbacks, "Executing command: " + commandString);

		final Runtime runtime = Runtime.getRuntime();

		try {
			inputStarted(callbacks);

			final String[] cmdarray = new String[params.length+1];
			cmdarray[0] = command;
			System.arraycopy(params, 0, cmdarray, 1, params.length);

			final Process process = runtime.exec(cmdarray);

			final LoggerCallbackInputThread inThread =
				new LoggerCallbackInputThread(
					commandString, process.getInputStream(), callbacks
				);
			final LoggerCallbackErrorThread errThread =
				new LoggerCallbackErrorThread(
					commandString, process.getErrorStream(), callbacks
				);

			inThread.start();
			errThread.start();

			final int outputCode = process.waitFor();

			inThread.join();
			errThread.join();

			return new DefaultExecutionResult(outputCode);
		} catch (IOException e) {
			notifyError(callbacks, "Error executing command: " + commandString, e);

			throw new ExecutionException(-1, e, command);
		} finally {
			inputFinished(callbacks);
		}
	}

	protected static ExecutionResult executeCommand(Logger log, String command,
		String... params
	) throws ExecutionException, InterruptedException {
		return executeCommand(log, true, command, params);
	}

	protected static ExecutionResult executeCommand(Logger log,
		boolean logOutput, String command, String... params
	) throws ExecutionException, InterruptedException {
		params = removeEmptyParams(params);
		final String commandString = commandToString(command, params);

//		log.info("Executing command: " + commandString);

		final Runtime runtime = Runtime.getRuntime();

		try {
			final String[] cmdarray = new String[params.length+1];
			cmdarray[0] = command;
			System.arraycopy(params, 0, cmdarray, 1, params.length);

			final Process process = runtime.exec(cmdarray);

			final LoggerThread inThread = new LoggerThread(
				commandString, process.getInputStream(), log, logOutput, LogConfiguration.MARKER_EXECUTION_STD
			);
			final LoggerThread errThread = new LoggerThread(
				commandString, process.getErrorStream(), log, true, LogConfiguration.MARKER_EXECUTION_ERROR
			);

			inThread.start();
			errThread.start();

			final int outputCode = process.waitFor();

			inThread.join();
			errThread.join();

			return new DefaultExecutionResult(outputCode, inThread.getText(), errThread.getText());
		} catch (IOException e) {
			log.error(LogConfiguration.MARKER_EXECUTION_ERROR, "Error executing command: " + commandString, e);

			throw new ExecutionException(-1, e, command);
		}
	}

	protected static ExecutionResult executeCommand(File redirectOutput,
		Logger log, String command, String... params
	) throws ExecutionException, InterruptedException {
		return executeCommand(redirectOutput, null, log, command, params);
	}

	protected static ExecutionResult executeCommand(File redirectOutput,
		File redirectError, Logger log, String command, String... params
	) throws ExecutionException, InterruptedException {
		params = removeEmptyParams(params);
		final String commandString = commandToString(command, params);

//		log.info("Executing command: " + commandString);

		try {
			final String[] cmdarray = new String[params.length+1];
			cmdarray[0] = command;
			System.arraycopy(params, 0, cmdarray, 1, params.length);

			ProcessBuilder builder = new ProcessBuilder(cmdarray);
			if (redirectOutput != null) {
				builder.redirectOutput(redirectOutput);
			}
			if (redirectError != null) {
				builder.redirectError(redirectError);
			}
			final Process process = builder.start();

			final LoggerThread inThread = new LoggerThread(
				commandString, process.getInputStream(), log, true, LogConfiguration.MARKER_EXECUTION_STD
			);
			inThread.start();
			final LoggerThread errThread = new LoggerThread(
				commandString, process.getErrorStream(), log, true, LogConfiguration.MARKER_EXECUTION_ERROR
			);
			errThread.start();

			final int outputCode = process.waitFor();

			if(outputCode == 1) {
				throw new IOException("See " + redirectError.getName() + " file for details.");
			}

			inThread.join();
			errThread.join();

			return new DefaultExecutionResult(outputCode, inThread.getText(), errThread.getText());
		} catch (IOException e) {
			log.error(LogConfiguration.MARKER_EXECUTION_ERROR, "Error executing command: " + commandString, e);

			throw new ExecutionException(-1, e, command);
		}
	}

	private static String[] removeEmptyParams(String[] params) {
		List<String> toret = new LinkedList<String>();
		for (String param : params) {
			if (param != null && !param.isEmpty()) {
				toret.add(param);
			}
		}
		return toret.toArray(new String[toret.size()]);
	}

	//TODO: Refactorize executeCommandMethods
	protected static ExecutionResult executeCommand(
		final Logger log, final boolean logOutput,
		List<InputLineCallback> callbacks,
		String[] envp, File workingDirectory,
		String command, String ... params
	) throws ExecutionException, InterruptedException {
		if (callbacks.isEmpty()) {
			return executeCommand(log, logOutput, command, params);
		} else {
			final List<InputLineCallback> newCallbacks = new ArrayList<>(callbacks);

			class LoggerNoOutputCallback implements InputLineCallback {
				@Override
				public void line(String line) {}

				@Override
				public void inputFinished() {}

				@Override
				public void inputStarted() {}

				@Override
				public void info(String message) {
					log.info(LogConfiguration.MARKER_EXECUTION_STD, message);
				}

				@Override
				public void error(String message, Exception e) {
					log.error(LogConfiguration.MARKER_EXECUTION_ERROR, message, e);
				}
			};

			class LoggerCallback extends LoggerNoOutputCallback {
				@Override
				public void line(String line) {
					if (logOutput)
						log.info(LogConfiguration.MARKER_EXECUTION_STD, line);
				}
			};

			if (logOutput) {
				newCallbacks.add(new LoggerCallback());
			} else {
				newCallbacks.add(new LoggerNoOutputCallback());
			}

			return executeCommand(newCallbacks, envp, workingDirectory, command, params);
		}
	}

	protected static ExecutionResult executeCommand(
		List<InputLineCallback> callbacks,
		String[] envp, File workingDirectory, String command,
		String ... params
	) throws ExecutionException, InterruptedException {
		params = removeEmptyParams(params);
		final String commandString = commandToString(command, params);

		notifyInfo(callbacks, "Executing command: " + commandString);

		final Runtime runtime = Runtime.getRuntime();

		try {
			inputStarted(callbacks);

			final String[] cmdarray = new String[params.length+1];
			cmdarray[0] = command;
			System.arraycopy(params, 0, cmdarray, 1, params.length);

			final Process process = runtime.exec(cmdarray, envp, workingDirectory);

			final LoggerCallbackInputThread inThread = new LoggerCallbackInputThread(commandString, process.getInputStream(), callbacks);
			final LoggerCallbackErrorThread errThread = new LoggerCallbackErrorThread(commandString, process.getErrorStream(), callbacks);

			inThread.start();
			errThread.start();

			final int outputCode = process.waitFor();

			inThread.join();
			errThread.join();

			return new DefaultExecutionResult(outputCode);
		} catch (IOException e) {
			notifyError(callbacks, "Error executing command: " + commandString, e);

			throw new ExecutionException(-1, e, command);
		} finally {
			inputFinished(callbacks);
		}
	}

	private static class LoggerThread extends Thread {
		private final String command;
		private final BufferedReader reader;
		private final Logger logger;
		private final boolean logOutput;
		private final Marker marker;
		private final StringBuilder sb;

		public LoggerThread(String command, InputStream is, Logger logger, boolean logOutput, Marker marker) {
			this.setDaemon(true);

			this.command = command;
			this.reader = new BufferedReader(new InputStreamReader(is));
			this.logger = logger;
			this.logOutput = logOutput;
			this.marker = marker;
			this.sb = new StringBuilder();
		}

		@Override
		public void run() {
			try {
				this.logger.info(this.marker, "Executing command: " + command);

				String line;
				while ((line = this.reader.readLine()) != null) {
					if (this.logOutput)
						this.logger.info(this.marker, line);
					sb.append(line).append('\n');
				}
			} catch (IOException e) {
				this.logger.error(this.marker, "Error executing command", e);
			}
		}

		public String getText() {
			return this.sb.toString();
		}
	}

	protected static class LoggerCallbackInputThread extends Thread {
		private final String command;
		private final BufferedReader reader;
		private final List<InputLineCallback> callbacks;

		public LoggerCallbackInputThread(String command, InputStream is, List<InputLineCallback> callbacks) {
			this.setDaemon(true);

			this.command = command;
			this.reader = new BufferedReader(new InputStreamReader(is));
			this.callbacks =  callbacks;
		}

		@Override
		public void run() {
			try {
				String line;
				while ((line = this.reader.readLine()) != null) {
					notifyLine(callbacks, line);
				}
			} catch (IOException e) {
				notifyError(callbacks, "Error executing command: " + this.command, e);
			}
		}
	}

	protected static class LoggerCallbackErrorThread extends Thread {
		private final String command;
		private final BufferedReader reader;
		private final List<InputLineCallback> callbacks;

		public LoggerCallbackErrorThread(String command, InputStream is, List<InputLineCallback> callbacks) {
			this.setDaemon(true);

			this.command = command;
			this.reader = new BufferedReader(new InputStreamReader(is));
			this.callbacks =  callbacks;
		}

		@Override
		public void run() {
			try {
				String line;
				while ((line = this.reader.readLine()) != null) {
					notifyError(callbacks, line, null);
				}
			} catch (IOException e) {
				notifyError(callbacks, "Error executing command: " + this.command, e);
			}
		}
	}

	protected static String escapeWhiteSpaces(File file) {
		return file.getAbsolutePath().replace(" ", "\\ ");
	}

	protected static String[] toParamArray(List<String> params) {
		return params.toArray(new String[params.size()]);
	}

	protected static List<String> splitParams(String params) {
		return Arrays.asList(params.split(" "));
	}
}
