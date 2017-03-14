package org.sing_group.rnaseq.gui.components.wizard.steps;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXLabel;
import org.sing_group.rnaseq.api.persistence.ReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenome;

import es.uvigo.ei.sing.hlfernandez.combobox.ComboBoxItem;
import es.uvigo.ei.sing.hlfernandez.ui.CenteredJPanel;
import es.uvigo.ei.sing.hlfernandez.wizard.WizardStep;

public abstract class ReferenceGenomeSelectionStep<T extends ReferenceGenome> extends WizardStep {
	
	private Class<T> referenceGenomeClass;
	private ReferenceGenomeDatabaseManager databaseManager;
	
	private JPanel stepComponent;
	private JComboBox<ComboBoxItem<ReferenceGenome>> combobox;

	public ReferenceGenomeSelectionStep(
		ReferenceGenomeDatabaseManager databaseManager,
		Class<T> referenceGenomeClass
	) {
		this.databaseManager = databaseManager;
		this.referenceGenomeClass = referenceGenomeClass;
	}
	
	@Override
	public String getStepTitle() {
		return "Reference genome selection";
	}

	@Override
	public JComponent getStepComponent() {
		if(stepComponent == null) {
			this.stepComponent = new JPanel();
			this.stepComponent.setLayout(new BorderLayout());
			this.stepComponent.add(getLabel(), BorderLayout.NORTH);
			this.stepComponent.add(getComboBox(), BorderLayout.CENTER);
		}
		return new CenteredJPanel(this.stepComponent);
	}

	private Component getLabel() {
		JXLabel label = new JXLabel(
			"Select a " + getReferenceGenomeType() + " reference genome:");
		label.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
		label.setLineWrap(true);
		label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		return label;
	}

	protected abstract String getReferenceGenomeType();

	private Component getComboBox() {
		if(combobox == null) {
			combobox = new JComboBox<>();
		}
		updateComboboxItems();
		return combobox;
	}

	private void updateComboboxItems() {
		this.combobox.removeAllItems();

		for (ReferenceGenome r : this.databaseManager
			.listReferenceGenomes(referenceGenomeClass)
		) {
			this.combobox.addItem(
				new ComboBoxItem<ReferenceGenome>(
					r, 
					r.getReferenceGenome().getAbsolutePath()
				)
			);
		}
	}

	@Override
	public boolean isStepCompleted() {
		return this.combobox.getSelectedIndex() != -1;
	}

	public T getSelectedReferenceGenome() {
		ComboBoxItem<?> selectedItem = 
			(ComboBoxItem<?>) this.combobox.getSelectedItem();

		return referenceGenomeClass.cast(selectedItem.getItem());
	}
}
