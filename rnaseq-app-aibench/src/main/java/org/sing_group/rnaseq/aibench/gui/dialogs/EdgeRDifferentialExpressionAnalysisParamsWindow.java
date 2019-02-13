/*
 * #%L
 * DEWE
 * %%
 * Copyright (C) 2016 - 2019 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
import es.uvigo.ei.aibench.workbench.inputgui.ParamsWindow;

/**
 * A customized {@code ParamsWindow} to use in the edgeR differential analysis
 * operation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class EdgeRDifferentialExpressionAnalysisParamsWindow
	extends ParamsWindow {
	private static final long serialVersionUID = 1L;
	public static final String SAMPLES = "Samples";
	public static final String SAMPLES_DESCRIPTION = 
		"<html>The edgeR samples to analyze. For each sample, "
		+ "you must provide:<ul>"
		+ "<li>File: the bam file.</li>"
		+ "<li>Name: the name of the sample.</li>"
		+ "<li>Type: the type or experimental condition of the sample. Note "
		+ "that this analysis requires two conditions with at least two "
		+ "samples each</li></ul></html>";

	protected ParamProvider getParamProvider(final Port arg0,
		final Class<?> arg1, final Object arg2) {

		if (arg0.name().equals(SAMPLES)) {
			return new EdgeRDifferentialExpressionAnalysisParamProvider();
		}

		return super.getParamProvider(arg0, arg1, arg2);
	}
}
