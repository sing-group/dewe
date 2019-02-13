/*
 * #%L
 * DEWE GUI
 * %%
 * Copyright (C) 2016 - 2019 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
