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
package org.sing_group.rnaseq.aibench;

import static es.uvigo.ei.aibench.workbench.inputgui.Common.MULTIPLE_FILE_CHOOSER;
import static es.uvigo.ei.aibench.workbench.inputgui.Common.SINGLE_FILE_CHOOSER;
import static es.uvigo.ei.aibench.workbench.utilities.ClearClipboardAction.ICON_24;
import static javax.swing.SwingUtilities.invokeLater;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import org.sing_group.gc4s.demo.DemoUtils;
import org.sing_group.rnaseq.aibench.gui.AboutFrame;
import org.sing_group.rnaseq.aibench.gui.HelpFrame;
import org.sing_group.rnaseq.aibench.gui.components.ReferenceGenomeIndexManagerComponent;
import org.sing_group.rnaseq.api.environment.AppEnvironment;
import org.sing_group.rnaseq.api.environment.execution.check.BinaryCheckException;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.environment.DefaultAppEnvironment;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeIndexDatabaseManager;
import org.sing_group.rnaseq.gui.util.CommonFileChooser;

import es.uvigo.ei.aibench.TextAreaAppender;
import es.uvigo.ei.aibench.workbench.MainWindow;
import es.uvigo.ei.aibench.workbench.Workbench;
import es.uvigo.ei.aibench.workbench.utilities.ClearClipboardAction;

/**
 * The lifecycle of the AIBench application. Its main purpose is to configure 
 * the application when it is started.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class Lifecycle extends org.platonos.pluginengine.PluginLifecycle {
	public static final String APP_CONFIGURATION_FILE = "conf/app.conf";
	public static final ImageIcon ICON =
		new ImageIcon(Lifecycle.class.getResource("/icons/app-icon.png"));

	@Override
	public void start() {
		invokeLater(this::configureApplication);
	}

	private final void configureApplication() {
		TextAreaAppender.clearGUIComponent();
		configureLocale();
		configureMainWindow();
		configureAIBenchToolbar();
		configureAIBenchMenuBar();
		configureApp();
	}

	private static final void configureLocale() {
		Locale.setDefault(Locale.ENGLISH);
		JComponent.setDefaultLocale(Locale.ENGLISH);
	}

	private void configureMainWindow() {
		DemoUtils.setNimbusKeepAlternateRowColor();

		MainWindow window = (MainWindow) Workbench.getInstance().getMainFrame();
		window.getDocumentTabbedPane().getParent().setBackground(Color.WHITE);

		JFrame mainFrame = Workbench.getInstance().getMainFrame();
		mainFrame.setIconImage(ICON.getImage());
	}

	private void configureAIBenchToolbar() {
		if(Workbench.getInstance().getToolBar() != null) {
			JToolBar toolBar = Workbench.getInstance().getToolBar();
			toolBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			toolBar.setOpaque(true);
			toolBar.setBackground(Color.WHITE);
			toolBar.setFloatable(false);
			toolBar.add(Box.createHorizontalGlue());
			toolBar.add(new ClearClipboardAction(ICON_24));
		}
	}

	private void configureAIBenchMenuBar() {
		AboutFrame.getInstance()
			.addToJMenubar(Workbench.getInstance().getMenuBar(), false);
		HelpFrame.getInstance()
			.addToJMenubar(Workbench.getInstance().getMenuBar(), false);
	}

	private void configureApp() {
		AppEnvironment environment = null;
		try {
			environment = new DefaultAppEnvironment(new File(APP_CONFIGURATION_FILE));
			this.configureFileChooser(environment);
		} catch (FileNotFoundException e) {
			Workbench.getInstance().error(e, "Configuration file not found");
		} catch (IOException e) {
			Workbench.getInstance().error(e, "Error reading configuration file");
		} catch(IllegalStateException e) {
			Workbench.getInstance().error(e);
		}
		if(environment != null) {
			try {
				DefaultAppController.getInstance().setAppEvironment(environment);
			} catch (BinaryCheckException e) {
				Workbench.getInstance().error(e, "Error while checking binaries: " + e.getMessage());
			}
		} else {
			Workbench.getInstance().error("Environment not available.");
		}
		this.updateReferenceGenomeManagerComponent();
	}

	public void updateReferenceGenomeManagerComponent() {
		ReferenceGenomeIndexManagerComponent component =
			(ReferenceGenomeIndexManagerComponent) Workbench.getInstance()
				.getComponentAtSlot("referencegenomemanager");
		component.setReferenceGenomeDatabaseManager(
			DefaultReferenceGenomeIndexDatabaseManager.getInstance()
		);
	}

	private void configureFileChooser(AppEnvironment env) {
		String defaultDirectory = System.getProperty("user.home");
		if (env.hasProperty("default.directory")) {
			defaultDirectory = env.getProperty("default.directory").get();
		}
		SINGLE_FILE_CHOOSER.setCurrentDirectory(new File(defaultDirectory));
		MULTIPLE_FILE_CHOOSER.setCurrentDirectory(new File(defaultDirectory));
		CommonFileChooser.getInstance().setSingleFilechooser(SINGLE_FILE_CHOOSER);
	}
}
