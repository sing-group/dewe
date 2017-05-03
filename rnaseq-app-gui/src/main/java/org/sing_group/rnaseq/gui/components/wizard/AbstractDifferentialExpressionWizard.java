package org.sing_group.rnaseq.gui.components.wizard;

import java.awt.Window;
import java.io.File;
import java.util.List;

import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenome;
import org.sing_group.rnaseq.gui.components.wizard.steps.WizardSummaryProvider;

import org.sing_group.gc4s.wizard.Wizard;
import org.sing_group.gc4s.wizard.WizardStep;

/**
 * An abstract extension of {@code Wizard} that implements
 * {@code WizardSummaryProvider} to provide a summary of the differential
 * expression workflow configuration.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public abstract class AbstractDifferentialExpressionWizard extends Wizard
	implements WizardSummaryProvider {
	private static final long serialVersionUID = 1L;
	private static final String NEW_LINE = "\n";

	/**
	 * Creates a new {@code AbstractDifferentialExpressionWizard} with the
	 * specified parameters.
	 *
	 * @param parent the parent window dialog
	 * @param wizardTitle the title of the wizard dialog
	 * @param steps the list of {@code WizardStep}
	 */
	public AbstractDifferentialExpressionWizard(Window parent,
		String wizardTitle, List<WizardStep> steps
	) {
		super(parent, wizardTitle, steps);
	}

	@Override
	public String getSummary() {
		StringBuilder sb = new StringBuilder();
		sb
			.append("Workflow configuration:")
			.append(NEW_LINE)
			.append("  · Reference genome: ")
			.append(getReferenceGenome().getReferenceGenome().getAbsolutePath())
			.append(NEW_LINE)
			.append("  · Reference annotation file: ")
			.append(getReferenceAnnotationFile().getAbsolutePath())
			.append(NEW_LINE)
			.append("  · Working directory: ")
			.append(getWorkingDirectory().getAbsolutePath())
			.append(NEW_LINE)
			.append(NEW_LINE)
			.append("Experiment samples: ")
			.append(NEW_LINE);
		getSamples().forEach(s -> {
			sb
				.append("  · Sample name: ")
				.append(s.getName())
				.append(" [Condition: ")
				.append(s.getCondition() + "]")
				.append(NEW_LINE);
		});
		sb
			.append(NEW_LINE)
			.append(NEW_LINE)
			.append("Click the finish button to run the workflow.");
		return sb.toString();
	}

	/**
	 * Returns the selected {code ReferenceGenome}.
	 *
	 * @return the selected {code ReferenceGenome}
	 */
	public abstract ReferenceGenome getReferenceGenome();

	/**
	 * Returns the selected {code FastqReadsSamples}.
	 *
	 * @return the selected {code FastqReadsSamples}
	 */
	public abstract FastqReadsSamples getSamples();

	/**
	 * Returns the selected reference annotation file.
	 *
	 * @return the selected reference annotation file
	 */
	public abstract File getReferenceAnnotationFile();

	/**
	 * Returns the selected working directory.
	 *
	 * @return the selected working directory
	 */
	public abstract File getWorkingDirectory();
}
