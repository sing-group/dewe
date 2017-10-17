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
package org.sing_group.rnaseq.gui.components.wizard.steps;

import static org.sing_group.rnaseq.gui.components.wizard.steps.StepUtils.configureStepComponent;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXLabel;
import org.sing_group.gc4s.combobox.ComboBoxItem;
import org.sing_group.gc4s.ui.CenteredJPanel;
import org.sing_group.gc4s.ui.icons.Icons;
import org.sing_group.gc4s.wizard.WizardStep;
import org.sing_group.gc4s.wizard.event.WizardStepEvent;
import org.sing_group.rnaseq.api.persistence.ReferenceGenomeIndexDatabaseManager;
import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenomeIndex;
import org.sing_group.rnaseq.api.persistence.entities.event.ReferenceGenomeIndexDatabaseListener;

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
	extends WizardStep implements ReferenceGenomeIndexDatabaseListener, ItemListener {
	private static final String UNCOMPLETED_TOOLTIP = "Select a reference "
		+ "genome index in order to advance to the next step";

	private Class<T> referenceGenomeClass;
	private ReferenceGenomeIndexDatabaseManager databaseManager;

	private JPanel stepComponent;
	private JComboBox<ComboBoxItem<ReferenceGenomeIndex>> combobox;
	private JLabel warningLabel;

	private static final ReferenceGenomeIndex NONE_SELECTED =
		new ReferenceGenomeIndex() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isValidIndex() {
				return false;
			}

			@Override
			public String getType() {
				return "";
			}

			@Override
			public String getReferenceGenomeIndex() {
				return "";
			}

			@Override
			public Optional<File> getReferenceGenome() {
				return null;
			}

			@Override
			public String getName() {
				return "<Click here to select an index>";
			}
		};

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
		selectionPanel.add(getWarningLabel(), BorderLayout.EAST);

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
	 * Returns the label that shows a warning in the selection combobox when a
	 * valid item is not selected.
	 *
	 * @return the label that shows a warning in the selection combobox when a
	 *         valid item is not selected
	 */
	protected JComponent getWarningLabel() {
		warningLabel = new JLabel(Icons.ICON_WARNING_COLOR_24);
		warningLabel.setToolTipText(UNCOMPLETED_TOOLTIP);

		return warningLabel;
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
			combobox.addItemListener(this);
		}
		updateComboboxItems();
		return combobox;
	}

	protected void updateComboboxItems() {
		this.combobox.removeAllItems();

		this.combobox.addItem(new ComboBoxItem<ReferenceGenomeIndex>(NONE_SELECTED, NONE_SELECTED.getName()));
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
		return this.combobox.getSelectedIndex() != -1
			&& this.combobox.getSelectedIndex() != 0;
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

	public boolean setSelectedReferenceGenomeIndex(T selected,
		boolean addIfNotExists
	) {
		int selectedIndex = findReferenceGenomeIndex(selected);
		if (addIfNotExists) {
			this.databaseManager.addIndex(selected);
			selectedIndex = findReferenceGenomeIndex(selected);
		}

		this.combobox.setSelectedIndex(selectedIndex);

		if (selectedIndex == 0) {
			return false;
		} else {
			return true;
		}
	}

	private int findReferenceGenomeIndex(T selected) {
		for (int i = 0; i < this.combobox.getModel().getSize(); i++) {
			ReferenceGenomeIndex item =
				this.combobox.getModel().getElementAt(i).getItem();

			if (item.getName().equals(selected.getName())
				&& item.getReferenceGenomeIndex()
					.equals(selected.getReferenceGenomeIndex())
				&& item.getType().equals(selected.getType())
			) {
				return i;
			}
		}

		return 0;
	}

	@Override
	public void referenceGenomeIndexAdded() {
		updateComboboxItems();
	}

	@Override
	public void referenceGenomeIndexRemoved() {
		updateComboboxItems();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			referenceGenomeIndexSelectionChanged();
		}
	}

	private void referenceGenomeIndexSelectionChanged() {
		if (isStepCompleted()) {
			setWarningLabelVisible(false);
			this.notifyWizardStepCompleted(new WizardStepEvent(this));
		} else {
			setWarningLabelVisible(true);
			this.notifyWizardStepUncompleted(new WizardStepEvent(this));
			this.notifyWizardStepNextButtonTooltipChanged(UNCOMPLETED_TOOLTIP);
		}
	}

	private void setWarningLabelVisible(boolean visible) {
		if (this.warningLabel != null) {
			this.warningLabel.setVisible(visible);
		}
	}

	@Override
	public void stepEntered() {
		this.notifyWizardStepNextButtonTooltipChanged(UNCOMPLETED_TOOLTIP);
	}
}
