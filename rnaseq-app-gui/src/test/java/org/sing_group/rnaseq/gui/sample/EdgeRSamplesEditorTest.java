package org.sing_group.rnaseq.gui.sample;

import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.setNimbusLookAndFeel;
import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.showComponent;

import javax.swing.JComponent;

public class EdgeRSamplesEditorTest {

	public static void main(String[] args) {
		setNimbusLookAndFeel();
		showComponent(createComponent());
	}

	private static JComponent createComponent() {
		return new EdgeRSamplesEditor();
	}
}
