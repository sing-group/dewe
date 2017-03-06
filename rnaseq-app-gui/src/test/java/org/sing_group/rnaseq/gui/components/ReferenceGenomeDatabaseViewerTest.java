package org.sing_group.rnaseq.gui.components;

import static org.sing_group.rnaseq.gui.util.TestUtils.createReferenceGenomeDatabaseManager;
import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.showComponent;

import javax.swing.JComponent;


public class ReferenceGenomeDatabaseViewerTest {

	public static void main(String[] args) {
		showComponent(createComponent());
	}

	private static JComponent createComponent() {
		ReferenceGenomeDatabaseViewer component = new ReferenceGenomeDatabaseViewer();
		component.setReferenceGenomeDatabaseManager(
			createReferenceGenomeDatabaseManager()
		);
		return component;
	}
}
