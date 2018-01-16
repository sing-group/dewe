/*
 * #%L
 * DEWE
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
package org.sing_group.rnaseq.aibench.gui.dialogs;

import static es.uvigo.ei.aibench.core.CoreUtils.createParams;
import static java.util.Arrays.asList;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;

import org.sing_group.rnaseq.gui.sample.BallgownSamplesEditor;
import org.sing_group.rnaseq.gui.sample.listener.SamplesEditorListener;

import es.uvigo.ei.aibench.core.ParamSpec;
import es.uvigo.ei.aibench.workbench.inputgui.AbstractParamProvider;

/**
 * An {@code AbstractParamProvider} implementation to allow the selection of a
 * list of {@code BallgownSample}s.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class BallgownDifferentialExpressionAnalysisParamProvider
	extends AbstractParamProvider implements SamplesEditorListener {
	private BallgownSamplesEditor ballgownSamplesEditor;

	@Override
	public JComponent getComponent() {
		if (this.ballgownSamplesEditor == null) {
			this.ballgownSamplesEditor = new BallgownSamplesEditor();
			this.ballgownSamplesEditor.addSamplesEditorListener(this);
		}
		return this.ballgownSamplesEditor;
	}

	@Override
	public ParamSpec getParamSpec() throws IllegalArgumentException {
		return createParams(asList(this.ballgownSamplesEditor.getSamples()))[0];
	}

	@Override
	public boolean isValidValue() {
		return 	this.ballgownSamplesEditor.isValidSelection() && 
				!this.ballgownSamplesEditor.getSamples().isEmpty();
	}

	@Override
	public void onSampleEdited(ChangeEvent event) {
		onBallgownSamplesEditorEvent();
	}

	@Override
	public void onSampleAdded(ChangeEvent event) {
		onBallgownSamplesEditorEvent();
	}

	@Override
	public void onSampleRemoved(ChangeEvent event) {
		onBallgownSamplesEditorEvent();
	}

	private void onBallgownSamplesEditorEvent() {
		this.setChanged();
		this.notifyObservers();
	}
}
