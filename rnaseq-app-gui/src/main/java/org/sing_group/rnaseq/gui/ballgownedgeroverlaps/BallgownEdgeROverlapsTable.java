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
package org.sing_group.rnaseq.gui.ballgownedgeroverlaps;

import org.jdesktop.swingx.JXTable;
import org.sing_group.rnaseq.api.entities.ballgownedgeroverlaps.BallgownEdgeROverlaps;

/**
 * A table for displaying a {@code  BallgownEdgeROverlaps} list using a
 * {@code  PathfindRPathwaysTableModel}. 
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see BallgownEdgeROverlaps
 * @see BallgownEdgeROverlapsTableModel
 * @see BallgownEdgeROverlapsTableSettings
 */
public class BallgownEdgeROverlapsTable extends JXTable {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@code BallgownEdgeROverlapsTable} for the specified {@code overlaps}
	 * list.
	 *
	 * @param pathways the {@code BallgownEdgeROverlaps} list.
	 */
	public BallgownEdgeROverlapsTable(BallgownEdgeROverlaps overlaps) {
		super(new BallgownEdgeROverlapsTableModel(overlaps));
	}
}
