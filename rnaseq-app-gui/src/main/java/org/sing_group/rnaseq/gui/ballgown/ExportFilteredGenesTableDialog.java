package org.sing_group.rnaseq.gui.ballgown;

import java.awt.Window;

/**
 * A dialog that allows the user to configure the settings to export a genes
 * table.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class ExportFilteredGenesTableDialog extends ExportTableDialog {
	private static final long serialVersionUID = 1L;
	private static final String DESCRIPTION =
		"This dialog allows you to export the filtered genes table and save "
		+ "only those genes under the specified p-value.";

	public ExportFilteredGenesTableDialog(Window parent) {
		super(parent);
	}

	@Override
	protected String getDescription() {
		return DESCRIPTION;
	}
}
