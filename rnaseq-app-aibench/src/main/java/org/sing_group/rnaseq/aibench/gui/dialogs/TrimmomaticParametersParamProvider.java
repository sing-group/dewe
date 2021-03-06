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
package org.sing_group.rnaseq.aibench.gui.dialogs;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;

import org.sing_group.rnaseq.api.environment.execution.parameters.trimmomatic.TrimmomaticParameter;
import org.sing_group.rnaseq.gui.components.configuration.TrimmomaticParametersPanel;

import es.uvigo.ei.aibench.core.CoreUtils;
import es.uvigo.ei.aibench.core.ParamSpec;
import es.uvigo.ei.aibench.workbench.inputgui.AbstractParamProvider;

public class TrimmomaticParametersParamProvider extends AbstractParamProvider {

	private TrimmomaticParametersPanel panel;

	public TrimmomaticParametersParamProvider() {
		this.panel = new TrimmomaticParametersPanel();
		this.panel.addPropertyChangeListener(
			TrimmomaticParametersPanel.PROPERTY_CONFIGURATION,
			new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					setChanged();
					notifyObservers();
				}
			});
	}
	
	@Override
	public JComponent getComponent() {
		return this.panel;
	}

	@Override
	public ParamSpec getParamSpec() throws IllegalArgumentException {
		List<TrimmomaticParameter[]> params = new LinkedList<>();
		params.add(this.panel.getParameters());

		return CoreUtils.createParams(params)[0];
	}

	@Override
	public boolean isValidValue() {
		return this.panel.isValidConfiguration();
	}
}
