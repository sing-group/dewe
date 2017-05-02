package org.sing_group.rnaseq.aibench.gui.dialogs;

import java.io.File;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.inputgui.ClipboardParamProvider;
import es.uvigo.ei.aibench.workbench.inputgui.FileParamProvider;
import es.uvigo.ei.aibench.workbench.inputgui.ParamProvider;
import es.uvigo.ei.aibench.workbench.inputgui.ParamsWindow;

/**
 * An extension of {@code ParamsWindow} to use in operations that take as input
 * a genome file and a genome name and you want to automatically set the genome
 * name when the genome file is selected.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class ReferenceGenomeOperationParamsWindow extends ParamsWindow
	implements DocumentListener {
	private static final long serialVersionUID = 1L;
	public static final String GENOME = "Genome";
	public static final String NAME = "Name";

	private JTextField genomeFileTextField;
	private ClipboardParamProvider nameParamProvider;

	protected ParamProvider getParamProvider(final Port arg0,
		final Class<?> arg1, final Object arg2
	) {
		ParamProvider p = super.getParamProvider(arg0, arg1, arg2);

		if (arg0.name().equals(GENOME)) {
			FileParamProvider f = (FileParamProvider) p;
			genomeFileTextField = f.getField();
			genomeFileTextField.getDocument().addDocumentListener(this);
		} else if (arg0.name().equals(NAME)) {
			nameParamProvider = (ClipboardParamProvider) p;
		}

		return p;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		genomeFileChanged();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		genomeFileChanged();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		genomeFileChanged();
	}

	protected void genomeFileChanged() {
		if (nameParamProvider.getCurrentString().isEmpty()) {
			nameParamProvider.setCurrentString(getGenomeName());
		}
	}

	protected String getGenomeName() {
		return getSelectedFileName() + " index";
	}

	private String getSelectedFileName() {
		return new File(genomeFileTextField.getText()).getName();
	}
}
