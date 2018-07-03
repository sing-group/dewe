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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.sing_group.gc4s.ui.icons.Icons;
import org.sing_group.rnaseq.gui.components.configuration.FigureConfigurationDialog;

/**
 * An extension of the {@code FigureConfigurationDialog} that also allows
 * users to set the number of clusters.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class HeatmapFigureConfigurationDialog extends FigureConfigurationDialog {
	private static final long serialVersionUID = 1L;

	private JCheckBox clustersCheckBox;
	private JSpinner clustersSpinner;

	public HeatmapFigureConfigurationDialog(Window parent) {
		super(parent);
	}

	@Override
	protected JPanel getInputComponentsPane() {
		JPanel superPanel = super.getInputComponentsPane();
		JPanel toret = new JPanel(new BorderLayout());
		toret.add(superPanel, BorderLayout.CENTER);
		toret.add(getClusterNumberSelectionPanel(), BorderLayout.SOUTH);
		return toret;
	}

	private Component getClusterNumberSelectionPanel() {
		clustersCheckBox = new JCheckBox("Clusters?", false);
		clustersCheckBox.addItemListener((i) -> {
			clustersSpinner.setEnabled(clustersCheckBox.isSelected());
		});
		clustersSpinner = new JSpinner(
			new SpinnerNumberModel(2, 2, Integer.MAX_VALUE, 1));
		clustersSpinner.setEnabled(false);
		JLabel infoLabel = new JLabel(Icons.ICON_INFO_2_16);
		infoLabel.setToolTipText("Whether to use a custom number of clusters "
			+ "to generate the heatmap or not.");

		JPanel panel = new JPanel(new GridLayout(1, 3));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		panel.add(clustersCheckBox);
		panel.add(clustersSpinner);
		panel.add(infoLabel);

		return panel;
	}

	public int getClustersNumber() {
		return this.clustersCheckBox.isSelected()
			? (Integer) clustersSpinner.getValue() : 1;
	}
}
