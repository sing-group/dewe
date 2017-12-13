/*
 * #%L
 * DEWE
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
package org.sing_group.rnaseq.aibench.gui.dialogs;

import static java.util.stream.Collectors.toList;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import org.sing_group.gc4s.input.combobox.ComboBoxItem;
import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenomeIndex;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeIndexDatabaseManager;

import es.uvigo.ei.aibench.core.CoreUtils;
import es.uvigo.ei.aibench.core.ParamSpec;
import es.uvigo.ei.aibench.workbench.inputgui.AbstractParamProvider;

/**
 * An abstract extension of {@code AbstractParamProvider} that shows a combobox
 * with the reference genome indexes in the database manager.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public abstract class AbstractReferenceGenomeIndexParamProvider
	extends AbstractParamProvider {
	private JComboBox<ComboBoxItem<ReferenceGenomeIndex>> component;

	@Override
	public JComponent getComponent() {
		component = new JComboBox<ComboBoxItem<ReferenceGenomeIndex>>();
		getComboboxItems().forEach(component::addItem);
		return component;
	}

	private List<ComboBoxItem<ReferenceGenomeIndex>> getComboboxItems() {
		return 	getReferenceGenomeIndexes().stream()
				.map(i -> new ComboBoxItem<ReferenceGenomeIndex>(i, i.getName()))
				.collect(toList());
	}

	protected List<ReferenceGenomeIndex> getReferenceGenomeIndexes() {
		return 	DefaultReferenceGenomeIndexDatabaseManager.getInstance()
				.listIndexes().stream()
				.filter(this::filterGenome)
				.collect(toList());
	}

	/**
	 * Returns {@code true} if the specified {@code ReferenceGenomeIndex} must be
	 * shown in the combobox and {@code false} otherwise.
	 * 
	 * @param genome the {@code ReferenceGenomeIndex} to filter.
	 * @return {@code true} if the specified {@code ReferenceGenomeIndex} must be
	 *         shown in the combobox and {@code false} otherwise
	 */
	protected abstract boolean filterGenome(ReferenceGenomeIndex genome);

	@Override
	public ParamSpec getParamSpec() throws IllegalArgumentException {
		List<ReferenceGenomeIndex> params = new LinkedList<>();
		params.add((ReferenceGenomeIndex) getSelectedItem().getItem());

		return CoreUtils.createParams(params)[0];
	}

	private ComboBoxItem<?> getSelectedItem() {
		ComboBoxItem<?> selectedItem = 
			((ComboBoxItem<?>) component.getSelectedItem());
		return selectedItem;
	}

	@Override
	public boolean isValidValue() {
		return !getReferenceGenomeIndexes().isEmpty();
	}
}
