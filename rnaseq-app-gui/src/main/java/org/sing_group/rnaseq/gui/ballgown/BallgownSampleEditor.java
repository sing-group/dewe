package org.sing_group.rnaseq.gui.ballgown;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.sing_group.rnaseq.api.entities.ballgown.BallgownSample;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownSample;
import org.sing_group.rnaseq.gui.ballgown.listener.BallgownSampleEditorListener;
import org.sing_group.rnaseq.gui.util.CommonFileChooser;

import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel;
import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel.Mode;
import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel.SelectionMode;

public class BallgownSampleEditor extends JPanel {
	private static final long serialVersionUID = 1L;
	private JFileChooserPanel fileChooser;
	private JTextField sampleNameTf;
	private JTextField sampleTypeTf;

	public BallgownSampleEditor() {
		this.init();
	}

	private void init() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(getFileChooserPanel());
		this.add(getInfoPanel());
	}

	private Component getFileChooserPanel() {
		this.fileChooser = new JFileChooserPanel(
			Mode.OPEN, SelectionMode.DIRECTORIES, 
			CommonFileChooser.getInstance().getSingleFilechooser()
		);
		this.fileChooser.addFileChooserListener(this::fileChooserListener);

		return this.fileChooser;
	}

	private void fileChooserListener(ChangeEvent e) {
		this.sampleEdited();
	}

	private void sampleEdited() {
		for (BallgownSampleEditorListener l : getBallgownSampleEditorListeners()) {
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
	
	public BallgownSample getSample() {
		return new DefaultBallgownSample(
			this.sampleNameTf.getText(), this.sampleTypeTf.getText(), 
			this.fileChooser.getSelectedFile()
		);
	}
	
	public boolean isValidValue() {
		return this.fileChooser.getSelectedFile() != null &&
				isValidString(this.sampleNameTf.getText()) &&
				isValidString(this.sampleTypeTf.getText());
	}

	private boolean isValidString(String text) {
		return text != null && !text.isEmpty();
	}
	
	public synchronized void addBallgownSampleEditorListener(BallgownSampleEditorListener l) {
		this.listenerList.add(BallgownSampleEditorListener.class, l);
	}

	public synchronized BallgownSampleEditorListener[] getBallgownSampleEditorListeners() {
		return this.listenerList.getListeners(BallgownSampleEditorListener.class);
	}
}
