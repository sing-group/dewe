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
package org.sing_group.rnaseq.gui.components.configuration;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;

import org.sing_group.gc4s.event.DocumentAdapter;
import org.sing_group.gc4s.input.InputParameter;
import org.sing_group.gc4s.input.InputParametersPanel;
import org.sing_group.gc4s.input.RadioButtonsPanel;
import org.sing_group.gc4s.input.text.JIntegerTextField;
import org.sing_group.rnaseq.api.environment.execution.parameters.VennDiagramConfigurationParameter;
import org.sing_group.rnaseq.api.environment.execution.parameters.VennDiagramConfigurationParameter.Format;
import org.sing_group.rnaseq.core.environment.execution.parameters.DefaultVennDiagramConfigurationParameter;

/**
 * A component that allows configuring a {@code VennDiagramConfigurationParameter}.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class VennDiagramConfigurationPanel extends JPanel implements ItemListener {
	private static final long serialVersionUID = 1L;
	public static final String PROPERTY_CONFIGURATION = "prop.imageconfiguration";

	private static final int DEFAULT_WIDTH = 3000;
	private static final int DEFAULT_HEIGHT = 3000;

	private RadioButtonsPanel<VennDiagramConfigurationParameter.Format> imageFormatPanel;
	private JIntegerTextField widthTF;
	private JIntegerTextField heightTF;
	private JCheckBox imageColor;

	private DocumentAdapter documentAdapter = new DocumentAdapter() {
		@Override
		public void insertUpdate(DocumentEvent e) {
			dimensionsChanged();
		};

		@Override
		public void removeUpdate(DocumentEvent e) {
			dimensionsChanged();
		};
	};

	/**
	 * Creates a new {@code VennDiagramConfigurationPanel}.
	 */
	public VennDiagramConfigurationPanel() {
		super(new BorderLayout());
		this.init();
	}

	private void init() {
		this.add(new InputParametersPanel(getParameters()));
	}

	private InputParameter[] getParameters() {
		InputParameter[] parameters = new InputParameter[4];
		parameters[0] = getImageFormatParameter();
		parameters[1] = getImageWidthParameter();
		parameters[2] = getImageHeightParameter();
		parameters[3] = getImageColorParameter();
		return parameters;
	}

	private InputParameter getImageFormatParameter() {
		return new InputParameter(
			"Format: ", getImageFormatPanel(), "The format of the image."
		);
	}

	private JComponent getImageFormatPanel() {
		Format[] formats = VennDiagramConfigurationParameter.Format.values();
		imageFormatPanel = new RadioButtonsPanel<>(formats, 1, formats.length);
		imageFormatPanel.addItemListener(this);
		return imageFormatPanel;
	}

	private InputParameter getImageWidthParameter() {
		return new InputParameter(
			"Width: ", getImageWidthComponent(), "The width of the image.");
	}

	private JComponent getImageWidthComponent() {
		this.widthTF = new JIntegerTextField(DEFAULT_WIDTH);
		this.widthTF.getDocument().addDocumentListener(documentAdapter );
		return this.widthTF;
	}

	private InputParameter getImageHeightParameter() {
		return new InputParameter(
			"Height: ", getImageHeightComponent(), "The height of the image.");
	}

	private JComponent getImageHeightComponent() {
		this.heightTF = new JIntegerTextField(DEFAULT_HEIGHT);
		this.heightTF.getDocument().addDocumentListener(documentAdapter);
		return this.heightTF;
	}


	private InputParameter getImageColorParameter() {
		return new InputParameter(
			"Color: ", getImageColorPanel(),
			"Whether the image should be in color or not."
		);
	}

	private JComponent getImageColorPanel() {
		imageColor = new JCheckBox();
		imageColor.setSelected(false);
		imageColor.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				notifyImageConfigurationPropertyChanged();
			}
		});
		return imageColor;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			notifyImageConfigurationPropertyChanged();
		}
	}

	private void dimensionsChanged() {
		notifyImageConfigurationPropertyChanged();
	}

	private void notifyImageConfigurationPropertyChanged() {
		PropertyChangeEvent event =
			new PropertyChangeEvent(this, PROPERTY_CONFIGURATION, null, null);
		for (PropertyChangeListener p :
			getPropertyChangeListeners(PROPERTY_CONFIGURATION)
		) {
			p.propertyChange(event);
		}
	}

	/**
	 * Returns {@code true} if the introduced configuration is valid and
	 * {@code false} otherwise.
	 *
	 * @return {@code true} if the introduced configuration is valid and
	 *         {@code false} otherwise
	 */
	public boolean isValidImageConfiguration() {
		return this.imageFormatPanel.getSelectedItem().isPresent()
			&& this.widthTF.getValue() > 0
			&& this.heightTF.getValue() > 0;
	}

	/**
	 * Returns the configured {@code VennDiagramConfigurationParameter}.
	 *
	 * @return the configured {@code VennDiagramConfigurationParameter}
	 */
	public VennDiagramConfigurationParameter getImageConfiguration() {
		return new DefaultVennDiagramConfigurationParameter(
			this.imageFormatPanel.getSelectedItem().get(),
			this.widthTF.getValue(), this.heightTF.getValue(),
			this.imageColor.isSelected()
		);
	}
}
