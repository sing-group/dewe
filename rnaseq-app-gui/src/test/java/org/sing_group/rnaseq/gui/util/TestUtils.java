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
package org.sing_group.rnaseq.gui.util;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

import org.sing_group.gc4s.demo.DemoUtils;
import org.sing_group.gc4s.dialog.wizard.WizardStep;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenomeIndex;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenomeIndex;
import org.sing_group.rnaseq.core.entities.DefaultFastqReadsSample;
import org.sing_group.rnaseq.core.entities.DefaultFastqReadsSamples;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeIndexDatabaseManager;

public class TestUtils {

	public static final FastqReadsSamples TEST_FASTQ_READS = new DefaultFastqReadsSamples(
		Arrays.asList(
			new DefaultFastqReadsSample(
				"Sample A", "A", new File("a_1.fastq"), new File("a_2.fastq")),
			new DefaultFastqReadsSample(
				"Sample B", "B", new File("b_1.fastq"), new File("b_2.fastq"))
		));

	public static void showStepComponent(WizardStep step) {
		DemoUtils.setNimbusLookAndFeel();
		DemoUtils.showComponent(step.getStepComponent());
		step.stepEntered();
	}

	public static DefaultReferenceGenomeIndexDatabaseManager createReferenceGenomeDatabaseManager() {
		DefaultReferenceGenomeIndexDatabaseManager dbManager =
			DefaultReferenceGenomeIndexDatabaseManager.getInstance();

		dbManager.addIndex(new Bowtie2ReferenceGenomeIndex() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isValidIndex() {
				return false;
			}

			@Override
			public String getName() {
				return "Genome 1";
			}

			@Override
			public String getType() {
				return "bowtie2";
			}

			@Override
			public String getReferenceGenomeIndex() {
				return "index";
			}

			@Override
			public Optional<File> getReferenceGenome() {
				return Optional.of(new File("/home/users/dataRNA/data/genome.fa"));
			}
		});

		dbManager.addIndex(new Bowtie2ReferenceGenomeIndex() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isValidIndex() {
				return true;
			}

			@Override
			public String getName() {
				return "Genome 2";
			}

			@Override
			public String getType() {
				return "bowtie2";
			}

			@Override
			public String getReferenceGenomeIndex() {
				return "index 2";
			}

			@Override
			public Optional<File> getReferenceGenome() {
				return Optional.of(new File("/data/genome-2.fa"));
			}
		});

		dbManager.addIndex(new Hisat2ReferenceGenomeIndex() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isValidIndex() {
				return true;
			}

			@Override
			public String getName() {
				return "Genome 3";
			}

			@Override
			public String getType() {
				return "bowtie2";
			}

			@Override
			public String getReferenceGenomeIndex() {
				return "index 3";
			}

			@Override
			public Optional<File> getReferenceGenome() {
				return Optional.of(new File("/data/genome-2.fa"));
			}
		});

		return dbManager;
	}
}
