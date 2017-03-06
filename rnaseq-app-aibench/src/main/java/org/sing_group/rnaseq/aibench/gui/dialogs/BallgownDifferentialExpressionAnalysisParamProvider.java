package org.sing_group.rnaseq.aibench.gui.dialogs;

import static es.uvigo.ei.aibench.core.CoreUtils.createParams;
import static java.util.Arrays.asList;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;

import org.sing_group.rnaseq.gui.sample.BallgownSamplesEditor;
import org.sing_group.rnaseq.gui.sample.listener.SamplesEditorListener;

import es.uvigo.ei.aibench.core.ParamSpec;
import es.uvigo.ei.aibench.workbench.inputgui.AbstractParamProvider;

public class BallgownDifferentialExpressionAnalysisParamProvider
	extends AbstractParamProvider 
	implements SamplesEditorListener 
{
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
