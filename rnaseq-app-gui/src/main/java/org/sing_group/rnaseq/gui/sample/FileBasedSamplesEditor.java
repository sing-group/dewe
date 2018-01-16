/*
 * #%L
 * DEWE GUI
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
package org.sing_group.rnaseq.gui.sample;

import static java.util.stream.Collectors.toList;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;

import org.sing_group.gc4s.ui.CenteredJPanel;
import org.sing_group.gc4s.ui.icons.Icons;
import org.sing_group.gc4s.utilities.builder.JButtonBuilder;
import org.sing_group.rnaseq.api.entities.FileBasedSample;
import org.sing_group.rnaseq.api.entities.FileBasedSamples;
import org.sing_group.rnaseq.gui.sample.listener.SampleEditorListener;
import org.sing_group.rnaseq.gui.sample.listener.SamplesEditorListener;

/**
 * An abstract, generic component to select a list of {@code FileBasedSample}.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 * @param <T> the type of the list
 * @param <E> the type of the elements in the list
 */
public abstract class FileBasedSamplesEditor<T extends FileBasedSamples<E>, E extends FileBasedSample>
	extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final ImageIcon ICON_WARNING = Icons.ICON_WARNING_COLOR_24;
	private static final String WARNING_SAMPLES = "Some samples are not valid.";
	private static final ImageIcon ICON_OK = Icons.ICON_OK_COLOR_24;

	private JPanel samplesPanel;
	private JScrollPane samplesPaneScroll;
	protected List<FileBasedSampleEditorComponent> samples = new LinkedList<>();
	private JLabel configurationStatusLabel;
	private List<String> warningsMessages = Collections.emptyList();

	/**
	 * Creates a new {@code FileBasedSamplesEditor}.
	 */
	public FileBasedSamplesEditor() {
		this.init();
	}

	private void init() {
		this.setLayout(new BorderLayout());
		this.add(createButtonsPanel(), BorderLayout.NORTH);
		this.add(createSamplesPanel(), BorderLayout.CENTER);
	}

	private JComponent createButtonsPanel() {
		JToolBar buttonsToolbar = new JToolBar();
		buttonsToolbar.setOpaque(false);
		buttonsToolbar.setFloatable(false);
		buttonsToolbar.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 10));

		JButton removeSamplesBtn = JButtonBuilder.newJButtonBuilder()
			.withText("Remove all samples")
			.withTooltip("Removes all samples from the analysis")
			.withIcon(Icons.ICON_TRASH_16)
			.thatDoes(new AbstractAction() {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					removeAllSamples();
				}
			})
			.build();

		JButton addBtn = JButtonBuilder.newJButtonBuilder()
			.withText("Add sample")
			.withTooltip("Adds a new sample to the analysis")
			.withIcon(Icons.ICON_ADD_16)
			.thatDoes(new AbstractAction() {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					addSampleEditorComponent();

				}
			})
			.build();

		configurationStatusLabel = new JLabel(ICON_WARNING);

		buttonsToolbar.add(removeSamplesBtn);
		buttonsToolbar.add(Box.createHorizontalGlue());
		buttonsToolbar.add(configurationStatusLabel);
		buttonsToolbar.add(Box.createHorizontalGlue());
		buttonsToolbar.add(addBtn);

		return buttonsToolbar;
	}

	private void removeAllSamples() {
		this.samples.clear();
		this.samplesPanel.removeAll();
		sampleRemoved();
		this.updateUI();
	}

	private void addSampleEditorComponent() {
		FileBasedSampleEditorComponent editor =
			new FileBasedSampleEditorComponent(getFileBasedSampleEditor());
		editor.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		samplesPanel.add(editor);
		samples.add(editor);
		sampleAdded();
		this.updateUI();
	}

	private JComponent createSamplesPanel() {
		samplesPanel = new JPanel();
		samplesPanel.setOpaque(false);
		samplesPanel.setLayout(new BoxLayout(samplesPanel, BoxLayout.Y_AXIS));
		for (int i = 0; i < 2; i++) {
			addSampleEditorComponent();
		}
		samplesPaneScroll = new JScrollPane(samplesPanel);

		return samplesPaneScroll;
	}

	protected abstract FileBasedSampleEditor<E> getFileBasedSampleEditor();

	class FileBasedSampleEditorComponent extends JPanel
		implements SampleEditorListener
	{
		private static final long serialVersionUID = 1L;
		private static final String WARNING_LABEL_TOOLTIP =
			"Some sample data is missing, please, revise it.";

		private FileBasedSampleEditor<E> editor;
		private JLabel warningLabel;

		public FileBasedSampleEditorComponent(FileBasedSampleEditor<E> editor) {
			this.editor = editor;
			this.editor.addSampleEditorListener(this);
			this.init();
		}

		private void init() {
			this.setLayout(new FlowLayout());
			this.add(editor);
			this.add(getButtonsPanel());
		}

		private JPanel getButtonsPanel() {
			JPanel buttonsPanel = new JPanel(new GridLayout(2, 1));
			warningLabel = new JLabel(ICON_WARNING);
			warningLabel.setToolTipText(WARNING_LABEL_TOOLTIP);
			warningLabel.setVisible(!this.editor.isValidValue());

			buttonsPanel.add(getRemoveButton());
			buttonsPanel.add(warningLabel);
			buttonsPanel.setOpaque(false);

			CenteredJPanel toret = new CenteredJPanel(buttonsPanel);
			toret.setOpaque(false);

			return toret;
		}

		private JButton getRemoveButton() {
			JButton closeBtn = 	JButtonBuilder.newJButtonBuilder()
				.withIcon(Icons.ICON_CANCEL_16)
				.withTooltip("Removes this sample")
				.thatDoes(new AbstractAction() {
					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						removeEditorComponent(FileBasedSampleEditorComponent.this);

					}
				})
				.build();
			return closeBtn;
		}

		public E getSample() {
			return this.editor.getSample();
		}

		public boolean isValidValue() {
			return this.editor.isValidValue();
		}

		@Override
		public void onSampleEdited(ChangeEvent event) {
			this.warningLabel.setVisible(!this.editor.isValidValue());
			sampleEdited();
		}
	}

	private void removeEditorComponent(FileBasedSampleEditorComponent component) {
		SwingUtilities.invokeLater(() -> {
			this.samples.remove(component);
			samplesPanel.remove(component);
			sampleRemoved();
			this.updateUI();
		});
	}

	/**
	 * The list of selected samples.
	 *
	 * @return list of selected samples
	 */
	public abstract T getSamples();

	protected List<E> getSamplesList() {
		return 	this.samples.stream()
				.map(FileBasedSampleEditorComponent::getSample)
				.collect(toList());
	}

	/**
	 * Returns {@code true} if the current selection is valid and {@code false}
	 * otherwise.
	 *
	 * @return {@code true} if the current selection is valid and {@code false}
	 *         otherwise
	 */
	public boolean isValidSelection() {
		return allSamplesAreValid();
	}

	private boolean allSamplesAreValid() {
		return !this.samples.stream()
			.map(FileBasedSampleEditorComponent::isValidValue)
			.filter(valid -> (valid == false)).findAny().isPresent();
	}

	private void sampleAdded() {
		updateConfigurationStatusLabel();
		for (SamplesEditorListener l : getSamplesEditorListeners()) {
			l.onSampleAdded(new ChangeEvent(this));
		}
	}

	private void sampleRemoved() {
		updateConfigurationStatusLabel();
		for (SamplesEditorListener l : getSamplesEditorListeners()) {
			l.onSampleRemoved(new ChangeEvent(this));
		}
	}

	private void sampleEdited() {
		updateConfigurationStatusLabel();
		for (SamplesEditorListener l : getSamplesEditorListeners()) {
			l.onSampleEdited(new ChangeEvent(this));
		}
	}

	private void updateConfigurationStatusLabel() {
		this.configurationStatusLabel.setToolTipText(null);
		boolean validSelection = this.isValidSelection();
		if (validSelection && !hasWarnings()) {
			this.configurationStatusLabel.setIcon(ICON_OK);
		} else {
			this.configurationStatusLabel.setIcon(ICON_WARNING);
			String warningsMessage = null;
			if (!this.allSamplesAreValid()) {
				warningsMessage = getWarningsMessage(WARNING_SAMPLES);
			} else {
				warningsMessage = getWarningsMessage();
			}
			this.configurationStatusLabel.setToolTipText(warningsMessage);
		}
	}


	private String getWarningsMessage(String...warnings) {
		List<String> allWarnings = new ArrayList<>();
		allWarnings.addAll(Arrays.asList(warnings));
		allWarnings.addAll(warningsMessages);

		StringBuilder sb = new StringBuilder();
		sb.append("<html><p>Warnings:</p><ul>");
		sb.append(allWarnings.stream().collect(Collectors.joining("</li><li>", "<li>", "</li>")));
		sb.append("</ul></html>");

		return sb.toString();
	}

	private boolean hasWarnings() {
		return !this.warningsMessages.isEmpty();
	}

	/**
	 * Sets the warning icon visible with the specified warning messages as
	 * tooltip.
	 *
	 * @param warningMessages the warning message to use as tooltip
	 */
	protected void setWarningMessages(List<String> warningMessages) {
		this.warningsMessages = warningMessages;
		this.updateConfigurationStatusLabel();
	}

	/**
	 * Hides the warning icon.
	 */
	protected void removeWarningMessages() {
		this.warningsMessages = Collections.emptyList();
		this.updateConfigurationStatusLabel();
	}

	/**
	 * Adds the specified {@code SamplesEditorListener} to the listeners list.
	 *
	 * @param l a {@code SamplesEditorListener}
	 */
	public synchronized void addSamplesEditorListener(SamplesEditorListener l) {
		this.listenerList.add(SamplesEditorListener.class, l);
	}

	/**
	 * Returns the list of all registered {@code SamplesEditorListener}s.
	 *
	 * @return the list of all registered {@code SamplesEditorListener}s
	 */
	public synchronized SamplesEditorListener[] getSamplesEditorListeners() {
		return this.listenerList.getListeners(SamplesEditorListener.class);
	}
}
