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
package org.sing_group.rnaseq.gui.workflow;

import org.sing_group.rnaseq.api.entities.WorkflowDescription;

/**
 * The Bowtie2, StringTie and R packages workflow description.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class Bowtie2StringTieDifferentialExpressionWorkflowDescription
	implements WorkflowDescription {

	@Override
	public String getTitle() {
		return "Differential expression analysis using Bowtie2, StringTie and R";
	}

	@Override
	public String getShortDescription() {
		return	"This workflow allows performing a differential expression "
			+ "analysis using Bowtie2 to align sample reads, StringTie to "
			+ "assemble transcripts and two R libraries, Ballgown and EdgeR, to "
			+ "perform the differential expression itself. This pipeline is "
			+ "able to compare two conditions with at least two samples each.";
	}
}
