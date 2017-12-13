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

import org.sing_group.gc4s.input.filechooser.JFileChooserPanel;
import org.sing_group.gc4s.input.filechooser.Mode;
import org.sing_group.gc4s.input.filechooser.SelectionMode;
import org.sing_group.rnaseq.api.entities.FileBasedSample;
import org.sing_group.rnaseq.gui.sample.listener.SampleEditorListener;
import org.sing_group.rnaseq.gui.util.CommonFileChooser;

/**
 * An abstract, generic component that allows the introduction of a
 * {@code FileBasedSample}.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 * @param <T>
 *            the type of the sample
 */
public abstract class FileBasedSampleEditor<T extends FileBasedSample> extends JPanel {
	private static final long serialVersionUID = 1L;
	private JFileChooserPanel fileChooser;
	private JTextField sampleNameTf;
	private JTextField sampleTypeTf;

	/**
	 * Creates a new {@code FileBasedSampleEditor} component.
	 */
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
	
	
	/**
	 * Returns the selected sample.
	 * 
	 * @return the selected sample
	 */
	public abstract T getSample();

	/**
	 * Returns {@code true} if the current selection is valid and {@code false}
	 * otherwise.
	 * 
	 * @return {@code true} if the current selection is valid and {@code false}
	 *         otherwise
	 */	
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
	
	/**
	 * Adds the specified {@code SampleEditorListener} to the listeners list.
	 *
	 * @param l a {@code SampleEditorListener}
	 */
	public synchronized void addSampleEditorListener(SampleEditorListener l) {
		this.listenerList.add(SampleEditorListener.class, l);
	}

	/**
	 * Returns the list of all registered {@code SampleEditorListener}s.
	 *
	 * @return the list of all registered {@code SampleEditorListener}s
	 */
	public synchronized SampleEditorListener[] getSampleEditorListeners() {
		return this.listenerList.getListeners(SampleEditorListener.class);
	}
}
