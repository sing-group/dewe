package org.sing_group.rnaseq.gui.ballgown;

import java.awt.Window;

import javax.swing.JPanel;

import org.sing_group.gc4s.dialog.AbstractInputJDialog;
import org.sing_group.gc4s.input.InputParameter;
import org.sing_group.gc4s.input.InputParametersPanel;
import org.sing_group.gc4s.text.DoubleTextField;

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
