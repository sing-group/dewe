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
package org.sing_group.rnaseq.aibench.operations;

import javax.swing.SwingUtilities;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.core.controller.DefaultAppController;

import es.uvigo.ei.aibench.core.Core;
import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
		name = "IGV browser",
		description = "Visualisation of RNA-seq signal using IGV."
	)
public class IgvBrowser {

	@Port(
		direction = Direction.OUTPUT,
		order = 1
	)
	public void runOperation() {
		SwingUtilities.invokeLater(() -> {
			new Thread(() -> {
				try {
					Core.getInstance().disableOperation("operations.igvbrowser");
					DefaultAppController appController = DefaultAppController.getInstance();
					appController.getIgvBrowserController().igvBrowser();
					Core.getInstance().enableOperation("operations.igvbrowser");
				} catch (ExecutionException e) {
					Workbench.getInstance().error(e, e.getMessage());
				} catch (InterruptedException e) {
					Workbench.getInstance().error(e, e.getMessage());
				}
			}).start();
		});
	}
}
