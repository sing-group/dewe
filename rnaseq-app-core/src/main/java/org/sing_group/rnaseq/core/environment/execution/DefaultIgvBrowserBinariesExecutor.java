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

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.IgvBrowserBinaries;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;
import org.sing_group.rnaseq.api.environment.execution.IgvBrowserBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default {@code IgvBrowserBinariesExecutor} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultIgvBrowserBinariesExecutor
	extends AbstractBinariesExecutor<IgvBrowserBinaries>
	implements IgvBrowserBinariesExecutor {
	private final static Logger LOG = LoggerFactory.getLogger(DefaultIgvBrowserBinariesExecutor.class);

	/**
	 * Creates a new {@code DefaultIgvBrowserBinariesExecutor} instance to execute
	 * the specified {@code IgvBrowserBinaries}.
	 *
	 * @param binaries the {@code IgvBrowserBinaries} to execute
	 * @throws BinaryCheckException if any of the commands can't be executed
	 */
	public DefaultIgvBrowserBinariesExecutor(IgvBrowserBinaries binaries)
		throws BinaryCheckException {
		this.setBinaries(binaries);
	}

	@Override
	public void setBinaries(IgvBrowserBinaries binaries)
		throws BinaryCheckException {
		super.setBinaries(binaries);
		this.checkBinaries();
	}

	@Override
	public void checkBinaries() throws BinaryCheckException {
		if(!new File(this.binaries.getIgvBrowser()).exists()) {
			throw new BinaryCheckException("Binary " + this.binaries.getIgvBrowser() + " does not exist.");
		}
	}

	@Override
	public ExecutionResult igvBrowser()
		throws ExecutionException, InterruptedException {
			return executeCommand(
				LOG,
				false,
				this.binaries.getIgvBrowser()
			);
	}
}
