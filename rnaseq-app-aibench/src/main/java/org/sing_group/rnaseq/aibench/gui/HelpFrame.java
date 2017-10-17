/*
 * #%L
 * DEWE
 * %%
 * Copyright (C) 2016 - 2017 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola,
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
package org.sing_group.rnaseq.aibench.gui;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import org.sing_group.gc4s.ui.icons.Icons;
import org.sing_group.rnaseq.aibench.Lifecycle;

/**
 * The "Help" frame of the AIBench application.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class HelpFrame extends AbstractInfoFrame {
	private static final long serialVersionUID = 1L;

	private final static ImageIcon IMAGE_ABOUT_SMALL = Icons.ICON_QUESTION_4_24;

	private final static ImageIcon IMAGE = Icons.getScaledIcon(Lifecycle.ICON, 78, 78);
	private final static String LINK = "http://www.sing-group.org/dewe";

	private final static JLabel[] HELP	= new JLabel[] {
		new JLabel("Visit the official web page for help, manual and tutorials:", SwingConstants.CENTER),
	};

	private static LinkedList<ResearchGroup> GROUPS;

	static {
		GROUPS = new LinkedList<HelpFrame.ResearchGroup>();
		GROUPS.add(new ResearchGroup(HELP, LINK, IMAGE));
	}

	private static HelpFrame instance;

	private synchronized static void createInstance() {
		if (HelpFrame.instance == null) {
			HelpFrame.instance = new HelpFrame();
		}
	}

	public static HelpFrame getInstance() {
		if (HelpFrame.instance == null) {
			HelpFrame.createInstance();
		}
		return HelpFrame.instance;
	}

	private final static Action ACTION_OPEN_ABOUT =
		new AbstractAction("DEWE Help") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				HelpFrame.getInstance().setVisible(true);
			}
		};

	private final static Action ACTION_OPEN_ABOUT_ICON =
		new AbstractAction("DEWE Help", HelpFrame.IMAGE_ABOUT_SMALL) {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				HelpFrame.getInstance().setVisible(true);
			}
		};

	static {
		HelpFrame.ACTION_OPEN_ABOUT.putValue(
			Action.SHORT_DESCRIPTION, "DEWE Help");
		HelpFrame.ACTION_OPEN_ABOUT_ICON.putValue(
			Action.SHORT_DESCRIPTION, "DEWE Help");
	}

	private HelpFrame() {
		super("DEWE Help");
	}

	public void addToJMenubar(JMenuBar menuBar, boolean icon) {
		super.addToJMenubar(menuBar, icon, ACTION_OPEN_ABOUT_ICON, ACTION_OPEN_ABOUT);
	}

	public void addToJToolbar(JToolBar toolBar) {
		super.addToJToolbar(toolBar, ACTION_OPEN_ABOUT_ICON);
	}

	@Override
	protected List<ResearchGroup> getResearchGroups() {
		return GROUPS;
	}
}
