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

/**
 * The "About" frame of the AIBench application.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class AboutFrame extends AbstractInfoFrame {
	private static final long serialVersionUID = 1L;

	private final static ImageIcon IMAGE_ABOUT_SMALL = Icons.ICON_INFO_2_24;

	private final static List<AboutFrame.ResearchGroup> GROUPS;
	private final static ImageIcon IMAGE_SING =
		new ImageIcon(AboutFrame.class.getResource("images/sing.png"));
	private final static String LINK_SING = "http://www.sing-group.org";
	private final static ImageIcon IMAGE_CSIC =
		new ImageIcon(AboutFrame.class.getResource("images/csic.png"));
	private final static String LINK_CSIC = "http://www.ipla.csic.es/";

	private final static JLabel[] DESCRIPTION_SING	= new JLabel[] {
		new JLabel("SING Research Group", SwingConstants.CENTER),
		new JLabel("Informatics Department", SwingConstants.CENTER),
		new JLabel("Higher Technical School of Computer Engineering", SwingConstants.CENTER),
		new JLabel("University of Vigo at Ourense Campus", SwingConstants.CENTER),
		new JLabel("E-32004 Ourense", SwingConstants.CENTER),
		new JLabel("Spain", SwingConstants.CENTER)
	};

	private final static JLabel[] DESCRIPTION_CSIC	= new JLabel[] {
		new JLabel("Probiotics and Prebiotics group", SwingConstants.CENTER),
		new JLabel("Department of Microbiology and Biochemistry", SwingConstants.CENTER),
		new JLabel("IPLA: Dairy Institute of Asturias", SwingConstants.CENTER),
		new JLabel("Spanish National Research Council (CSIC)", SwingConstants.CENTER),
		new JLabel("E33300 Villaviciosa, Principado de Asturias", SwingConstants.CENTER),
		new JLabel("Spain", SwingConstants.CENTER)
	};

	static {
		GROUPS = new LinkedList<AboutFrame.ResearchGroup>();
		GROUPS.add(new ResearchGroup(DESCRIPTION_CSIC, LINK_CSIC, IMAGE_CSIC));
		GROUPS.add(new ResearchGroup(DESCRIPTION_SING, LINK_SING, IMAGE_SING));
	}

	private static AboutFrame instance;

	private synchronized static void createInstance() {
		if (AboutFrame.instance == null) {
			AboutFrame.instance = new AboutFrame();
		}
	}

	public static AboutFrame getInstance() {
		if (AboutFrame.instance == null) {
			AboutFrame.createInstance();
		}
		return AboutFrame.instance;
	}

	private final static Action ACTION_OPEN_ABOUT =
		new AbstractAction("About DEWE") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				AboutFrame.getInstance().setVisible(true);
			}
		};

	private final static Action ACTION_OPEN_ABOUT_ICON =
		new AbstractAction("About DEWE", AboutFrame.IMAGE_ABOUT_SMALL) {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				AboutFrame.getInstance().setVisible(true);
			}
		};

	static {
		AboutFrame.ACTION_OPEN_ABOUT.putValue(
			Action.SHORT_DESCRIPTION, "About DEWE");
		AboutFrame.ACTION_OPEN_ABOUT_ICON.putValue(
			Action.SHORT_DESCRIPTION, "About DEWE");
	}

	private AboutFrame() {
		super("About DEWE");
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
