/*
 * #%L
 * DEWE GUI
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
package org.sing_group.rnaseq.gui.components.wizard.steps;

import java.util.Arrays;
import java.util.List;

import javax.swing.filechooser.FileFilter;

import org.sing_group.gc4s.filechooser.ExtensionFileFilter;
import org.sing_group.gc4s.filechooser.SelectionMode;

/**
 * A {@code FileSelectionStep} that allows the selection of a reference
 * annotation file.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class ReferenceAnnotationFileSelectionStep extends FileSelectionStep {

	@Override
	protected String getSelectionFileLabelText() {
		return "Select a reference annotation file (.gtf): ";
	}

	@Override
	public String getStepTitle() {
		return "Reference annotation file selection";
	}

	@Override
	protected SelectionMode getSelectionMode() {
		return SelectionMode.FILES;
	}

	@Override
	protected List<FileFilter> getFileFilters() {
		return Arrays.asList(new ExtensionFileFilter(".*\\.gtf",
			"Gene transfer format (GTF) files"));
	}

	@Override
	protected boolean isValidFile() {
		return 	super.isValidFile() &&
				getSelectedFile().getName().endsWith(".gtf");
	}

}
