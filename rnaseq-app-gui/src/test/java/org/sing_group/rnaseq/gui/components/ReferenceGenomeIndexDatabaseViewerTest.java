package org.sing_group.rnaseq.gui.components;

import static org.sing_group.rnaseq.gui.util.TestUtils.createReferenceGenomeDatabaseManager;
import static org.sing_group.gc4s.demo.DemoUtils.showComponent;

import javax.swing.JComponent;


public class ReferenceGenomeIndexDatabaseViewerTest {

	public static void main(String[] args) {
		showComponent(createComponent());
	}

	private static JComponent createComponent() {
		ReferenceGenomeIndexDatabaseViewer component = 
			new ReferenceGenomeIndexDatabaseViewer();
		component.setReferenceGenomeDatabaseManager(
			createReferenceGenomeDatabaseManager()
		);
		return component;
	}
}
