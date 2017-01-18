package org.sing_group.rnaseq.gui.components;

import static es.uvigo.ei.sing.hlfernandez.demo.DemoUtils.showComponent;

import java.io.File;
import java.util.Optional;

import javax.swing.JComponent;

import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenome;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeDatabaseManager;;

public class ReferenceGenomeDatabaseViewerTest {

	public static void main(String[] args) {
		showComponent(createComponent());
	}
	
	private static JComponent createComponent() {
		ReferenceGenomeDatabaseViewer component = new ReferenceGenomeDatabaseViewer();
		component.setReferenceGenomeDatabaseManager(
			createReferenceGenomeDatabaseManager());
		return component;
	}

	private static DefaultReferenceGenomeDatabaseManager 
		createReferenceGenomeDatabaseManager() {
		DefaultReferenceGenomeDatabaseManager dbManager = 
			DefaultReferenceGenomeDatabaseManager.getInstance();

		dbManager.addReferenceGenome(new ReferenceGenome() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isValid() {
				return false;
			}

			@Override
			public String getType() {
				return "bowtie2";
			}

			@Override
			public Optional<String> getReferenceGenomeIndex() {
				return Optional.of("index");
			}

			@Override
			public File getReferenceGenome() {
				return new File("/data/genome.fa");
			}
		});
		dbManager.addReferenceGenome(new ReferenceGenome() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isValid() {
				return true;
			}

			@Override
			public String getType() {
				return "bowtie2";
			}

			@Override
			public Optional<String> getReferenceGenomeIndex() {
				return Optional.of("index");
			}

			@Override
			public File getReferenceGenome() {
				return new File("/data/genome-2.fa");
			}
		});
		return dbManager;
	}
}
