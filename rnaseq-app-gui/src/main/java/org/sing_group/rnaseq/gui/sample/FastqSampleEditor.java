package org.sing_group.rnaseq.gui.sample;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.filechooser.FileFilter;

import org.sing_group.rnaseq.core.entities.DefaultFastqReadsSample;
import org.sing_group.rnaseq.gui.sample.listener.SampleEditorListener;
import org.sing_group.rnaseq.gui.util.CommonFileChooser;

import es.uvigo.ei.sing.hlfernandez.event.DocumentAdapter;
import es.uvigo.ei.sing.hlfernandez.filechooser.ExtensionFileFilter;
import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel;
import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanel.SelectionMode;
import es.uvigo.ei.sing.hlfernandez.filechooser.JFileChooserPanelBuilder;
import es.uvigo.ei.sing.hlfernandez.input.InputParameter;
import es.uvigo.ei.sing.hlfernandez.input.InputParametersPanel;

public class FastqSampleEditor extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final String NO_CONDITION = "<Select a condition>";

	private List<String> selectableConditions;
	private InputParametersPanel inputParametersPanel = new InputParametersPanel();
	private DefaultComboBoxModel<String> selectableConditionsModel;
	private JTextField sampleNameTextField;
	private JFileChooserPanel reads1FileChooser;
	private JFileChooserPanel reads2FileChooser;

	public FastqSampleEditor() {
		this(Collections.emptyList());
	}

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
		reads1FileChooser.addFileChooserListener(this::readsFileChanged);
		reads1FileChooser.setOpaque(false);

		return new InputParameter("Reads 1", 
			reads1FileChooser, "The reads file 1");
	}

	private List<FileFilter> getFastqFileFilters() {
		return Arrays.asList(
			new ExtensionFileFilter(".*\\.fq", "FASTQ (.fq) format files"),
			new ExtensionFileFilter(".*\\.fastq", "FASTQ (.fastq) format files")
		);
	}

	private InputParameter getReadsFile2Parameter() {
		reads2FileChooser =  JFileChooserPanelBuilder.createOpenJFileChooserPanel()
				.withFileChooser(
					CommonFileChooser.getInstance().getSingleFilechooser()
				)
				.withFileFilters(getFastqFileFilters())
				.withLabel("").withFileChooserSelectionMode(SelectionMode.FILES)
			.build();
		reads2FileChooser.addFileChooserListener(this::readsFileChanged);
		reads2FileChooser.setOpaque(false);

		return new InputParameter("Reads 2", 
			reads2FileChooser, "The reads file 2");
	}
	
	private void readsFileChanged(ChangeEvent e) {
		this.sampleEdited();
	}

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
	
	public boolean isValidReadsFile1() {
		return isValidFile(reads1FileChooser.getSelectedFile());
	}

	public boolean isValidReadsFile2() {
		return isValidFile(reads2FileChooser.getSelectedFile());
	}

	private boolean isValidFile(File selectedFile) {
		return selectedFile != null && selectedFile.exists() && 
			(	selectedFile.getName().endsWith(".fq") || 
				selectedFile.getName().endsWith(".fastq")
			);
	}

	public DefaultFastqReadsSample getSample() {
		return 	new DefaultFastqReadsSample(
					this.sampleNameTextField.getText(),
					this.selectableConditionsModel.getSelectedItem().toString(), 
					reads1FileChooser.getSelectedFile(), 
					reads2FileChooser.getSelectedFile()
				);
	}

	private void sampleEdited() {
		for (SampleEditorListener l : getdSampleEditorListeners()) {
			l.onSampleEdited(new ChangeEvent(this));
		}
	}

	public synchronized void addSampleEditorListener(SampleEditorListener l) {
		this.listenerList.add(SampleEditorListener.class, l);
	}

	public synchronized SampleEditorListener[] getdSampleEditorListeners() {
		return this.listenerList.getListeners(SampleEditorListener.class);
	}
}
