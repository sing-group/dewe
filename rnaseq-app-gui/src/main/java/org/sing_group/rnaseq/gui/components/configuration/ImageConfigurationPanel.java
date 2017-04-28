package org.sing_group.rnaseq.gui.components.configuration;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;

import org.sing_group.rnaseq.api.environment.execution.parameters.ImageConfigurationParameter;
import org.sing_group.rnaseq.api.environment.execution.parameters.ImageConfigurationParameter.Format;
import org.sing_group.rnaseq.core.environment.execution.parameters.DefaultImageConfigurationParameter;

import es.uvigo.ei.sing.hlfernandez.event.DocumentAdapter;
import es.uvigo.ei.sing.hlfernandez.input.InputParameter;
import es.uvigo.ei.sing.hlfernandez.input.InputParametersPanel;
import es.uvigo.ei.sing.hlfernandez.input.RadioButtonsPanel;
import es.uvigo.ei.sing.hlfernandez.text.JIntegerTextField;

/**
 * A component that allows configuring a {@code ImageConfigurationParameter}.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class ImageConfigurationPanel extends JPanel implements ItemListener {
	private static final long serialVersionUID = 1L;
	public static final String PROPERTY_CONFIGURATION = "prop.imageconfiguration";

	private static final int DEFAULT_WIDTH = 1000;
	private static final int DEFAULT_HEIGHT = 1000;

	private RadioButtonsPanel<ImageConfigurationParameter.Format> imageFormatPanel;
	private JIntegerTextField widthTF;
	private JIntegerTextField heightTF;

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
	 * Creates a new {@code ImageConfigurationPanel}.
	 */
	public ImageConfigurationPanel() {
		super(new BorderLayout());
		this.init();
	}

	private void init() {
		this.add(new InputParametersPanel(getParameters()));
	}

	private InputParameter[] getParameters() {
		InputParameter[] parameters = new InputParameter[3];
		parameters[0] = getImageFormatParameter();
		parameters[1] = getImageWidthParameter();
		parameters[2] = getImageHeightParameter();
		return parameters;
	}

	private InputParameter getImageFormatParameter() {
		return new InputParameter(
			"Format: ", getImageFormatPanel(), "The format of the image."
		);
	}

	private JComponent getImageFormatPanel() {
		Format[] formats = ImageConfigurationParameter.Format.values();
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
		return this.imageFormatPanel.getSelectedItem().isPresent();
	}

	/**
	 * Returns the configured {@code ImageConfigurationParameter}.
	 *
	 * @return the configured {@code ImageConfigurationParameter}
	 */
	public ImageConfigurationParameter getImageConfiguration() {
		return new DefaultImageConfigurationParameter(
			this.imageFormatPanel.getSelectedItem().get(),
			this.widthTF.getValue(), this.heightTF.getValue()
		);
	}
}
