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
package org.sing_group.rnaseq.gui.components.configuration;

import static java.util.Arrays.asList;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.io.InvalidClassException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.sing_group.gc4s.event.DocumentAdapter;
import org.sing_group.gc4s.input.InputParameter;
import org.sing_group.gc4s.input.InputParametersPanel;
import org.sing_group.gc4s.input.list.ExtendedDefaultListModel;
import org.sing_group.gc4s.input.list.JListPanel;
import org.sing_group.gc4s.input.list.JParallelListsPanel;
import org.sing_group.gc4s.input.text.JIntegerTextField;
import org.sing_group.gc4s.ui.text.MultilineLabel;
import org.sing_group.rnaseq.api.environment.execution.parameters.trimmomatic.TrimmomaticParameter;
import org.sing_group.rnaseq.core.environment.execution.parameters.trimmomatic.ConvertQualityParameter;
import org.sing_group.rnaseq.core.environment.execution.parameters.trimmomatic.ConvertQualityParameter.Quality;
import org.sing_group.rnaseq.core.environment.execution.parameters.trimmomatic.CropParameter;
import org.sing_group.rnaseq.core.environment.execution.parameters.trimmomatic.HeadCropParameter;
import org.sing_group.rnaseq.core.environment.execution.parameters.trimmomatic.IlluminaClipParameter;
import org.sing_group.rnaseq.core.environment.execution.parameters.trimmomatic.IlluminaClipParameter.FastaAdapter;
import org.sing_group.rnaseq.core.environment.execution.parameters.trimmomatic.LeadingParameter;
import org.sing_group.rnaseq.core.environment.execution.parameters.trimmomatic.MaxInfoParameter;
import org.sing_group.rnaseq.core.environment.execution.parameters.trimmomatic.MinLenParameter;
import org.sing_group.rnaseq.core.environment.execution.parameters.trimmomatic.SlidingWindowParameter;
import org.sing_group.rnaseq.core.environment.execution.parameters.trimmomatic.TrailingParameter;

public class TrimmomaticParametersPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final String PROPERTY_CONFIGURATION = "trimmomatic.configuration";
	
	private enum Step
	{
		MAXINFO, LEADING, TRAILING, CROP, HEADCROP, 
		MINLEN, SLIDING_WINDOW, QUALITY, ILLUMINA_CLIP
	};

	private JIntegerTextField leadingTextField;
	private JIntegerTextField trailingTextField;
	private JIntegerTextField cropTextField;
	private JIntegerTextField headCropTextField;
	private JIntegerTextField minLenTextField;
	private JIntegerTextField slidingWindowSizeTextField;
	private JIntegerTextField slidingWindowQualityTextField;
	private JIntegerTextField maxInfoLengthTextField;
	private JIntegerTextField maxInfoStrictnessTextField;
	private JComboBox<ConvertQualityParameter.Quality> qualityComboBox;
	private JList<Step> selectedStepsList;
	private JComboBox<FastaAdapter> illuminaAdapterComboBox;
	private JIntegerTextField illuminaSeedMismatchesTextField;
	private JIntegerTextField illuminaPalindromeClipThresholdTextField;
	private JIntegerTextField illuminaSimpleClipThresholdTextField;
	
	private final DocumentListener textFieldListener = new DocumentAdapter() {
		
		@Override
		public void removeUpdate(DocumentEvent e) {
			configurationChanged();
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			configurationChanged();
		}
	};

	public TrimmomaticParametersPanel() {
		this.init();
	}

	private void init() {
		this.add(getParametersPanel());
	}

	private Component getParametersPanel() {
		JTabbedPane parametersPanel = new JTabbedPane();
		
		parametersPanel.addTab("Steps", getStepsPanel());
		parametersPanel.addTab("Leading", getLeadingParameterPanel());
		parametersPanel.addTab("Trailing", getTrailingParameterPanel());
		parametersPanel.addTab("Crop", getCropParameterPanel());
		parametersPanel.addTab("Headcrop", getHeadCropParameterPanel());
		parametersPanel.addTab("Minlen", getMinLenParameterPanel());
		parametersPanel.addTab("Sliding Window", getSlidingWindowParameterPanel());
		parametersPanel.addTab("Max info", getMaxInfoParameterPanel());
		parametersPanel.addTab("Quality", getQualityParameterPanel());
		parametersPanel.addTab("Illumina Clip", getIlluminaClipParameterPanel());
		
		return parametersPanel;
	}

	private Component getLeadingParameterPanel() {
		return createParametersPanel(
			createLabel(LeadingParameter.DESCRIPTION), 
			new InputParametersPanel(getLeadingParameter())
		);
	}

	private Component createLabel(String description) {
		MultilineLabel label = new MultilineLabel(description);
		label.setOpaque(false);
		label.setBackground(UIManager.getColor("Panel.background"));

		JScrollPane pane = new JScrollPane();
		pane.setViewportView(label);
		label.setBorder(BorderFactory.createTitledBorder("Parameter description"));

		return pane;
	}

	private Component createParametersPanel(Component... components) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createVerticalGlue());
		for (Component c : components) {
			panel.add(c);
		}
		panel.add(Box.createVerticalGlue());

		return panel;
	}

	private InputParameter getLeadingParameter() {
		this.leadingTextField = new JIntegerTextField(0);
		this.leadingTextField.setColumns(5);
		addDocumentListener(this.leadingTextField);

		return new InputParameter(
			"Quality", this.leadingTextField, LeadingParameter.DESCRIPTION_QUALITY);
	}
	
	private void addDocumentListener(JIntegerTextField field) {
		field.getDocument().addDocumentListener(textFieldListener);
	}

	private Component getTrailingParameterPanel() {
		return createParametersPanel(
			createLabel(TrailingParameter.DESCRIPTION), 
			new InputParametersPanel(getTrailingParameter())
		);
	}
	
	private InputParameter getTrailingParameter() {
		this.trailingTextField = new JIntegerTextField(0);
		this.trailingTextField.setColumns(5);
		addDocumentListener(this.trailingTextField);
		
		return new InputParameter(
			"Quality", this.trailingTextField, TrailingParameter.DESCRIPTION_QUALITY);
	}
	
	private Component getCropParameterPanel() {
		return createParametersPanel(
			createLabel(CropParameter.DESCRIPTION), 
			new InputParametersPanel(getCropParameter())
		);
	}
	
	private InputParameter getCropParameter() {
		this.cropTextField = new JIntegerTextField(0);
		this.cropTextField.setColumns(5);
		addDocumentListener(this.cropTextField);
		
		return new InputParameter(
			"Length", this.cropTextField, CropParameter.DESCRIPTION_LENGTH);
	}
	
	private Component getHeadCropParameterPanel() {
		return createParametersPanel(
			createLabel(HeadCropParameter.DESCRIPTION), 
			new InputParametersPanel(getHeadCropParameter())
		);
	}
	
	private InputParameter getHeadCropParameter() {
		this.headCropTextField = new JIntegerTextField(0);
		this.headCropTextField.setColumns(5);
		addDocumentListener(this.headCropTextField);
		
		return new InputParameter(
			"Length", this.headCropTextField, HeadCropParameter.DESCRIPTION_LENGTH);
	}
	
	private Component getMinLenParameterPanel() {
		return createParametersPanel(
			createLabel(MinLenParameter.DESCRIPTION), 
			new InputParametersPanel(getMinLenParameter())
		);
	}
	
	private InputParameter getMinLenParameter() {
		this.minLenTextField = new JIntegerTextField(0);
		this.minLenTextField.setColumns(5);
		addDocumentListener(this.minLenTextField);
		
		return new InputParameter(
			"Length", this.minLenTextField, MinLenParameter.DESCRIPTION_LENGTH);
	}
	
	private Component getSlidingWindowParameterPanel() {
		return createParametersPanel(
			createLabel(SlidingWindowParameter.DESCRIPTION), 
			new InputParametersPanel(getSlidingWindowParameters())
		);
	}
	
	private InputParameter[] getSlidingWindowParameters() {
		InputParameter[] parameters = new InputParameter[2];
		parameters[0] = getSlidingWindowSizeParameter();
		parameters[1] = getSlidingWindowQualityParameter();
		
		return parameters;
	}

	private InputParameter getSlidingWindowSizeParameter() {
		this.slidingWindowSizeTextField = new JIntegerTextField(0);
		this.slidingWindowSizeTextField.setColumns(5);
		addDocumentListener(this.slidingWindowSizeTextField);
		
		return new InputParameter(
			"Window size", 
			this.slidingWindowSizeTextField, 
			SlidingWindowParameter.DESCRIPTION_WINDOW_SIZE
		);
	}
	
	private InputParameter getSlidingWindowQualityParameter() {
		this.slidingWindowQualityTextField = new JIntegerTextField(0);
		this.slidingWindowQualityTextField.setColumns(5);
		addDocumentListener(this.slidingWindowQualityTextField);
		
		return new InputParameter(
			"Required quality", 
			this.slidingWindowQualityTextField, 
			SlidingWindowParameter.DESCRIPTION_REQUIRED_QUALITY
		);
	}
	
	private Component getMaxInfoParameterPanel() {
		return createParametersPanel(
			createLabel(MaxInfoParameter.DESCRIPTION), 
			new InputParametersPanel(getMaxInfoParameters())
		);
	}
	
	private InputParameter[] getMaxInfoParameters() {
		InputParameter[] parameters = new InputParameter[2];
		parameters[0] = getMaxInfoLengthParameter();
		parameters[1] = getMaxInfoStrictnessParameter();
		
		return parameters;
	}
	
	private InputParameter getMaxInfoLengthParameter() {
		this.maxInfoLengthTextField = new JIntegerTextField(0);
		this.maxInfoLengthTextField.setColumns(5);
		addDocumentListener(this.maxInfoLengthTextField);
		
		return new InputParameter(
			"Target length", 
			this.maxInfoLengthTextField, 
			MaxInfoParameter.DESCRIPTION_TARGET_LENGTH
		);
	}
	
	private InputParameter getMaxInfoStrictnessParameter() {
		this.maxInfoStrictnessTextField = new JIntegerTextField(0);
		this.maxInfoStrictnessTextField.setColumns(5);
		addDocumentListener(this.maxInfoStrictnessTextField);
		
		return new InputParameter(
			"Strictness", 
			this.maxInfoStrictnessTextField, 
			MaxInfoParameter.DESCRIPTION_STRICTNESS
		);
	}
	
	private Component getIlluminaClipParameterPanel() {
		return createParametersPanel(
			createLabel(IlluminaClipParameter.DESCRIPTION), 
			new InputParametersPanel(getIlluminaClipParameters())
		);
	}
	
	
	private InputParameter[] getIlluminaClipParameters() {
		InputParameter[] parameters = new InputParameter[4];
		parameters[0] = getIlluminaFastaAdapterParameter();
		parameters[1] = getIlluminaSeedMismatchesParameter();
		parameters[2] = getIlluminaPalindromeClipThresholdParameter();
		parameters[3] = getIlluminaSimpleClipThresholdParameter();
		
		return parameters;
	}
	
	private InputParameter getIlluminaFastaAdapterParameter() {
		this.illuminaAdapterComboBox = new JComboBox<>(FastaAdapter.values());
		this.illuminaAdapterComboBox.addItemListener(this::illuminaAdapterComboBoxChanged);

		return new InputParameter(
			"Quality", 
			this.illuminaAdapterComboBox, 
			IlluminaClipParameter.DESCRIPTION_ADAPTER
		);
	}
	
	private void illuminaAdapterComboBoxChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			this.configurationChanged();
		}
	}
	
	private InputParameter getIlluminaSeedMismatchesParameter() {
		this.illuminaSeedMismatchesTextField = new JIntegerTextField(0);
		this.illuminaSeedMismatchesTextField.setColumns(5);
		addDocumentListener(this.illuminaSeedMismatchesTextField);
		
		return new InputParameter("Seed mismatches", 
			this.illuminaSeedMismatchesTextField, 
			IlluminaClipParameter.DESCRIPTION_SEED_MISMATCHES);
	}
	
	private InputParameter getIlluminaPalindromeClipThresholdParameter() {
		this.illuminaPalindromeClipThresholdTextField = new JIntegerTextField(0);
		this.illuminaPalindromeClipThresholdTextField.setColumns(5);
		addDocumentListener(this.illuminaPalindromeClipThresholdTextField);
		
		return new InputParameter("Palindrome clip threshold", 
			this.illuminaPalindromeClipThresholdTextField, 
			IlluminaClipParameter.DESCRIPTION_PALINDROME_THRESHOLD);
	}
	
	private InputParameter getIlluminaSimpleClipThresholdParameter() {
		this.illuminaSimpleClipThresholdTextField = new JIntegerTextField(0);
		this.illuminaSimpleClipThresholdTextField.setColumns(5);
		addDocumentListener(this.illuminaSimpleClipThresholdTextField);
		
		return new InputParameter("Simple clip threshold", 
			this.illuminaSimpleClipThresholdTextField, 
			IlluminaClipParameter.DESCRIPTION_SIMPLE_THRESHOLD);
	}
	
	private Component getQualityParameterPanel() {
		return createParametersPanel(
			createLabel(ConvertQualityParameter.DESCRIPTION), 
			new InputParametersPanel(getConvertQualityParameter())
		);
	}
	
	private InputParameter getConvertQualityParameter() {
		this.qualityComboBox = new JComboBox<>(Quality.values());
		this.qualityComboBox.addItemListener(this::qualityComboboxChanged);

		return new InputParameter("Quality", 
			this.qualityComboBox, ConvertQualityParameter.DESCRIPTION_TYPE);
	}
	
	private void qualityComboboxChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			this.configurationChanged();
		}
	}

	private Component getStepsPanel() {
		JParallelListsPanel<Step> parallelLists;
		try {
			parallelLists = new JParallelListsPanel<>(
				createLeftList(), createRightList(), 
				"Unselected", "Selected", true, false
			);
			JListPanel<Step> leftList = parallelLists.getLeftListPanel();
			JListPanel<Step> rightList = parallelLists.getRightListPanel();

			leftList.getList().setPrototypeCellValue(Step.SLIDING_WINDOW);
			rightList.getList().setPrototypeCellValue(Step.SLIDING_WINDOW);

			leftList.getBtnClearSelection().setVisible(false);
			rightList.getBtnClearSelection().setVisible(false);
			leftList.getBtnSelectAll().setVisible(false);
			rightList.getBtnSelectAll().setVisible(false);
			leftList.getBtnRemoveElements().setVisible(false);
			rightList.getBtnRemoveElements().setVisible(false);

		} catch (InvalidClassException e) {
			throw new RuntimeException();
		}
		return parallelLists;
	}
	
	private JList<Step> createLeftList() {
		return createList(asList(Step.values()));
	}

	private JList<Step> createRightList() {
		this.selectedStepsList = createList(asList());
		this.selectedStepsList.getModel().addListDataListener(
			new ListDataListener() {
			
			@Override
			public void intervalRemoved(ListDataEvent e) {
				selectedStepsChanged();
			}
			
			@Override
			public void intervalAdded(ListDataEvent e) {
				selectedStepsChanged();
			}
			
			@Override
			public void contentsChanged(ListDataEvent e) {
				selectedStepsChanged();
			}
		});
		
		return this.selectedStepsList;
	}

	private static JList<Step> createList(List<Step> data) {
		ExtendedDefaultListModel<Step> listModel = 
			new ExtendedDefaultListModel<Step>();
		listModel.addElements(data);

		return new JList<Step>(listModel);
	}
	
	private void selectedStepsChanged() {
		this.firePropertyChange(PROPERTY_CONFIGURATION, null, null);
	}

	private void configurationChanged() {
		this.firePropertyChange(PROPERTY_CONFIGURATION, null, null);
	}

	public TrimmomaticParameter[] getParameters() {
		List<TrimmomaticParameter> parameters = new LinkedList<>();
		ListModel<Step> model = this.selectedStepsList.getModel();
		for(int i = 0; i < model.getSize(); i++) {
			Step step = model.getElementAt(i);
			switch (step) {
			case CROP:
				parameters.add(new CropParameter(this.cropTextField.getValue()));
				break;
			case HEADCROP:
				parameters.add(new HeadCropParameter(this.headCropTextField.getValue()));
				break;
			case LEADING:
				parameters.add(new LeadingParameter(this.leadingTextField.getValue()));
				break;
			case MAXINFO:
				parameters.add(new MaxInfoParameter(
					this.maxInfoLengthTextField.getValue(), 
					this.maxInfoStrictnessTextField.getValue()
				));
				break;
			case MINLEN:
				parameters.add(new MinLenParameter(this.minLenTextField.getValue()));
				break;
			case TRAILING:
				parameters.add(new TrailingParameter(this.trailingTextField.getValue()));
				break;
			case SLIDING_WINDOW:
				parameters.add(new SlidingWindowParameter(
					this.slidingWindowSizeTextField.getValue(), 
					this.slidingWindowQualityTextField.getValue()
				));
				break;
			case QUALITY:
				parameters.add(new ConvertQualityParameter(
					(Quality) this.qualityComboBox.getSelectedItem())
				);
				break;
			case ILLUMINA_CLIP:
				parameters.add(new IlluminaClipParameter(
					(FastaAdapter) this.illuminaAdapterComboBox.getSelectedItem(), 
					this.illuminaSeedMismatchesTextField.getValue(), 
					this.illuminaPalindromeClipThresholdTextField.getValue(), 
					this.illuminaSimpleClipThresholdTextField.getValue())
				);
				break;
			}
		}
		
		return parameters.toArray(new TrimmomaticParameter[parameters.size()]);
	}
	
	public boolean isValidConfiguration() {
		return getParameters().length > 0;
	}
}
