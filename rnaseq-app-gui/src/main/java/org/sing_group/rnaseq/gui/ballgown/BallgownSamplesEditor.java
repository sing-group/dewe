package org.sing_group.rnaseq.gui.ballgown;

import static java.util.stream.Collectors.toList;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;

import org.sing_group.rnaseq.api.entities.ballgown.BallgownSample;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownSamples;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownSamples;
import org.sing_group.rnaseq.gui.ballgown.listener.BallgownSampleEditorListener;
import org.sing_group.rnaseq.gui.ballgown.listener.BallgownSamplesEditorListener;

import es.uvigo.ei.sing.hlfernandez.ui.icons.Icons;
import es.uvigo.ei.sing.hlfernandez.utilities.builder.JButtonBuilder;

public class BallgownSamplesEditor extends JPanel {
	private static final long serialVersionUID = 1L;
	private List<BallgownSampleEditorComponent> samples = new LinkedList<>();

	public BallgownSamplesEditor() {
		this.init();
	}

	private void init() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.add(createButtonsPanel());
		for(int i = 0; i < 2; i++) {
			addSampleEditorComponent();
		}
	}
	
	private JPanel createButtonsPanel() {
		JPanel buttonsPanel = new JPanel(new BorderLayout());
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

	private void addSampleEditorComponent() {
		BallgownSampleEditorComponent editor = 
			new BallgownSampleEditorComponent(new BallgownSampleEditor());
		editor.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		this.add(editor);
		samples.add(editor);
		sampleAdded();
		this.updateUI();
	}

	class BallgownSampleEditorComponent extends JPanel 
		implements BallgownSampleEditorListener 
	{
		private static final long serialVersionUID = 1L;

		private BallgownSampleEditor editor;

		public BallgownSampleEditorComponent(BallgownSampleEditor editor) {
			this.editor = editor;
			this.editor.addBallgownSampleEditorListener(this);
			this.init();
		}

		private void init() {
			this.setLayout(new FlowLayout());
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
						removeEditorComponent(BallgownSampleEditorComponent.this);
						
					}
				})
				.build();
			return closeBtn;
		}

		public BallgownSample getSample() {
			return this.editor.getSample();
		}

		public boolean isValidValue() {
			return this.editor.isValidValue();
		}

		@Override
		public void onSampleEdited(ChangeEvent event) {
			sampleEdited();
		}
	}
	
	private void removeEditorComponent(BallgownSampleEditorComponent component) {
		SwingUtilities.invokeLater(() -> {
			this.samples.remove(component);
			this.remove(component);
			sampleRemoved();
			this.updateUI();
		});
	}

	public BallgownSamples getSamples() {
		return new DefaultBallgownSamples(getSamplesList());
	}

	private List<BallgownSample> getSamplesList() {
		return 	this.samples.stream()
				.map(BallgownSampleEditorComponent::getSample)
				.collect(toList());
	}

	public boolean isValidSelection() {
		return 	!this.samples.stream()
				.map(BallgownSampleEditorComponent::isValidValue)
				.filter(valid -> (valid == false))
				.findAny().isPresent();
	}

	private void sampleAdded() {
		for (BallgownSamplesEditorListener l : getBallgownSamplesEditorListeners()) {
			l.onSampleAdded(new ChangeEvent(this));
		}
	}

	private void sampleRemoved() {
		for (BallgownSamplesEditorListener l : getBallgownSamplesEditorListeners()) {
			l.onSampleRemoved(new ChangeEvent(this));
		}
	}
	
	private void sampleEdited() {
		for (BallgownSamplesEditorListener l : getBallgownSamplesEditorListeners()) {
			l.onSampleEdited(new ChangeEvent(this));
		}
	}

	public synchronized void addBallgownSamplesEditorListener(BallgownSamplesEditorListener l) {
		this.listenerList.add(BallgownSamplesEditorListener.class, l);
	}

	public synchronized BallgownSamplesEditorListener[] getBallgownSamplesEditorListeners() {
		return this.listenerList.getListeners(BallgownSamplesEditorListener.class);
	}
}