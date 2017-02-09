package org.sing_group.rnaseq.aibench.gui.dialogs;

import static java.util.stream.Collectors.toList;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenome;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.core.persistence.entities.DefaultBowtie2ReferenceGenome;

import es.uvigo.ei.aibench.core.CoreUtils;
import es.uvigo.ei.aibench.core.ParamSpec;
import es.uvigo.ei.aibench.workbench.inputgui.AbstractParamProvider;
import es.uvigo.ei.sing.hlfernandez.combobox.ComboBoxItem;

public class Bowtie2ReferenceGenomeParamProvider extends AbstractParamProvider {
	private JComboBox<ComboBoxItem<ReferenceGenome>> component;

	@Override
	public JComponent getComponent() {
		component = new JComboBox<ComboBoxItem<ReferenceGenome>>();
		getComboboxItems().forEach(component::addItem);
		return component;
	}

	private List<ComboBoxItem<ReferenceGenome>> getComboboxItems() {
		return 	getBowtie2Indexes().stream()
				.map(i -> new ComboBoxItem<ReferenceGenome>(i, i.getReferenceGenome().getAbsolutePath()))
				.collect(toList());
	}

	private List<ReferenceGenome> getBowtie2Indexes() {
		return 	DefaultReferenceGenomeDatabaseManager.getInstance()
				.listReferenceGenomes().stream()
				.filter(g -> g instanceof DefaultBowtie2ReferenceGenome)
				.collect(toList());
	}

	@Override
	public ParamSpec getParamSpec() throws IllegalArgumentException {
		List<ReferenceGenome> params = new LinkedList<>();
		params.add((ReferenceGenome) getSelectedItem().getItem());

		return CoreUtils.createParams(params)[0];
	}

	private ComboBoxItem<?> getSelectedItem() {
		ComboBoxItem<?> selectedItem = ((ComboBoxItem<?>) component.getSelectedItem());
		return selectedItem;
	}

	@Override
	public boolean isValidValue() {
		return !DefaultReferenceGenomeDatabaseManager.getInstance().listReferenceGenomes().isEmpty();
	}
}
