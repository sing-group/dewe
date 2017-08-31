package org.sing_group.rnaseq.aibench.gui.wizard;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import es.uvigo.ei.aibench.workbench.Workbench;

/**
 * A class with utility methods for AIBench wizards.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class AIBenchWizardUtil {

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
}
