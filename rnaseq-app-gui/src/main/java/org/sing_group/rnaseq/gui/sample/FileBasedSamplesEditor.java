package org.sing_group.rnaseq.gui.sample;

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
	private List<FileBasedSampleEditorComponent> samples = new LinkedList<>();

	/**
	 * Creates a new {@code FileBasedSamplesEditor}.
	 */
	public FileBasedSamplesEditor() {
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
		FileBasedSampleEditorComponent editor = 
			new FileBasedSampleEditorComponent(getFileBasedSampleEditor());
		editor.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		this.add(editor);
		samples.add(editor);
		sampleAdded();
		this.updateUI();
	}

	protected abstract FileBasedSampleEditor<E> getFileBasedSampleEditor();

	class FileBasedSampleEditorComponent extends JPanel 
		implements SampleEditorListener 
	{
		private static final long serialVersionUID = 1L;

		private FileBasedSampleEditor<E> editor;

		public FileBasedSampleEditorComponent(FileBasedSampleEditor<E> editor) {
			this.editor = editor;
			this.editor.addSampleEditorListener(this);
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
			sampleEdited();
		}
	}
	
	private void removeEditorComponent(FileBasedSampleEditorComponent component) {
		SwingUtilities.invokeLater(() -> {
			this.samples.remove(component);
			this.remove(component);
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
		return 	!this.samples.stream()
				.map(FileBasedSampleEditorComponent::isValidValue)
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
