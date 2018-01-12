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

/**
 * The workflow presentation step.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class BowtieStringTieAndRDifferentialExpressionWizardPresentationStep
	extends AbstractHtmlTextStep {

	public static final String TEXT = "<html>"
		+ "<p>This wizard will guide you through the steps to configure the "
		+ "differential expression pipeline using Bowtie2, samtools, StringTie"
		+ ", Ballgown and EdgeR.</p>"

		+ "<p>This pipeline is able to compare two conditions with at least "
		+ "two samples each.</p>"

		+ "<p>The pipeline performs the following steps:</p><ol>"
		+ "<li>Reads alignment using Bowtie2.</li>"
		+ "<li>Conversion of the aligned reads into bam files using samtools.</li>"
		+ "<li>Transcript assembling using StringTie.</li>"
		+ "<li>And, finally, the differential expression analysis using two "
		+ "R packages: ballgown and edgeR.</li>"
		+ "</ol>"

		+ "Once you have finished this wizard, the pipeline execution starts "
		+ "and you will be able to monitor the exection progress."

		+ "</html>";

	@Override
	protected String getHtmlText() {
		return TEXT;
	}

	@Override
	public String getStepTitle() {
		return "Differential expression analysis pipeline";
	}
}

