package org.sing_group.rnaseq.gui.ballgown;

import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.setNimbusLookAndFeel;
import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.showComponent;

import javax.swing.JComponent;

public class BallgownSamplesEditorTest {

	public static void main(String[] args) {
		setNimbusLookAndFeel();
		showComponent(createComponent());
	}

	private static JComponent createComponent() {
		return new BallgownSamplesEditor();
	}
}
