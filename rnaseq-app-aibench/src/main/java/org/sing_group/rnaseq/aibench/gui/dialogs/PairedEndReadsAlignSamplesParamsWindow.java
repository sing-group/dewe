package org.sing_group.rnaseq.aibench.gui.dialogs;

import static org.sing_group.rnaseq.gui.sample.FastqSampleEditor.*;
import java.io.File;
import java.util.Optional;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;

import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.inputgui.FileParamProvider;
import es.uvigo.ei.aibench.workbench.inputgui.ParamProvider;
import es.uvigo.ei.aibench.workbench.inputgui.ParamsWindow;
import org.sing_group.gc4s.event.DocumentAdapter;

/**
 * An extension of {@code ParamsWindow} to use in operations that take as input
 * paired end reads files. This parameters window listens for changes in the
 * first reads file in order to automatically look for the second reads file in
 * the same folder and select it if it can be found.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class PairedEndReadsAlignSamplesParamsWindow extends ParamsWindow {
	private static final long serialVersionUID = 1L;

	public static final String READS_FILE_1 = "Reads file 1";
	public static final String READS_FILE_1_DESCRIPTION = "Reads file 1.";
	public static final String READS_FILE_2 = "Reads file 2";
	public static final String READS_FILE_2_DESCRIPTION = "Reads file 2.";

	private JTextField readsFile1TextField;
	private FileParamProvider readsFile2TextParamProvider;

	protected ParamProvider getParamProvider(final Port arg0,
		final Class<?> arg1, final Object arg2
	) {
		ParamProvider paramProvider = super.getParamProvider(arg0, arg1, arg2);
		
		if (arg0.name().equals(READS_FILE_1)) {
			this.readsFile1TextField = 
				((FileParamProvider) paramProvider).getField();
			this.readsFile1TextField.getDocument().addDocumentListener(
				new DocumentAdapter() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						readsFile1Changed();
					}
				});
		} else if (arg0.name().equals(READS_FILE_2)) {
			this.readsFile2TextParamProvider =
				((FileParamProvider) paramProvider); 
		}

		return paramProvider;
	}

	protected void readsFile1Changed() {
		File readsFile1 = new File(readsFile1TextField.getText());
		if (isValidFile(readsFile1)) {
			Optional<File> readsFile2 = lookForReadsFile2(readsFile1);
			if (readsFile2.isPresent()) {
				this.readsFile2TextParamProvider
					.setSelectedFile(readsFile2.get());
			}
		}
	}
}
