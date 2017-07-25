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

public class Lifecycle extends org.platonos.pluginengine.PluginLifecycle {
	public static final String APP_CONFIGURATION_FILE = "conf/app.conf";
	private static final ImageIcon ICON =
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
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
			defaultDirectory = env.getProperty("default.directory");
		}
		SINGLE_FILE_CHOOSER.setCurrentDirectory(new File(defaultDirectory));
		MULTIPLE_FILE_CHOOSER.setCurrentDirectory(new File(defaultDirectory));
		CommonFileChooser.getInstance().setSingleFilechooser(SINGLE_FILE_CHOOSER);
	}
}
