package org.sing_group.rnaseq.gui.components;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXTable;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeDatabaseManager;

public class ReferenceGenomeDatabaseViewer extends JPanel {
	private static final long serialVersionUID = 1L;
	private JXTable table;

	public ReferenceGenomeDatabaseViewer() {
		this.setLayout(new BorderLayout());
		this.table = new JXTable(0, 0);
		this.add(new JScrollPane(this.table), BorderLayout.CENTER);
	}

	public void setReferenceGenomeDatabaseManager(DefaultReferenceGenomeDatabaseManager database) {
		this.table.setModel(new ReferenceGenomeDatabaseTableModel(database));
		this.updateUI();
	}
}
