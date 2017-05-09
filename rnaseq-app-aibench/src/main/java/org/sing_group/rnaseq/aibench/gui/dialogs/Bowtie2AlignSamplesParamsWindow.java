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
