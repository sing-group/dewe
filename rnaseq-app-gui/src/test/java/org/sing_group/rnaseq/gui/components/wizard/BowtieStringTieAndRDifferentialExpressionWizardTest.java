package org.sing_group.rnaseq.gui.components.wizard;

import javax.swing.JFrame;

import org.sing_group.rnaseq.gui.util.TestUtils;

import es.uvigo.ei.sing.hlfernandez.demo.DemoUtils;

public class BowtieStringTieAndRDifferentialExpressionWizardTest {

	public static void main(String[] args) {
		DemoUtils.setNimbusLookAndFeel();
		TestUtils.createReferenceGenomeDatabaseManager();

		DemoUtils.showDialog(
			BowtieStringTieAndRDifferentialExpressionWizard.getWizard(
				new JFrame()
			)
		);
	}
}
