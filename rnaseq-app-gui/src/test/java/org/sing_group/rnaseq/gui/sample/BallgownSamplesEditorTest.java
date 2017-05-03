package org.sing_group.rnaseq.gui.sample;

import static org.sing_group.gc4s.demo.DemoUtils.setNimbusLookAndFeel;
import static org.sing_group.gc4s.demo.DemoUtils.showComponent;

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
