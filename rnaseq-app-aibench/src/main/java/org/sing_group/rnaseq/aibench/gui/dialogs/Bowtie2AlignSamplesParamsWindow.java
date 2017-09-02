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

import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.inputgui.ParamProvider;

/**
 * This extension of {@link PairedEndReadsAlignSamplesParamsWindow} provides a
 * {@link Bowtie2ReferenceGenomeIndexParamProvider} to select the reference genome.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class Bowtie2AlignSamplesParamsWindow
	extends PairedEndReadsAlignSamplesParamsWindow {
	private static final long serialVersionUID = 1L;
	public static final String REFERENCE_GENOME = "Reference genome";

	protected ParamProvider getParamProvider(final Port arg0,
		final Class<?> arg1, final Object arg2
	) {
		if (arg0.name().equals(REFERENCE_GENOME)) {
			return new Bowtie2ReferenceGenomeIndexParamProvider();
		}

		return super.getParamProvider(arg0, arg1, arg2);
	}
}
