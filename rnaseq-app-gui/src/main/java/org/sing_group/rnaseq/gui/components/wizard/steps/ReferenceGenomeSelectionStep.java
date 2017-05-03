package org.sing_group.rnaseq.gui.components.wizard.steps;

import static org.sing_group.rnaseq.gui.components.wizard.steps.StepUtils.configureStepComponent;

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
import org.sing_group.rnaseq.api.persistence.entities.event.ReferenceGenomeDatabaseListener;

import org.sing_group.gc4s.combobox.ComboBoxItem;
import org.sing_group.gc4s.ui.CenteredJPanel;
import org.sing_group.gc4s.wizard.WizardStep;

/**
 * An abstract component that allows the selection of a {@code ReferenceGenome}
 * from a combobox.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 * @param <T> the class of the {@code ReferenceGenome} that can be selected
 */
public abstract class ReferenceGenomeSelectionStep<T extends ReferenceGenome>
	extends WizardStep implements ReferenceGenomeDatabaseListener {
	
	private Class<T> referenceGenomeClass;
	private ReferenceGenomeDatabaseManager databaseManager;
	
	private JPanel stepComponent;
	private JComboBox<ComboBoxItem<ReferenceGenome>> combobox;

	/**
	 * Creates a new {@code ReferenceGenomeSelectionStep} using the specified
	 * {@code databaseManager} for selecting reference genomes from the
	 * specified class.
	 * 
	 * @param databaseManager the {@code ReferenceGenomeDatabaseManager}
	 * @param referenceGenomeClass the class of the reference genomes
	 */
	public ReferenceGenomeSelectionStep(
		ReferenceGenomeDatabaseManager databaseManager,
		Class<T> referenceGenomeClass
	) {
		this.databaseManager = databaseManager;
		this.databaseManager.addReferenceGenomeDatabaseListener(this);
		this.referenceGenomeClass = referenceGenomeClass;
	}
	
	@Override
	public String getStepTitle() {
		return "Reference genome selection";
	}

	@Override
	public JComponent getStepComponent() {
		if(this.stepComponent == null) {
			this.stepComponent = new CenteredJPanel(getSelectionPanel());
			configureStepComponent(this.stepComponent);
		}
		return this.stepComponent;
	}

	/**
	 * Returns the panel that contains the selection combobox.
	 * 
	 * @return the panel that contains the selection combobox
	 */
	protected JComponent getSelectionPanel() {
		JPanel selectionPanel = new JPanel();
		selectionPanel.setOpaque(false);
		selectionPanel.setLayout(new BorderLayout());
		selectionPanel.add(getLabel(), BorderLayout.NORTH);
		selectionPanel.add(getComboBox(), BorderLayout.CENTER);

		return selectionPanel;
	}

	/**
	 * Returns the label that goes with the selection combobox.
	 * 
	 * @return the label that goes with the selection combobox
	 */
	protected Component getLabel() {
		JXLabel label = new JXLabel(
			"Select a " + getReferenceGenomeType() + " reference genome:");
		label.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
		label.setLineWrap(true);
		label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

		return label;
	}

	/**
	 * Returns a string indicating the reference genome type.
	 * 
	 * @return a string indicating the reference genome type
	 */
	protected abstract String getReferenceGenomeType();

	private Component getComboBox() {
		if(combobox == null) {
			combobox = new JComboBox<>();
		}
		updateComboboxItems();
		return combobox;
	}

	protected void updateComboboxItems() {
		this.combobox.removeAllItems();

		for (ReferenceGenome r : this.databaseManager
			.listReferenceGenomes(referenceGenomeClass)
		) {
			this.combobox.addItem(
				new ComboBoxItem<ReferenceGenome>(r, r.getName())
			);
		}
	}

	@Override
	public boolean isStepCompleted() {
		return this.combobox.getSelectedIndex() != -1;
	}

	/**
	 * Returns the selected reference genome.
	 * 
	 * @return the selected reference genome
	 */
	public T getSelectedReferenceGenome() {
		ComboBoxItem<?> selectedItem = 
			(ComboBoxItem<?>) this.combobox.getSelectedItem();

		return referenceGenomeClass.cast(selectedItem.getItem());
	}

	public void referenceGenomeAdded() {
		updateComboboxItems();
	}

	public void referenceGenomeRemoved() {
		updateComboboxItems();
	}
}
