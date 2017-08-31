package org.sing_group.rnaseq.aibench.gui.dialogs;

import static es.uvigo.ei.aibench.core.CoreUtils.createParams;
import static java.util.Arrays.asList;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;

import org.sing_group.rnaseq.gui.sample.EdgeRSamplesEditor;
import org.sing_group.rnaseq.gui.sample.listener.SamplesEditorListener;

import es.uvigo.ei.aibench.core.ParamSpec;
import es.uvigo.ei.aibench.workbench.inputgui.AbstractParamProvider;

/**
 * An {@code AbstractParamProvider} implementation to allow the selection of a
 * list of {@code EdgeRSample}s.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class EdgeRDifferentialExpressionAnalysisParamProvider
	extends AbstractParamProvider implements SamplesEditorListener {
	private EdgeRSamplesEditor edgeRSamplesEditor;

	@Override
	public JComponent getComponent() {
		if (this.edgeRSamplesEditor == null) {
			this.edgeRSamplesEditor = new EdgeRSamplesEditor();
			this.edgeRSamplesEditor.addSamplesEditorListener(this);
		}
		return this.edgeRSamplesEditor;
	}

	@Override
	public ParamSpec getParamSpec() throws IllegalArgumentException {
		return createParams(asList(this.edgeRSamplesEditor.getSamples()))[0];
	}

	@Override
	public boolean isValidValue() {
		return 	this.edgeRSamplesEditor.isValidSelection() && 
				!this.edgeRSamplesEditor.getSamples().isEmpty();
	}

	@Override
	public void onSampleEdited(ChangeEvent event) {
		onSamplesEditorEvent();
	}

	@Override
	public void onSampleAdded(ChangeEvent event) {
		onSamplesEditorEvent();
	}

	@Override
	public void onSampleRemoved(ChangeEvent event) {
		onSamplesEditorEvent();
	}

	private void onSamplesEditorEvent() {
		this.setChanged();
		this.notifyObservers();
	}
}
