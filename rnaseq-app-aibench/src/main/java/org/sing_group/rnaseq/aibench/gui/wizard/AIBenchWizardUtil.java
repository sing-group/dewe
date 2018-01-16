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
package org.sing_group.rnaseq.aibench.gui.wizard;

import java.awt.Dimension;
import java.awt.Window;
import java.io.File;
import java.util.Optional;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.sing_group.rnaseq.gui.util.CommonFileChooser;

import es.uvigo.ei.aibench.workbench.Workbench;

/**
 * A class with utility methods for AIBench wizards.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class AIBenchWizardUtil {

	public static final FileFilter DEWE_EXTENSION =
		new FileNameExtensionFilter("DEWE workflow configuration file", "dewe");

	public static final int IMPORT_INDEX = 0;
	public static final int BUILD_INDEX = 1;

	/**
	 * Asks user to import a genome index or build one. If user cancels, method
	 * returns {@code false}. If user performs an action, method returns
	 * {@code true}.
	 *
	 * @param index an string describing the index type.
	 * @param importOperationUid the AIBench operation uid for the import
	 * operation that must be executed.
	 * @param buildOperationUid the AIBench operation uid for the build
	 * operation that must be executed.
	 * @return {@code true} if user performed an action and {@code false} if
	 * user canceled the dialog.
	 */
	public static boolean askUserImportOrBuild(String index,
		String importOperationUid, String buildOperationUid) {
		int userAction = AIBenchWizardUtil.askUserAction(index);
		if (userAction == IMPORT_INDEX) {
			executeOperation(importOperationUid);
		} else if (userAction == BUILD_INDEX) {
			executeOperation(buildOperationUid);
		} else {
			return false;
		}

		return true;
	}

	private static int askUserAction(String index) {
		Object[] options = { "Import index", "Create index", "Cancel" };
		int n = JOptionPane.showOptionDialog(
			Workbench.getInstance().getMainFrame(),
			"You should create or import a " + index
				+ " index before launching the "
				+ "wizard. What do you want to do?",
			index + " index required", JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

		return n;
	}

	private static void executeOperation(String uid) {
		Workbench.getInstance().executeOperationAndWait(uid);
	}

	/**
	 * Fixes the size of the dialog.
	 *
	 * @param visible whether the dialog is visible or not
	 * @param dialog the dialog whose size must be fixed
	 */
	public static void fixDialogSize(boolean visible, JDialog dialog) {
		if (visible) {
			dialog.setResizable(true);
			int height = (int) (dialog.getSize().getHeight() + 1);
			int width = (int) (dialog.getSize().getWidth() + 1);
			dialog.setSize(new Dimension(width, height));
			dialog.setResizable(false);
		}
	}

	/**
	 * Shows a file chooser to open a file using the specified file filter.
	 *
	 * @param parent the parent component of the file chooser dialog
	 * @param fileFilter the file filter
	 *
	 * @return the selected file, wrapped as an optional which is empty if user
	 * 		   cancels the file selection dialog
	 */
	public static Optional<File> getFile(Window parent,
		FileFilter fileFilter
	) {
		JFileChooser fileChooser = CommonFileChooser.getInstance()
			.getSingleFilechooser();
		fileChooser.resetChoosableFileFilters();
		fileChooser.addChoosableFileFilter(fileFilter);
		fileChooser.setAcceptAllFileFilterUsed(true);

		int option = fileChooser.showOpenDialog(parent);
		fileChooser.removeChoosableFileFilter(fileFilter);

		if (option == JFileChooser.APPROVE_OPTION) {
			return Optional.of(fileChooser.getSelectedFile());
		} else {
			return Optional.empty();
		}
	}
}
