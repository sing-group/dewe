/*
 * #%L
 * DEWE GUI
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
package org.sing_group.rnaseq.gui.ballgown;

import java.awt.Window;

import javax.swing.JPanel;

import org.sing_group.gc4s.dialog.AbstractInputJDialog;
import org.sing_group.gc4s.input.InputParameter;
import org.sing_group.gc4s.input.InputParametersPanel;
import org.sing_group.gc4s.input.text.DoubleTextField;

/**
 * A dialog that allows the user to configure the settings to export a genes
 * or transcripts table.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public abstract class ExportTableDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;
	private DoubleTextField pValueTf;

	public ExportTableDialog(Window parent) {
		super(parent);
	}

	@Override
	protected String getDialogTitle() {
		return "Export table";
	}

	@Override
	protected JPanel getInputComponentsPane() {
		return new InputParametersPanel(getInputParameters());
	}

	private InputParameter[] getInputParameters() {
		InputParameter[] parameters = new InputParameter[1];
		parameters[0] = getPValueParameter();
		return parameters;
	}

	private InputParameter getPValueParameter() {
		this.pValueTf = new DoubleTextField(0.05d);
		return new InputParameter(
			"Maximum p-value", pValueTf, "The maximum p-value allowed.");
	}

	/**
	 * Returns the pValue selected by the user.
	 *
	 * @return the pValue selected by the user.
	 */
	public double getPvalue() {
		return this.pValueTf.getValue();
	}

	@Override
	public void setVisible(boolean b) {
		this.pack();
		this.okButton.setEnabled(true);
		super.setVisible(b);
	}
}
