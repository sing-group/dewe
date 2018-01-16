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
package org.sing_group.rnaseq.gui.ballgown.results;

import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.io.InvalidClassException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;

import org.sing_group.gc4s.dialog.AbstractInputJDialog;
import org.sing_group.gc4s.event.ListDataAdapter;
import org.sing_group.gc4s.input.InputParameter;
import org.sing_group.gc4s.input.InputParametersPanel;
import org.sing_group.gc4s.input.list.ExtendedDefaultListModel;
import org.sing_group.gc4s.input.list.JParallelListsPanel;
import org.sing_group.rnaseq.api.environment.execution.parameters.ImageConfigurationParameter;
import org.sing_group.rnaseq.gui.components.configuration.ImageConfigurationPanel;

/**
 * A dialog that allows users to configure the figure of the structure and
 * expression levels of a given transcript in a sample.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class ExpressionLevelsFigureConfigurationDialog
	extends AbstractInputJDialog {
	private static final long serialVersionUID = 1L;

	private List<String> samples;
	private InputParameter[] parameters;
	private ExtendedDefaultListModel<String> unselectedListModel;
	private ExtendedDefaultListModel<String> selectedListModel;
	private JList<String> unselectedList;
	private ImageConfigurationPanel imageConfigurationPanel;

	protected ExpressionLevelsFigureConfigurationDialog(Window parent,
		List<String> samples
	) {
		super(parent);
		this.samples = samples;
		this.init();
	}

	private void init() {
		this.unselectedListModel.addElements(samples);
	}

	@Override
	protected String getDialogTitle() {
		return "Figure configuration";
	}

	@Override
	protected String getDescription() {
		return "This dialog allows you to configure the figure settings and "
			+ "select for which samples the figure must be created.";
	}

	@Override
	protected JPanel getInputComponentsPane() {
		return getImageConfigurationPanel();
	}

	private JPanel getImageConfigurationPanel() {
		return new InputParametersPanel(getInputParameters());
	}

	private InputParameter[] getInputParameters() {
		parameters = new InputParameter[2];
		parameters[0] = getSamplesImputParameter();
		parameters[1] = getImageConfigurationInputParameter();

		return parameters;
	}

	private InputParameter getSamplesImputParameter() {
		return new InputParameter(
			"Samples", getSamplesInputComponent(),
			"The samples for which you want to create the figure."
		);
	}

	private JComponent getSamplesInputComponent() {
		unselectedListModel = new ExtendedDefaultListModel<String>();
		unselectedList = new JList<>(unselectedListModel);
		unselectedList.setFixedCellWidth(200);

		selectedListModel = new ExtendedDefaultListModel<String>();
		JList<String> selectedList = new JList<>(selectedListModel);
		selectedList.setFixedCellWidth(200);
		selectedListModel.addListDataListener(new ListDataAdapter() {
			@Override
			public void intervalAdded(ListDataEvent e) {
				checkOkButton();
			}

			@Override
			public void intervalRemoved(ListDataEvent e) {
				checkOkButton();
			}
		});

		try {
			JParallelListsPanel<String> parallelLists =
				new JParallelListsPanel<String>(
					unselectedList, selectedList, "Unselected samples",
					"Selected samples", false, false
				);
			return parallelLists;
		} catch (InvalidClassException e) {
			throw new RuntimeException(e);
		}
	}

	private InputParameter getImageConfigurationInputParameter() {
		return new InputParameter(
			"Image configuration",
			getImageParameterConfigurationPanel(),
			"The image configuration"
		);
	}

	private JPanel getImageParameterConfigurationPanel() {
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
		okButton.setEnabled(
			this.imageConfigurationPanel.isValidImageConfiguration() &&
			this.selectedListModel.getSize() > 0
		);
	}

	/**
	 * Returns the selected {@code ImageConfigurationParameter}.
	 *
	 * @return the selected {@code ImageConfigurationParameter}
	 */
	public ImageConfigurationParameter getImageConfiguration() {
		return this.imageConfigurationPanel.getImageConfiguration();
	}

	/**
	 * Returns the names of the selected samples for which the image should be
	 * generated.
	 *
	 * @return the list of selected samples
	 */
	public List<String> getSelectedSamples() {
		return extractElements(selectedListModel);
	}

	private List<String> extractElements(DefaultListModel<String> listModel) {
		Enumeration<String> iterator = listModel.elements();
		List<String> toret = new LinkedList<>();
		while (iterator.hasMoreElements()) {
			toret.add(iterator.nextElement());
		}
		return toret;
	}

	@Override
	public void setVisible(boolean b) {
		checkOkButton();
		unselectedList.updateUI();
		this.pack();
		super.setVisible(b);
	}
}
