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
package org.sing_group.rnaseq.gui.ballgown;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;

import org.jdesktop.swingx.renderer.DefaultTableRenderer;

/**
 * A {@code DefaultTableRenderer} to render double values representing
 * probabilities. Values under a specified threshold are highlighted with a bold
 * font style.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class ProbabilityValueCellRenderer extends DefaultTableRenderer {
	private static final long serialVersionUID = 1L;
	public static final double BOLD_THRESHOLD = 0.05;

	private double boldThreshold;

	/**
	 * Creates a new {@code ProbabilityValueCellRenderer} with the default
	 * threshold as {@link ProbabilityValueCellRenderer#BOLD_THRESHOLD}.
	 */
	public ProbabilityValueCellRenderer() {
		this(BOLD_THRESHOLD);
	}

	/**
	 * Creates a new {@code ProbabilityValueCellRenderer} with the specified
	 * threshold for highlighting values less than it.
	 *
	 * @param boldThreshold the threshold for highlighting values less than it
	 */
	public ProbabilityValueCellRenderer(double boldThreshold) {
		this.boldThreshold = boldThreshold;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table,
		Object value, boolean isSelected, boolean hasFocus, int row,
		int column
	) {
		final Component c = super.getTableCellRendererComponent(
			table, value, isSelected, hasFocus, row, column
		);

		if(c instanceof JLabel) {
			JLabel label = (JLabel) c;
			String cellValue = Double.isNaN((double) value) ?
					"" : String.format("%1.4e", value);
			if(!Double.isNaN((double) value)) {
				label.setToolTipText(cellValue);
			}
			label.setText(cellValue);
			if((double) value < boldThreshold) {
				label.setFont(label.getFont().deriveFont(Font.BOLD));
			}
		}

		return c;
	}
}