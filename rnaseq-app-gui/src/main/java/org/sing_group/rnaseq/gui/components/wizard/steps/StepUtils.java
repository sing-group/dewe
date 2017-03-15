package org.sing_group.rnaseq.gui.components.wizard.steps;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class StepUtils {
	public static final Color BACKGROUND_COLOR = Color.WHITE;

	public static void configureStepComponent(JComponent component) {
		component.setBackground(BACKGROUND_COLOR);
		component.setBorder(BorderFactory.createEtchedBorder());
	}
}
