package org.sing_group.rnaseq.gui.ballgown.results;

import java.awt.Window;
import java.beans.PropertyChangeEvent;

import javax.swing.JPanel;

import org.sing_group.rnaseq.api.environment.execution.parameters.ImageConfigurationParameter;
import org.sing_group.rnaseq.gui.components.configuration.ImageConfigurationPanel;

import org.sing_group.gc4s.dialog.AbstractInputJDialog;

/**
 * A dialog that allows users to configure the FKPM transcripts figure.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class FkpmTranscriptsFigureConfigurationDialog
	extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;

	private ImageConfigurationPanel imageConfigurationPanel;

	protected FkpmTranscriptsFigureConfigurationDialog(Window parent) {
		super(parent);
	}

	@Override
	protected String getDialogTitle() {
		return "Figure configuration";
	}

	@Override
	protected String getDescription() {
		return "This dialog allows you to configure the figure settings.";
	}

	@Override
	protected JPanel getInputComponentsPane() {
		return getImageConfigurationPanel();
	}

	private JPanel getImageConfigurationPanel() {
		this.imageConfigurationPanel = new ImageConfigurationPanel();
		this.imageConfigurationPanel.addPropertyChangeListener(
			ImageConfigurationPanel.PROPERTY_CONFIGURATION,
			this::imageConfigurationChanged);

		return this.imageConfigurationPanel;
	}

	protected void imageConfigurationChanged(PropertyChangeEvent evt) {
		checkOkButton();
	}

	private void checkOkButton() {
		okButton.setEnabled(isValidImageConfiguration());
	}

	private boolean isValidImageConfiguration() {
		return this.imageConfigurationPanel.isValidImageConfiguration();
	}

	/**
	 * Returns the selected {@code ImageConfigurationParameter}.
	 *
	 * @return the selected {@code ImageConfigurationParameter}
	 */
	public ImageConfigurationParameter getImageConfiguration() {
		return imageConfigurationPanel.getImageConfiguration();
	}

	@Override
	public void setVisible(boolean b) {
		checkOkButton();
		this.pack();
		super.setVisible(b);
	}
}
