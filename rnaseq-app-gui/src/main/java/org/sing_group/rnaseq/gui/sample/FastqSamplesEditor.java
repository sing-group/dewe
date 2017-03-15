package org.sing_group.rnaseq.gui.sample;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;

import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.core.entities.DefaultFastqReadsSample;
import org.sing_group.rnaseq.core.entities.DefaultFastqReadsSamples;
import org.sing_group.rnaseq.gui.sample.listener.SampleEditorListener;
import org.sing_group.rnaseq.gui.sample.listener.SamplesEditorListener;

import es.uvigo.ei.sing.hlfernandez.ui.icons.Icons;
import es.uvigo.ei.sing.hlfernandez.utilities.builder.JButtonBuilder;

public class FastqSamplesEditor extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_INITIAL_NUM_SAMPLES = 2;

	private List<SampleEditorComponent> samples = new LinkedList<>();
	private JPanel samplesPanel;
	private List<String> selectableConditions = Collections.emptyList();
	private int initialSamples;
	private JScrollPane samplesPaneScroll;

	public FastqSamplesEditor() {
		this(DEFAULT_INITIAL_NUM_SAMPLES);
	}

	public FastqSamplesEditor(int initialSamples) {
		this.initialSamples = initialSamples;
		this.init();
	}

	private void init() {
		this.setLayout(new BorderLayout());
		this.add(createButtonsPanel(), BorderLayout.NORTH);
		this.add(createSamplesPanel(), BorderLayout.CENTER);
	}

	private JPanel createButtonsPanel() {
		JPanel buttonsPanel = new JPanel(new BorderLayout());
		buttonsPanel.setOpaque(false);
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
		buttonsPanel.add(addBtn, BorderLayout.EAST);
		return buttonsPanel;
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

	protected FastqSampleEditor getFastqSampleEditor() {
		return new FastqSampleEditor(selectableConditions);
	}

	class SampleEditorComponent extends JPanel 
		implements SampleEditorListener 
	{
		private static final long serialVersionUID = 1L;

		private FastqSampleEditor editor;

		public SampleEditorComponent(FastqSampleEditor editor) {
			this.editor = editor;
			this.editor.addSampleEditorListener(this);
			this.init();
		}

		private void init() {
			this.setLayout(new FlowLayout());
			this.setOpaque(false);
			this.add(editor);
			this.add(getRemoveButton());
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
			sampleEdited();
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

	public boolean isValidSelection() {
		return 	!this.samples.stream()
				.map(SampleEditorComponent::isValidValue)
				.filter(valid -> (valid == false))
				.findAny().isPresent();
	}

	private void sampleAdded() {
		for (SamplesEditorListener l : getSamplesEditorListeners()) {
			l.onSampleAdded(new ChangeEvent(this));
		}
	}

	private void sampleRemoved() {
		for (SamplesEditorListener l : getSamplesEditorListeners()) {
			l.onSampleRemoved(new ChangeEvent(this));
		}
	}
	
	private void sampleEdited() {
		for (SamplesEditorListener l : getSamplesEditorListeners()) {
			l.onSampleEdited(new ChangeEvent(this));
		}
	}

	public synchronized void addSamplesEditorListener(SamplesEditorListener l) {
		this.listenerList.add(SamplesEditorListener.class, l);
	}

	public synchronized SamplesEditorListener[] getSamplesEditorListeners() {
		return this.listenerList.getListeners(SamplesEditorListener.class);
	}

	public void setSelectableConditions(List<String> conditions) {
		this.selectableConditions = Objects.requireNonNull(conditions);
		this.samples.stream().forEach(c -> c.setSelectableConditions(conditions));
	}

	public FastqReadsSamples getSamples() {
		return 	new DefaultFastqReadsSamples(
					this.samples.stream().map(SampleEditorComponent::getSample)
					.collect(Collectors.toList())
				);
	}

	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		if (samplesPaneScroll != null) {
			samplesPaneScroll.getViewport().setBackground(bg);
		}
	}
}
