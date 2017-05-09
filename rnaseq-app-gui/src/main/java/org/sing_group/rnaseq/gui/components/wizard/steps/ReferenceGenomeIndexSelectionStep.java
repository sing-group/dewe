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
import org.sing_group.rnaseq.api.persistence.ReferenceGenomeIndexDatabaseManager;
import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenomeIndex;
import org.sing_group.rnaseq.api.persistence.entities.event.ReferenceGenomeIndexDatabaseListener;

import org.sing_group.gc4s.combobox.ComboBoxItem;
import org.sing_group.gc4s.ui.CenteredJPanel;
import org.sing_group.gc4s.wizard.WizardStep;

/**
 * An abstract component that allows the selection of a
 * {@code ReferenceGenomeIndex} from a combobox.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 * @param <T> the class of the {@code ReferenceGenomeIndex} that can be 
 *        selected
 */
public abstract class ReferenceGenomeIndexSelectionStep<T extends ReferenceGenomeIndex>
	extends WizardStep implements ReferenceGenomeIndexDatabaseListener {
	
	private Class<T> referenceGenomeClass;
	private ReferenceGenomeIndexDatabaseManager databaseManager;
	
	private JPanel stepComponent;
	private JComboBox<ComboBoxItem<ReferenceGenomeIndex>> combobox;

	/**
	 * Creates a new {@code ReferenceGenomeIndexSelectionStep} using the
	 * specified {@code databaseManager} for selecting reference genomes from
	 * the specified class.
	 * 
	 * @param databaseManager the {@code ReferenceGenomeIndexDatabaseManager}
	 * @param referenceGenomeClass the class of the reference genomes
	 */
	public ReferenceGenomeIndexSelectionStep(
		ReferenceGenomeIndexDatabaseManager databaseManager,
		Class<T> referenceGenomeClass
	) {
		this.databaseManager = databaseManager;
		this.databaseManager.addReferenceGenomeIndexDatabaseListener(this);
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
			"Select a " + getReferenceGenomeType() + " reference genome index:");
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

		for (ReferenceGenomeIndex r : this.databaseManager
			.listIndexes(referenceGenomeClass)
		) {
			this.combobox.addItem(
				new ComboBoxItem<ReferenceGenomeIndex>(r, r.getName())
			);
		}
	}

	@Override
	public boolean isStepCompleted() {
		return this.combobox.getSelectedIndex() != -1;
	}

	/**
	 * Returns the selected reference genome index.
	 * 
	 * @return the selected reference genome index
	 */
	public T getSelectedReferenceGenomeIndex() {
		ComboBoxItem<?> selectedItem = 
			(ComboBoxItem<?>) this.combobox.getSelectedItem();

		return referenceGenomeClass.cast(selectedItem.getItem());
	}

	@Override
	public void referenceGenomeIndexAdded() {
		updateComboboxItems();
	}

	@Override
	public void referenceGenomeIndexRemoved() {
		updateComboboxItems();
	}
}
