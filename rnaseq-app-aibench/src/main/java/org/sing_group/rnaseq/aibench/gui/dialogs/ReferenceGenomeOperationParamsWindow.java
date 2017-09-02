/*
 * #%L
 * DEWE
 * %%
 * Copyright (C) 2016 - 2017 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
