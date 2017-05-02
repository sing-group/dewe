package org.sing_group.rnaseq.gui.components.wizard.steps;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

/**
 * A class that provides common methods and constants for step components.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class StepUtils {
	public static final Color BACKGROUND_COLOR = Color.WHITE;

	/**
	 * Configures the border and background of the given {@code component}.
	 *
	 * @param component the {@code JComponent} to configure
	 */
	public static void configureStepComponent(JComponent component) {
		component.setBackground(BACKGROUND_COLOR);
		component.setBorder(BorderFactory.createEtchedBorder());
	}
}
