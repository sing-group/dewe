/*
 * #%L
 * DEWE
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
package org.sing_group.rnaseq.aibench.operations.util;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import es.uvigo.ei.aibench.workbench.Workbench;

/**
 * An {@code AbstractAction} that opens an AIBench operation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class LaunchOperationAbstractAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private String uid;

	public LaunchOperationAbstractAction(String name, String uid) {
		super(name);
		this.uid = uid;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Workbench.getInstance().executeOperation(this.uid);
	}
}
