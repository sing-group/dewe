/*
 * #%L
 * DEWE GUI
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
package org.sing_group.rnaseq.gui.ballgown;

import java.awt.Window;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;

import org.sing_group.gc4s.dialog.AbstractInputJDialog;
import org.sing_group.gc4s.event.DocumentAdapter;
import org.sing_group.gc4s.input.InputParameter;
import org.sing_group.gc4s.input.InputParametersPanel;
import org.sing_group.gc4s.input.RadioButtonsPanel;
import org.sing_group.gc4s.input.filechooser.JFileChooserPanel;
import org.sing_group.gc4s.input.filechooser.JFileChooserPanelBuilder;
import org.sing_group.gc4s.input.filechooser.event.FileChooserListener;
import org.sing_group.gc4s.input.text.JIntegerTextField;
import org.sing_group.rnaseq.core.operations.ballgown.BallgownGenesOperations;
import org.sing_group.rnaseq.gui.util.CommonFileChooser;

/**
 * A dialog that allows the user to configure the settings to export a list of
 * gene names.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class ExportGeneNamesDialog extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;

	private JFileChooserPanel outputFile;
	private RadioButtonsPanel<BallgownGenesOperations.GenesType> genesType;
	private JIntegerTextField numberOfGenes;

	protected ExportGeneNamesDialog(Window parent) {
		super(parent);
	}

	@Override
	protected String getDialogTitle() {
		return "Export genes";
	}

	@Override
	protected String getDescription() {
		return "This dialog allows you to export the most overexpressed or"
			+ " underexpressed genes.";
	}

	@Override
	protected JPanel getInputComponentsPane() {
		return new InputParametersPanel(getInputParameters());
	}

	private InputParameter[] getInputParameters() {
		InputParameter[] parameters = new InputParameter[3];
		parameters[0] = getOutputFileParameter();
		parameters[1] = getGenesParameter();
		parameters[2] = getNumberOfGenesParameter();
		return parameters;
	}

	private InputParameter getOutputFileParameter() {
		outputFile = JFileChooserPanelBuilder
			.createSaveJFileChooserPanel()
			.withFileChooser(CommonFileChooser.getInstance().getSingleFilechooser())
			.withLabel("")
			.withRequiredFileSaveExtension("txt")
			.build();
		outputFile.addFileChooserListener(new FileChooserListener() {

			@Override
			public void onFileChoosed(ChangeEvent event) {
				checkOkButton();
			}
		});

		return new InputParameter("Output file", outputFile, "The output file");
	}

	private InputParameter getGenesParameter() {
		genesType = new RadioButtonsPanel<>(
			BallgownGenesOperations.GenesType.values(), 1, 2);
		return new InputParameter("Type of genes", genesType,
			"The type genes that you want.");
	}

	private InputParameter getNumberOfGenesParameter() {
		numberOfGenes = new JIntegerTextField(10);
		numberOfGenes.getDocument().addDocumentListener(
			new DocumentAdapter() {

				@Override
				public void removeUpdate(DocumentEvent e) {
					checkOkButton();
				}

				@Override
				public void insertUpdate(DocumentEvent e) {
					checkOkButton();
				}
			});
		return new InputParameter("Number of genes", numberOfGenes,
			"The number of genes that you want.");
	}

	@Override
	public void setVisible(boolean b) {
		this.pack();
		super.setVisible(b);
	}

	private void checkOkButton() {
		this.okButton.setEnabled(isValidConfiguration());
	}

	private boolean isValidConfiguration() {
		return isValidSelectedFile() && isValidNumberOfGenes();
	}

	private boolean isValidSelectedFile() {
		return this.outputFile.getSelectedFile() != null;
	}

	private boolean isValidNumberOfGenes() {
		return this.numberOfGenes.getValue() > 0;
	}

	/**
	 * Returns the output file selected by the user.
	 *
	 * @return the output file selected by the user
	 */
	public File getOutputFile() {
		return this.outputFile.getSelectedFile();
	}

	/**
	 * Returns the criteria for gene sorting.
	 *
	 * @return the criteria for gene sorting
	 */
	public BallgownGenesOperations.GenesType getGenesType() {
		return this.genesType.getSelectedItem().get();
	}

	/**
	 * Returns the number of genes selected by the user.
	 *
	 * @return the number of genes selected by the user
	 */
	public int getGenesCount() {
		return this.numberOfGenes.getValue();
	}
}
