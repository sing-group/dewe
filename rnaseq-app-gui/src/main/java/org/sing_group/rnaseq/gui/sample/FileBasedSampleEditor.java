package org.sing_group.rnaseq.gui.sample;

import java.awt.Component;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.sing_group.rnaseq.api.entities.FileBasedSample;
import org.sing_group.rnaseq.gui.sample.listener.SampleEditorListener;
import org.sing_group.rnaseq.gui.util.CommonFileChooser;

import org.sing_group.gc4s.filechooser.JFileChooserPanel;
import org.sing_group.gc4s.filechooser.JFileChooserPanel.Mode;
import org.sing_group.gc4s.filechooser.JFileChooserPanel.SelectionMode;

public abstract class FileBasedSampleEditor<T extends FileBasedSample> extends JPanel {
	private static final long serialVersionUID = 1L;
	private JFileChooserPanel fileChooser;
	private JTextField sampleNameTf;
	private JTextField sampleTypeTf;

	public FileBasedSampleEditor() {
		this.init();
	}

	private void init() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(getFileChooserPanel());
		this.add(getInfoPanel());
	}

	private Component getFileChooserPanel() {
		this.fileChooser = new JFileChooserPanel(
			Mode.OPEN, getSelectionMode(), 
			CommonFileChooser.getInstance().getSingleFilechooser()
		);
		this.fileChooser.addFileChooserListener(this::fileChooserListener);

		return this.fileChooser;
	}

	protected abstract SelectionMode getSelectionMode();

	private void fileChooserListener(ChangeEvent e) {
		this.sampleEdited();
	}

	private void sampleEdited() {
		for (SampleEditorListener l : getSampleEditorListeners()) {
			l.onSampleEdited(new ChangeEvent(this));
		}
	}

	private JPanel getInfoPanel() {
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));

		MyDocumentListener documentListener = new MyDocumentListener();
		infoPanel.add(new JLabel("Name: "));
		sampleNameTf = new JTextField(10);
		sampleNameTf.getDocument().addDocumentListener(documentListener);
		infoPanel.add(sampleNameTf);
		
		infoPanel.add(Box.createHorizontalStrut(5));
		infoPanel.add(new JLabel("Type: "));
		sampleTypeTf = new JTextField(10);
		sampleTypeTf.getDocument().addDocumentListener(documentListener);
		infoPanel.add(sampleTypeTf);

		return infoPanel;
	}
	
	private class MyDocumentListener implements DocumentListener {
		
		@Override
		public void removeUpdate(DocumentEvent e) {
			sampleEdited();
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			sampleEdited();
		}
		
		@Override
		public void changedUpdate(DocumentEvent e) {
		}
	};
	
	public abstract T getSample();
	
	public boolean isValidValue() {
		return this.fileChooser.getSelectedFile() != null &&
				isValidString(this.sampleNameTf.getText()) &&
				isValidString(this.sampleTypeTf.getText());
	}

	private boolean isValidString(String text) {
		return text != null && !text.isEmpty();
	}
	
	protected File getSelectedFile() {
		return this.fileChooser.getSelectedFile();
	}

	protected String getSampleType() {
		return this.sampleTypeTf.getText();
	}

	protected String getSampleName() {
		return this.sampleNameTf.getText();
	}
	
	public synchronized void addSampleEditorListener(SampleEditorListener l) {
		this.listenerList.add(SampleEditorListener.class, l);
	}

	public synchronized SampleEditorListener[] getSampleEditorListeners() {
		return this.listenerList.getListeners(SampleEditorListener.class);
	}
}
