package org.sing_group.rnaseq.aibench.gui.dialogs;

import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.inputgui.ParamProvider;
import es.uvigo.ei.aibench.workbench.inputgui.ParamsWindow;

public class Hisat2AlignSamplesParamsWindow extends ParamsWindow {
	private static final long serialVersionUID = 1L;
	public static final String REFERENCE_GENOME = "Reference genome";

	protected ParamProvider getParamProvider(final Port arg0,
			final Class<?> arg1, final Object arg2
		) {
			
			if (arg0.name().equals(REFERENCE_GENOME)) {
				return new Hisat2ReferenceGenomeParamProvider();
			}

			return super.getParamProvider(arg0, arg1, arg2);
		}
}
