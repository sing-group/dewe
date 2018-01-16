/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola,
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
package org.sing_group.rnaseq.core.environment.execution.parameters.trimmomatic;

import org.sing_group.rnaseq.api.environment.binaries.TrimmomaticBinaries;
import org.sing_group.rnaseq.api.environment.execution.parameters.trimmomatic.TrimmomaticParameter;
import org.sing_group.rnaseq.core.controller.DefaultAppController;

public class IlluminaClipParameter implements TrimmomaticParameter {
	private static final long serialVersionUID = 1L;

	public static final String DESCRIPTION = "This step is used to find and "
		+ "remove Illumina adapters.";
	public static final String DESCRIPTION_ADAPTER = 
		"The fasta file containing all the adapters, PCR sequences etc. The "
		+ "naming of the various sequences within this file determines how "
		+ "they are used. Refer to Trimmomatic documentation for more details";
	public static final String DESCRIPTION_SEED_MISMATCHES = "The maximum "
		+ "mismatch count which will still allow a full match to be performed.";
	public static final String DESCRIPTION_PALINDROME_THRESHOLD = "How "
		+ "accurate the match between the two 'adapter ligated' reads must be "
		+ "for PE palindrome read alignment.";
	public static final String DESCRIPTION_SIMPLE_THRESHOLD = "How accurate "
		+ "the match between any adapter etc. sequence must be against a read.";

	public enum FastaAdapter {
		NEXTERA_PE("NexteraPE-PE.fa"),
		TRUSEQ2_PE("TruSeq2-PE.fa"),
		TRUSEQ2_SE("TruSeq2-SE.fa"),
		TRUSEQ3_PE("TruSeq3-PE.fa"),
		TRUSEQ3_PE_2("TruSeq3-PE-2.fa"),
		TRUSEQ3_SE("TruSeq3-SE.fa");
		
		private String fasta;

		FastaAdapter(String fasta) {
			this.fasta = fasta;
		}
		
		@Override
		public String toString() {
			return this.fasta;
		}

		public String getAdapter() {
			if (DefaultAppController.getInstance() != null) {
				return DefaultAppController.getInstance()
					.getProperty(TrimmomaticBinaries.BASE_DIRECTORY_PROP)
					.orElse("") + "/adapters/" + this.fasta;
			} else {
				return this.fasta;
			}
		}
	};

	private FastaAdapter fastaAdapter;
	private int seedMismatches;
	private int palindromeClipThreshold;
	private int simpleClipThreshold;

	public IlluminaClipParameter(FastaAdapter fastaAdapter, int seedMismatches,
		int palindromeClipThreshold, int simpleClipThreshold
	) {
		this.fastaAdapter = fastaAdapter;
		this.seedMismatches = seedMismatches;
		this.palindromeClipThreshold = palindromeClipThreshold;
		this.simpleClipThreshold = simpleClipThreshold;
	}

	@Override
	public String getParameter() {
		return "ILLUMINACLIP";
	}

	@Override
	public String getValue() {
		return this.fastaAdapter.getAdapter() + ":" + this.seedMismatches + ":"
			+ this.palindromeClipThreshold + ":" + this.simpleClipThreshold;
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@Override
	public boolean isValidValue() {
		return true;
	}
	
	@Override
	public String toString() {
		return this.getParameter() + ":" + this.getValue();
	}
}
