/*
 * #%L
 * DEWE GUI
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
package org.sing_group.rnaseq.gui.sample;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;

import org.sing_group.gc4s.ui.CenteredJPanel;
import org.sing_group.gc4s.ui.icons.Icons;
import org.sing_group.gc4s.utilities.builder.JButtonBuilder;
import org.sing_group.rnaseq.api.entities.FastqReadsSample;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.core.controller.helper.AbstractDifferentialExpressionWorkflow;
import org.sing_group.rnaseq.core.entities.DefaultFastqReadsSample;
import org.sing_group.rnaseq.core.entities.DefaultFastqReadsSamples;
import org.sing_group.rnaseq.gui.sample.FastqSampleEditor.SampleType;
import org.sing_group.rnaseq.gui.sample.listener.SampleEditorListener;
import org.sing_group.rnaseq.gui.sample.listener.SamplesEditorListener;

/**
 * A graphical component that allows the edition of several
 * {@code FastqReadsSample} objects by using {@code FastqSampleEditor}.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 * @see FastqSampleEditor
 */
public class FastqSamplesEditor extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_INITIAL_NUM_SAMPLES = 2;
	private static final ImageIcon ICON_WARNING = Icons.ICON_WARNING_COLOR_24;
	private static final String WARNING_SAMPLES = "Some samples are not valid.";
	private static final ImageIcon ICON_OK = Icons.ICON_OK_COLOR_24;

	private SampleType sampleType = SampleType.PAIRED_END;
	private List<SampleEditorComponent> samples = new LinkedList<>();
	private JPanel samplesPanel;
	private List<String> selectableConditions = Collections.emptyList();
	private int initialSamples;
	private JScrollPane samplesPaneScroll;
	private JLabel configurationStatusLabel;
	private List<String> warningsMessages = Collections.emptyList();

	/**
	 * Creates a new {@code FastqSamplesEditor} with the default number of
	 * samples.
	 */
	public FastqSamplesEditor() {
		this(DEFAULT_INITIAL_NUM_SAMPLES);
	}

	/**
	 * Creates a new {@code FastqSamplesEditor} with the specified number of
	 * samples.
	 *
	 * @param initialSamples the number of initial samples
	 */
	public FastqSamplesEditor(int initialSamples) {
		this.initialSamples = initialSamples;
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
		JButton summaryBtn = JButtonBuilder.newJButtonBuilder()
			.withText("Summary")
			.withTooltip("Shows a summary of all samples")
			.withIcon(Icons.ICON_INFO_16)
			.thatDoes(new AbstractAction() {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					showSamplesSummary();
				}
			})
			.build();

		configurationStatusLabel = new JLabel(ICON_WARNING);

		buttonsToolbar.add(removeSamplesBtn);
		buttonsToolbar.add(Box.createHorizontalGlue());
		buttonsToolbar.add(configurationStatusLabel);
		buttonsToolbar.add(Box.createHorizontalGlue());
		buttonsToolbar.add(addBtn);
		buttonsToolbar.add(summaryBtn);

		return buttonsToolbar;
	}

	private JComponent createSamplesPanel() {
		samplesPanel = new JPanel();
		samplesPanel.setOpaque(false);
		samplesPanel.setLayout(new BoxLayout(samplesPanel, BoxLayout.Y_AXIS));
		for (int i = 0; i < initialSamples; i++) {
			addSampleEditorComponent();
		}
		samplesPaneScroll = new JScrollPane(samplesPanel);

		return samplesPaneScroll;
	}

	private void addSampleEditorComponent() {
		SampleEditorComponent editor =
			new SampleEditorComponent(getFastqSampleEditor());
		editor.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		samplesPanel.add(editor);
		samples.add(editor);
		sampleAdded();
		this.updateUI();
	}

	private void showSamplesSummary() {
		showMessageDialog(getParent(), getSamplesSummary(),
			"Samples summary", JOptionPane.INFORMATION_MESSAGE);
	}

	private JComponent getSamplesSummary() {
		JTextArea textArea = new JTextArea();
		textArea.setTabSize(2);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setOpaque(false);
		textArea.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		textArea.setText(AbstractDifferentialExpressionWorkflow
			.getSamplesSummary(getSamples(), isPairedEnd()));
		JScrollPane toret = new JScrollPane(textArea);
		Dimension preferredSize = new Dimension(600, 400);
		toret.setPreferredSize(preferredSize);
		textArea.setSelectionStart(0);
		textArea.setSelectionEnd(0);
		return toret;
	}

	private boolean isPairedEnd() {
		return this.sampleType.isPairedEnd();
	}

	protected FastqSampleEditor getFastqSampleEditor() {
		return new FastqSampleEditor(sampleType.isPairedEnd(),
			selectableConditions);
	}

	class SampleEditorComponent extends JPanel
		implements SampleEditorListener
	{
		private static final long serialVersionUID = 1L;

		private static final String WARNING_LABEL_TOOLTIP =
			"Some sample data is missing, please, revise it.";
		private FastqSampleEditor editor;
		private JLabel warningLabel;

		public SampleEditorComponent(FastqSampleEditor editor) {
			this.editor = editor;
			this.editor.addSampleEditorListener(this);
			this.init();
		}

		private void init() {
			this.setLayout(new FlowLayout());
			this.setOpaque(false);
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
						removeEditorComponent(SampleEditorComponent.this);
					}
				})
				.build();
			return closeBtn;
		}

		public DefaultFastqReadsSample getSample() {
			return this.editor.getSample();
		}

		public boolean isValidValue() {
			return this.editor.isValidValue();
		}

		public void setSelectableConditions(List<String> selectableConditions) {
			this.editor.setSelectableConditions(selectableConditions);
		}

		@Override
		public void onSampleEdited(ChangeEvent event) {
			checkConfigurationStatus();
			sampleEdited();
		}

		public void setSample(FastqReadsSample s) {
			this.editor.setSample(s);
		}

		public void setSampleType(SampleType sampleType) {
			this.editor.setSampleType(sampleType);
		}

		public void checkConfigurationStatus() {
			this.warningLabel.setVisible(!this.editor.isValidValue());
		}
	}

	private void removeEditorComponent(SampleEditorComponent component) {
		SwingUtilities.invokeLater(() -> {
			this.samples.remove(component);
			this.samplesPanel.remove(component);
			sampleRemoved();
			this.updateUI();
		});
	}

	/**
	 * Tells the component to check if the current configuration is valid.
	 */
	public void checkConfigurationStatus() {
		this.samples.forEach(s -> s.checkConfigurationStatus());
		this.updateConfigurationStatusLabel();
	}

	private void updateConfigurationStatusLabel() {
		this.configurationStatusLabel.setToolTipText(null);
		boolean validSelection = this.isValidSelection();
		if (validSelection && !hasWarnings()) {
			this.configurationStatusLabel.setIcon(ICON_OK);
		} else {
			this.configurationStatusLabel.setIcon(ICON_WARNING);
			String warningsMessage = null;
			if (!validSelection) {
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
	 * Returns {@code true} if the current values are valid for creating a new
	 * {@code FastqReadsSample} objects and {@code false} otherwise.
	 *
	 * @return {@code true} if the current values are valid for creating a new
	 * {@code FastqReadsSample} objects and {@code false} otherwise.
	 */
	public boolean isValidSelection() {
		return 	!this.samples.stream()
				.map(SampleEditorComponent::isValidValue)
				.filter(valid -> (valid == false))
				.findAny().isPresent();
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

	/**
	 * Adds the specified {@code SampleEditorListener} to the listeners list.
	 *
	 * @param l a {@code SampleEditorListener}
	 */
	public synchronized void addSamplesEditorListener(SamplesEditorListener l) {
		this.listenerList.add(SamplesEditorListener.class, l);
	}

	/**
	 * Returns the list of all registered {@code SampleEditorListener}s.
	 *
	 * @return the list of all registered {@code SampleEditorListener}s
	 */
	public synchronized SamplesEditorListener[] getSamplesEditorListeners() {
		return this.listenerList.getListeners(SamplesEditorListener.class);
	}

	/**
	 * Sets the list of selectable conditions.
	 *
	 * @param selectableConditions the list of selectable conditions
	 */
	public void setSelectableConditions(List<String> selectableConditions) {
		this.selectableConditions = Objects
			.requireNonNull(selectableConditions);
		this.samples.stream()
			.forEach(c -> c.setSelectableConditions(selectableConditions));
	}

	/**
	 * Returns the list of {@code FastqReadsSamples} with the introduced
	 * samples.
	 *
	 * @return the list of {@code FastqReadsSamples} with the introduced
	 *         samples
	 */
	public FastqReadsSamples getSamples() {
		return 	new DefaultFastqReadsSamples(
					this.samples.stream().map(SampleEditorComponent::getSample)
					.collect(Collectors.toList())
				);
	}

	/**
	 * Sets the list of samples to edit and removes the previous one.
	 *
	 * @param samples the new list of samples to edit
	 */
	public void setSamples(List<FastqReadsSample> samples) {
		this.removeAllSamples();
		samples.forEach(s -> addSampleEditorComponent(s));
		this.updateUI();
	}

	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		if (samplesPaneScroll != null) {
			samplesPaneScroll.getViewport().setBackground(bg);
		}
	}

	private void removeAllSamples() {
		this.samples.clear();
		this.samplesPanel.removeAll();
		sampleRemoved();
		this.updateUI();
	}

	private void addSampleEditorComponent(FastqReadsSample s) {
		SampleEditorComponent editor = new SampleEditorComponent(
			getFastqSampleEditor());
		editor.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		editor.setSample(s);
		samplesPanel.add(editor);
		samples.add(editor);
		sampleAdded();
	}

	/**
	 * Sets the warning icon visible with the specified warning messages as
	 * tooltip.
	 *
	 * @param warningMessages the warning message to use as tooltip
	 */
	public void setWarningMessages(List<String> warningMessages) {
		this.warningsMessages = warningMessages;
		this.updateConfigurationStatusLabel();
	}

	/**
	 * Hides the warning icon.
	 */
	public void removeWarningMessages() {
		this.warningsMessages = Collections.emptyList();
		this.updateConfigurationStatusLabel();
	}


	/**
	 * Sets the {@code SampleType}.
	 *
	 * @param sampleType the {@code SampleType}
	 */
	public void setSampleType(SampleType sampleType) {
		this.sampleType = sampleType;
		this.samples.forEach(s -> {
			s.setSampleType(sampleType);
		});
	}
}
