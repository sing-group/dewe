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

import static java.util.Objects.requireNonNull;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.action.AbstractActionExt;
import org.sing_group.gc4s.ui.icons.Icons;

import es.uvigo.ei.aibench.workbench.Workbench;

/**
 * The "About" frame of the AIBench application.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class AboutFrame extends JDialog {
	private static final long serialVersionUID = 1L;

	private static final int FRAME_WIDTH = 390;
	private static final int LINE_HEIGHT = 16;
	private static final int IMAGE_HEIGHT = 105;

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
		super(Workbench.getInstance().getMainFrame(), "About DEWE", false);

		final String version = ResourceBundle.getBundle("dewe")
			.getString("version");

		final JPanel panel = new JPanel(new BorderLayout());

		final JLabel dewe = new JLabel("DEWE v" + version, SwingConstants.CENTER);
		fixSize(dewe, FRAME_WIDTH, LINE_HEIGHT);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(dewe, BorderLayout.NORTH);
		panel.add(getResearchGroupsPanel(), BorderLayout.CENTER);

		this.setContentPane(panel);

		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(Workbench.getInstance().getMainFrame());

		addEscapeListener(this);
	}

	private JPanel getResearchGroupsPanel() {
		JPanel researchGroupsPanel = new JPanel();
		researchGroupsPanel.setLayout(
			new BoxLayout(researchGroupsPanel, BoxLayout.Y_AXIS));
		GROUPS.stream().forEach(
			group -> researchGroupsPanel.add(getResearchGroupPanel(group)));
		return researchGroupsPanel;
	}

	private static JPanel getResearchGroupPanel(ResearchGroup group) {
		final JPanel toret = new JPanel();
		toret.setLayout(new BoxLayout(toret, BoxLayout.Y_AXIS));
		toret.setAlignmentX(CENTER_ALIGNMENT);
		toret.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));

		final JLabel lblLogo = new JLabel(
				group.getLogo(), SwingConstants.CENTER);
		final JXHyperlink link = new JXHyperlink(
				new URLLinkAction(group.getLink()));

		link.setHorizontalAlignment(SwingConstants.CENTER);
		link.setFocusable(false);

		fixSize(lblLogo, FRAME_WIDTH, IMAGE_HEIGHT);
		Stream.of(group.getDescription()).forEach(
				label -> fixSize(label, FRAME_WIDTH, LINE_HEIGHT));
		fixSize(link, FRAME_WIDTH, LINE_HEIGHT);

		toret.add(lblLogo);
		Stream.of(group.getDescription()).forEach(toret::add);
		toret.add(link);

		lblLogo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AboutFrame.openURL(group.getLink());
			}
		});
		return toret;
	}

	public void addToJMenubar(JMenuBar menuBar, boolean icon) {
		if (menuBar != null) {
			final JMenu helpMenu = new JMenu("Help");
			helpMenu.add(new JMenuItem(
				icon ? ACTION_OPEN_ABOUT_ICON : ACTION_OPEN_ABOUT));
			menuBar.add(helpMenu);
			menuBar.updateUI();
		}
	}

	public void addToJToolbar(JToolBar toolBar) {
		if (toolBar != null) {
			toolBar.add(AboutFrame.ACTION_OPEN_ABOUT_ICON);
			toolBar.updateUI();
		}
	}

	private final static void fixSize(
		Component component, int width, int height
	) {
		requireNonNull(component);

		final Dimension size = new Dimension(
			(width <= 0) ? component.getWidth() : width,
			(height <= 0) ? component.getHeight() : height
		);

		component.setPreferredSize(size);
		component.setMinimumSize(size);
		component.setMaximumSize(size);
		component.setSize(size);
	}

	private final static class URLLinkAction extends AbstractActionExt {
		private static final long serialVersionUID = 1L;

		public URLLinkAction(String url) {
			super(url);
		}

		public void actionPerformed(ActionEvent e) {
			AboutFrame.openURL(this.getName());
		}
	}

	public static void openURL(String url) {
		try {
			Desktop.getDesktop().browse(new URI(url));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
				null,
				"Could not open URL: " + url,
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		}
	}

	public static void addEscapeListener(final JDialog dialog) {
		dialog.getRootPane().registerKeyboardAction(
			e -> dialog.setVisible(false),
			KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
			JComponent.WHEN_IN_FOCUSED_WINDOW
		);
	}

	static class ResearchGroup {
		private JLabel[] description;
		private String link;
		private ImageIcon logo;

		public ResearchGroup(JLabel[] description, String link, ImageIcon logo) {
			this.description = description;
			this.link = link;
			this.logo = logo;
		}

		public JLabel[] getDescription() {
			return description;
		}

		public ImageIcon getLogo() {
			return logo;
		}

		public String getLink() {
			return link;
		}
	}
}
