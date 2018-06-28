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
package org.sing_group.rnaseq.gui.components.parameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;

import org.sing_group.gc4s.event.DocumentAdapter;
import org.sing_group.gc4s.input.InputParameter;
import org.sing_group.gc4s.input.InputParametersPanel;
import org.sing_group.gc4s.ui.CenteredJPanel;
import org.sing_group.rnaseq.gui.components.parameters.event.CommandLineApplicationsParametersListener;
import org.sing_group.rnaseq.gui.util.UISettings;

/**
 * A panel to allow users introducing command-line parameters.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see CommandLineParameter
 */
public class CommandLineParametersPanel extends CenteredJPanel {
	private static final long serialVersionUID = 1L;

	private List<CommandLineParameter> parameters;
	private Map<CommandLineParameter, InputParameter> parametersMap;
	private InputParametersPanel parametersPanel;

	/**
	 * Creates a new {@code CommandLineParametersPanel} for the specified 
	 * parameters.
	 * 
	 * @param parameters a list of {@code CommandLineParameter}
	 */
	public CommandLineParametersPanel(List<CommandLineParameter> parameters) {
		this.parameters = parameters;
		this.init();
	}

	private void init() {
		this.createInputParameters();
		this.parametersPanel = new InputParametersPanel(getParametersArray());
		this.add(this.parametersPanel);
	}

	private void createInputParameters() {
		this.parametersMap = new HashMap<>();
		this.parameters.forEach(p -> {
			this.parametersMap.put(p, getInputParameter(p));
		});
	}

	private InputParameter[] getParametersArray() {
		return this.parameters.stream()
			.map(this.parametersMap::get).collect(Collectors.toList())
			.toArray(new InputParameter[this.parameters.size()]);
	}

	private InputParameter getInputParameter(CommandLineParameter param) {
		JTextField tf = new JTextField(param.getValue(), 20);
		tf.getDocument().addDocumentListener(new DocumentAdapter() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				SwingUtilities.invokeLater(() -> validate());
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				SwingUtilities.invokeLater(() -> validate());
			}

			private void validate() {
				if (param.validate(tf.getText())) {
					tf.setBackground(null);
				} else {
					tf.setBackground(UISettings.COLOR_ERROR);
				}
				notifyParameterChanged(param);
			}
		});

		return new InputParameter(param.getName(), tf, param.getDescripion());
	}

	private void notifyParameterChanged(CommandLineParameter param) {
		for(CommandLineApplicationsParametersListener l : 
			getListeners(CommandLineApplicationsParametersListener.class)
		) {
			l.parameterValueChanged(param);
		}
	}

	/**
	 * Returns {@code true} if all parameters are valid and {@code false}
	 * otherwise.
	 * 
	 * @return {@code true} if all parameters are valid and {@code false}
	 * 		   otherwise.
	 */
	public boolean areAllParametersValid() {
		for (CommandLineParameter parameter : this.parameters) {
			if (!parameter.validate(getParameterValue(parameter).get())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns the value of the specified {@code CommandLineParameter}.
	 *   
	 * @param parameter a {@code CommandLineParameter} that should have been
	 *        used to construct the panel
	 * @return the value of the specified parameter
	 */
	public Optional<String> getParameterValue(CommandLineParameter parameter) {
		return Optional.ofNullable(
			((JTextField) parametersMap.get(parameter).getInput()).getText());
	}

	/**
	 * Sets the value for the specified parameter.
	 * 
	 * @param parameter a {@code CommandLineParameter} that should have been
	 *        used to construct the panel.
	 * @param value the new parameter value
	 */
	public void setParameterValue(CommandLineParameter parameter,
		String value
	) {
		if (this.parametersMap.containsKey(parameter)) {
			((JTextField) parametersMap.get(parameter).getInput())
				.setText(value);
		}
	}

	/**
	 * Adds the specified {@code CommandLineApplicationsParametersListener} for
	 * receiving events from this component.
	 * 
	 * @param l a {@code CommandLineApplicationsParametersListener}.
	 */
	public void addCommandLineParametersPanelListener(
		CommandLineApplicationsParametersListener l
	) {
		this.listenerList.add(CommandLineApplicationsParametersListener.class, l);
	}
}
