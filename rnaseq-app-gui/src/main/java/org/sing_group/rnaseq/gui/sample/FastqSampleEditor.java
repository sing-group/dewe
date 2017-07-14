package org.sing_group.rnaseq.gui.sample;

import static org.sing_group.rnaseq.core.io.samples.ImportPairedSamplesDirectory.FASTQ_EXTENSIONS;
import static org.sing_group.rnaseq.core.io.samples.ImportPairedSamplesDirectory.lookForReadsFile2;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.filechooser.FileFilter;

import org.sing_group.gc4s.event.DocumentAdapter;
import org.sing_group.gc4s.filechooser.ExtensionFileFilter;
import org.sing_group.gc4s.filechooser.JFileChooserPanel;
import org.sing_group.gc4s.filechooser.JFileChooserPanel.SelectionMode;
import org.sing_group.gc4s.filechooser.JFileChooserPanelBuilder;
import org.sing_group.gc4s.input.InputParameter;
import org.sing_group.gc4s.input.InputParametersPanel;
import org.sing_group.rnaseq.api.entities.FastqReadsSample;
import org.sing_group.rnaseq.core.entities.DefaultFastqReadsSample;
import org.sing_group.rnaseq.gui.sample.listener.SampleEditorListener;
import org.sing_group.rnaseq.gui.util.CommonFileChooser;

/**
 * A graphical component that allows the edition of {@code FastqReadsSample}
 * objects.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class FastqSampleEditor extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final String NO_CONDITION = "<Select a condition>";

	private static final List<FileFilter> FASTQ_FILE_FILTERS = Arrays.asList(
		new ExtensionFileFilter(".*\\" + FASTQ_EXTENSIONS[0],
			"FASTQ (.fq) format files"),
		new ExtensionFileFilter(".*\\" + FASTQ_EXTENSIONS[1],
			"FASTQ (.fastq) format files"),
		new ExtensionFileFilter(".*\\" + FASTQ_EXTENSIONS[2],
			"Compressed FASTQ (.fastq.gz) format files")
	);

	private List<String> selectableConditions;
	private InputParametersPanel inputParametersPanel = new InputParametersPanel();
	private DefaultComboBoxModel<String> selectableConditionsModel;
	private JTextField sampleNameTextField;
	private JFileChooserPanel reads1FileChooser;
	private JFileChooserPanel reads2FileChooser;

	/**
	 * Creates an empty {@code FastqSampleEditor}.
	 */
	public FastqSampleEditor() {
		this(Collections.emptyList());
	}

	/**
	 * Creates an empty {@code FastqSampleEditor} with the specified list of
	 * selectable conditions.
	 *
	 * @param selectableConditions the list of selectable conditions
	 */
	public FastqSampleEditor(List<String> selectableConditions) {
		this.selectableConditions = new LinkedList<>(selectableConditions);
		this.selectableConditions.add(NO_CONDITION);

		this.init();
	}

	private static List<String> defaultSelectableConditions() {
		return new LinkedList<>(Arrays.asList(NO_CONDITION));
	}

	private void init() {
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		this.add(getInputParamtersPanel(), BorderLayout.CENTER);
	}

	private Component getInputParamtersPanel() {
		JFileChooserPanel.clearSharedLastFileFilter();
		this.inputParametersPanel = new InputParametersPanel(getParameters());
		this.inputParametersPanel.setOpaque(false);
		return this.inputParametersPanel;
	}

	private InputParameter[] getParameters() {
		InputParameter[] parameters = new InputParameter[4];
		parameters[0] = getSampleNameParameter();
		parameters[1] = getSampleConditionParameter();
		parameters[2] = getReadsFile1Parameter();
		parameters[3] = getReadsFile2Parameter();
		return parameters;
	}

	private InputParameter getSampleNameParameter() {
		sampleNameTextField = new JTextField();
		sampleNameTextField.getDocument()
			.addDocumentListener(new DocumentAdapter() {
				@Override
				public void insertUpdate(DocumentEvent e) {
					sampleEdited();
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					sampleEdited();
				}
		});
		return new InputParameter(
			"Sample name", sampleNameTextField, "The name of the sample");
	}

	private InputParameter getSampleConditionParameter() {
		selectableConditionsModel = new DefaultComboBoxModel<>();
		this.selectableConditions.forEach(selectableConditionsModel::addElement);
		this.selectableConditionsModel.setSelectedItem(NO_CONDITION);
		JComboBox<String> selectableConditionsCombo =
			new JComboBox<>(selectableConditionsModel);
		selectableConditionsCombo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					sampleEdited();
				}
			}
		});
		return new InputParameter(
			"Condition", selectableConditionsCombo,
			"The experimental condition of the sample"
		);
	}

	private InputParameter getReadsFile1Parameter() {
		reads1FileChooser =  JFileChooserPanelBuilder.createOpenJFileChooserPanel()
				.withFileChooser(
					CommonFileChooser.getInstance().getSingleFilechooser()
				)
				.withFileFilters(getFastqFileFilters())
				.withLabel("").withFileChooserSelectionMode(SelectionMode.FILES)
			.build();
		reads1FileChooser.setUseSharedLastFileFilter(true);
		reads1FileChooser.addFileChooserListener(this::readsFileChanged);
		reads1FileChooser.addFileChooserListener(this::readsFile1Changed);
		reads1FileChooser.setOpaque(false);

		return new InputParameter("Reads 1",
			reads1FileChooser, "The reads file 1");
	}

	private List<FileFilter> getFastqFileFilters() {
		return FASTQ_FILE_FILTERS;
	}

	private InputParameter getReadsFile2Parameter() {
		reads2FileChooser =  JFileChooserPanelBuilder.createOpenJFileChooserPanel()
				.withFileChooser(
					CommonFileChooser.getInstance().getSingleFilechooser()
				)
				.withFileFilters(getFastqFileFilters())
				.withLabel("").withFileChooserSelectionMode(SelectionMode.FILES)
			.build();
		reads2FileChooser.setUseSharedLastFileFilter(true);
		reads2FileChooser.addFileChooserListener(this::readsFileChanged);
		reads2FileChooser.setOpaque(false);

		return new InputParameter("Reads 2",
			reads2FileChooser, "The reads file 2");
	}

	private void readsFileChanged(ChangeEvent e) {
		this.sampleEdited();
	}

	private void readsFile1Changed(ChangeEvent e) {
		if (isValidReadsFile1()) {
			File readsFile1 = reads1FileChooser.getSelectedFile();

			Optional<File> readsFile2 = lookForReadsFile2(readsFile1);
			if (readsFile2.isPresent()) {
				this.reads2FileChooser.setSelectedFile(readsFile2.get());
			}

			setSampleName(readsFile1);
		}
	}

	private void setSampleName(File readsFile1) {
		if(isValidSampleName()) {
			return;
		}

		String fileName = readsFile1.getName();
		if(fileName.contains(".")) {
			String sampleName =
				fileName.substring(0, fileName.indexOf(".")).replace("_1", "");
			this.sampleNameTextField.setText(sampleName);
		}
	}

	/**
	 * Returns the name of a reads file by removing the file extension and the
	 * {@code _1} or {@code _2} markers.
	 *
	 * @param readsFile the reads file
	 * @return a string with the name
	 */
	public static final String extractSampleNameFromReadsFile(File readsFile) {
		String fileName = readsFile.getName();
		if (fileName.contains(".")) {
			return fileName.substring(0, fileName.indexOf("."))
						.replace("_1", "")
						.replace("_2", "");
		} else {
			return fileName;
		}
	}

	/**
	 * Sets the list of selectable conditions.
	 *
	 * @param selectableConditions the list of selectable conditions
	 */
	public void setSelectableConditions(List<String> selectableConditions) {
		Object selectedItem = this.selectableConditionsModel.getSelectedItem();
		this.selectableConditionsModel.removeAllElements();
		this.selectableConditions.clear();

		this.selectableConditions = defaultSelectableConditions();
		this.selectableConditions .addAll(selectableConditions);
		this.selectableConditions.forEach(selectableConditionsModel::addElement);

		if(selectableConditions.contains(selectedItem)) {
			this.selectableConditionsModel.setSelectedItem(selectedItem);
		} else {
			this.selectableConditionsModel.setSelectedItem(NO_CONDITION);
		}
	}

	/**
	 * Returns {@code true} if the current values are valid for creating a new
	 * {@code FastqReadsSample} object and {@code false} otherwise.
	 *
	 * @return {@code true} if the current values are valid for creating a new
	 * {@code FastqReadsSample} object and {@code false} otherwise.
	 */
	public boolean isValidValue() {
		return 	isValidSampleName()	&&
				isValidCondition()	&&
				isValidReadsFile1()	&&
				isValidReadsFile2();
	}

	private boolean isValidSampleName() {
		return !this.sampleNameTextField.getText().isEmpty();
	}

	private boolean isValidCondition() {
		return 	!this.selectableConditionsModel.getSelectedItem()
				.equals(NO_CONDITION);
	}

	private boolean isValidReadsFile1() {
		return isValidFile(reads1FileChooser.getSelectedFile());
	}

	private boolean isValidReadsFile2() {
		return isValidFile(reads2FileChooser.getSelectedFile());
	}

	/**
	 * Returns {@code true} if {@code selectedFile} is a valid file, that is: it
	 * is not null, it exists in the file system and it has a valid reads file
	 * extension. Otherwise, it returns {@code false}
	 *
	 * @param file the file that must be checked
	 * @return {@code true} if the given file is valid and {@code false}
	 *         otherwise
	 */
	public static boolean isValidFile(File file) {
		return 	file != null 	&&
				file.exists()	&&
				hasValidFileExtension(file);
	}

	private static boolean hasValidFileExtension(File file) {
		for (String extension : FASTQ_EXTENSIONS) {
			if (file.getName().endsWith(extension)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a new {@code DefaultFastqReadsSample} created with the current
	 * values of the editor.
	 *
	 * @return a new {@code DefaultFastqReadsSample} created with the current
	 *         values of the editor
	 */
	public DefaultFastqReadsSample getSample() {
		return 	new DefaultFastqReadsSample(
					this.sampleNameTextField.getText(),
					this.selectableConditionsModel.getSelectedItem().toString(),
					reads1FileChooser.getSelectedFile(),
					reads2FileChooser.getSelectedFile()
				);
	}

	/**
	 * Sets a {@code FastqReadsSample} to edit.
	 *
	 * @param sample a {@code FastqReadsSample} to edit.
	 */
	public void setSample(FastqReadsSample sample) {
		this.sampleNameTextField.setText(sample.getName());
		this.selectableConditionsModel.setSelectedItem(sample.getCondition());
		this.reads1FileChooser.setSelectedFile(sample.getReadsFile1());
		this.reads2FileChooser.setSelectedFile(sample.getReadsFile2());
	}

	private void sampleEdited() {
		for (SampleEditorListener l : getdSampleEditorListeners()) {
			l.onSampleEdited(new ChangeEvent(this));
		}
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
	public synchronized SampleEditorListener[] getdSampleEditorListeners() {
		return this.listenerList.getListeners(SampleEditorListener.class);
	}
}
